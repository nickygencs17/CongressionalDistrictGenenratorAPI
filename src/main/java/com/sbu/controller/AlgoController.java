package com.sbu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sbu.data.AppUserRepository;
import com.sbu.data.entitys.*;
import com.sbu.main.Constants;
import com.sbu.services.AlgoService;
import com.sbu.services.AppUserService;
import com.sbu.services.LogService;
import com.sbu.services.StateService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build404;

@RestController
@CrossOrigin
@Component
@RequestMapping("/algorithm")
public class AlgoController {

    @Autowired
    AlgoService algoService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    LogService logService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    StateService stateService;

    HashMap<String, UsState> currentStates = new HashMap<>();
    HashMap<String, StartAlgoObject> currentProperties = new HashMap<>();
    HashMap<String, List<Move>> currentMoves = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST)
    Response postStartAlgo(@RequestBody StartAlgoObject startAlgoObject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        startAlgoObject.trimString();
        AppUser appUser = (AppUser) appUserService.getUserByUsername(username);
        UsState selectedState = stateService.getStatebyId(startAlgoObject.getState_id());
        selectedState.setIncludes(startAlgoObject);
        String id = UUID.randomUUID().toString();
        currentStates.put(id, selectedState);
        currentProperties.put(id, startAlgoObject);
        currentMoves.put(id, new ArrayList<Move>());
        return build200(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    Response deleteAlgo(@PathVariable(value = "id") String id) {
        if (currentStates.containsKey(id)) currentStates.remove(id);
        if (currentProperties.containsKey(id)) currentProperties.remove(id);
        if (currentMoves.containsKey(id)) currentMoves.remove(id);
        stateService.deleteRedistrict(id);
        return build200("Deleted");
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    Response saveAlgo(@PathVariable(value = "id") String id) {
        if (!currentStates.containsKey(id)) {
            return build404("No current state found with specified id");
        }
        StartAlgoObject startAlgoObject = currentProperties.get(id);
        Redistrict newRedistrict = new Redistrict();
        newRedistrict.setC_coefficient(startAlgoObject.getC_coefficient());
        newRedistrict.setF_coefficient(startAlgoObject.getF_coefficient());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String exludedPrecinctsString = "[]";
        try {
            exludedPrecinctsString = ow.writeValueAsString(startAlgoObject.getExcluded_precinct_ids());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        newRedistrict.setExcluded_precincts(exludedPrecinctsString);
        String includedCongressString = "[]";
        try {
            includedCongressString = ow.writeValueAsString(startAlgoObject.getIncluded_districts_ids());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        newRedistrict.setIncluded_congressional_districts(includedCongressString);
        newRedistrict.setPopulation_deviation(startAlgoObject.getPopulation_deviation());
        newRedistrict.setId(id);
        List<Move> moves = currentMoves.get(id);
        if (moves == null) {
            return build404("Moves not found in memory");
        }
        String movesJson = null;
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            movesJson = ow.writeValueAsString(moves);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        newRedistrict.setMoves(movesJson);
        newRedistrict.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        newRedistrict.setTimestamp(new Date(System.currentTimeMillis()));
        newRedistrict.setState_id(startAlgoObject.getState_id());
        stateService.saveRedistrict(newRedistrict);
        return build200("saved");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    Response getUpdate(@PathVariable(value = "id") String id) {
        if (!currentStates.containsKey(id)) {
            return build404("Id not found in current states");
        }
        StartAlgoObject startAlgoObject = currentProperties.get(id);

        String time_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String log_details = Constants.LOG_USER + SecurityContextHolder.getContext().getAuthentication().getName() + Constants.LOG_STATE + currentStates.get(id).getState_id() + Constants.LOG_INCLUDED + startAlgoObject.getIncluded_districts_ids();
        Log algoLog = new Log(time_date, log_details);
        logService.postLog(algoLog);

        float populationDeviation = startAlgoObject.getPopulation_deviation();
        int ccoefficient = startAlgoObject.getC_coefficient();
        int fcoefficient = startAlgoObject.getF_coefficient();
        Update update = algoService.startAlgorithm(currentStates.get(id), populationDeviation, ccoefficient, fcoefficient);
        List<Move> moves = currentMoves.get(id);
        List<Move> updateMoves = update.getMoves();
        for (int i = 0; i < updateMoves.size(); i++) {
            moves.add(updateMoves.get(i));
        }
        currentMoves.put(id, moves);
        if (update.isFinished()) {
            currentStates.remove(id);
            currentMoves.remove(id);
            currentProperties.remove(id);
        }
        logService.deleteLog(algoLog);
        return build200(update);
    }

    @RequestMapping(value = "all/{username:.+}", method = RequestMethod.GET)
    Response getAllRedistrictsByUsername(@PathVariable(value = "username") String username) {
        List<Redistrict> savedRedistricts = stateService.getSavedRedistringByUser(username);
        if (savedRedistricts == null) {
            return build404("Saved redistricts not found for user");
        }
        JSONObject return_node = new JSONObject();
        JSONArray arr = new JSONArray();
        for (int i = 0; i < savedRedistricts.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("id", savedRedistricts.get(i).getId());
            obj.put("time", savedRedistricts.get(i).getTimestamp());
            obj.put("state_id", savedRedistricts.get(i).getState_id());
            arr.add(obj);
        }
        return_node.put("Redistricts", arr);
        return build200(return_node);
    }

    @RequestMapping(value = "redistricting/{id}", method = RequestMethod.GET)
    Response getRedistrictById(@PathVariable(value = "id") String id) {
        Redistrict savedRedistrict = stateService.getSavedRedistringById(id);
        if (savedRedistrict == null) {
            return build404("No such redistrict with given id");
        }
        savedRedistrict.mapLists();
        StartAlgoObject startAlgoObject = new StartAlgoObject(savedRedistrict);
        UsState selectedState = stateService.getStatebyId(startAlgoObject.getState_id());
        selectedState.setIncludes(startAlgoObject);
        algoService.executeMoves(selectedState, savedRedistrict.getMovesList());
        currentStates.put(id, selectedState);
        currentProperties.put(id, startAlgoObject);
        currentMoves.put(id, new ArrayList<Move>(savedRedistrict.getMovesList()));
        JSONObject return_node = new JSONObject();
        return_node.put("fCoefficient", savedRedistrict.getF_coefficient());
        return_node.put("cCoefficient", savedRedistrict.getC_coefficient());
        return_node.put("population_deviation", savedRedistrict.getPopulation_deviation());
        return_node.put("state_id", savedRedistrict.getState_id());
        return_node.put("moves", savedRedistrict.getMovesList());
        return_node.put("excluded_precinct_ids", savedRedistrict.getexcluded_precincts_geo_ids());
        return_node.put("included_districts_ids", savedRedistrict.getIncluded_congressional_ids());
        return_node.put("population_deviation", selectedState.calculatePopulationDeviation());
        return_node.put("compactness", selectedState.calculateCompactness());
        return_node.put("political_fairness", selectedState.calculatePoliticalFairness());
        return build200(return_node);
    }
}
