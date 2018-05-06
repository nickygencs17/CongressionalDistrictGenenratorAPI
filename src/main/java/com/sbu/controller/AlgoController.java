package com.sbu.controller;

import com.sbu.data.AppUserRepository;
import com.sbu.data.RedistrictRepository;
import com.sbu.data.entitys.*;
import com.sbu.main.Constants;
import com.sbu.services.AlgoService;
import com.sbu.services.AppUserService;
import com.sbu.services.StateService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build401;
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
    AppUserRepository appUserRepository;

    @Autowired
    StateService stateService;

    HashMap<String, UsState> currentStates = new HashMap<>();
    HashMap<String, StartAlgoObject> currentProperties = new HashMap<>();
    @RequestMapping(method = RequestMethod.POST)
    Response postStartAlgo(@RequestBody StartAlgoObject startAlgoObject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = (AppUser) appUserService.getUserByUsername(username);
        UsState selectedState = stateService.getStatebyId(startAlgoObject.getState_id());
        selectedState.setIncludes(startAlgoObject);
        String id = UUID.randomUUID().toString();
        currentStates.put(id, selectedState);
        currentProperties.put(id, startAlgoObject);
        return build200(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    Response deleteAlgo(@PathVariable(value = "id") String id) {
        if(currentStates.containsKey(id)) currentStates.remove(id);
        if(currentProperties.containsKey(id)) currentProperties.remove(id);
        return build200("Deleted");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    Response getUpdate(@PathVariable(value = "id") String id) {
        //alright im gonna try on this one...
        // url aka path variables are used to identify a resources. Query parms are used to change resourcess.
        //since we wanna grab an update we are just identifying


        if(!currentStates.containsKey(id)) {

            //ERROR
        }
        StartAlgoObject startAlgoObject = currentProperties.get(id);
        float populationDeviation = startAlgoObject.getPopulation_deviation();
        int ccoefficient = startAlgoObject.getC_coefficient();
        int fcoefficient = startAlgoObject.getF_coefficient();
        Update update = algoService.startAlgorithm(currentStates.get(id), populationDeviation, ccoefficient, fcoefficient);
        if(update.isFinished()) currentStates.remove(id);
        return build200(update);
    }

    @RequestMapping(value = "all/{username:.+}", method = RequestMethod.GET)
    Response getAllRedistrictsByUsername(@PathVariable(value = "username") String username) {
        List<Redistrict> savedRedistricts = stateService.getSavedRedistringByUser(username);
        JSONObject return_node = new JSONObject();
        JSONArray arr = new JSONArray();
        for(int i = 0; i < savedRedistricts.size(); i++) {
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
        savedRedistrict.mapLists();
        StartAlgoObject startAlgoObject = new StartAlgoObject(savedRedistrict);
        UsState selectedState = stateService.getStatebyId(startAlgoObject.getState_id());
        selectedState.setIncludes(startAlgoObject);
        algoService.executeMoves(selectedState, savedRedistrict.getMovesList());
        currentStates.put(id, selectedState);
        currentProperties.put(id, startAlgoObject);
        JSONObject return_node = new JSONObject();
        return_node.put("fCoefficient", savedRedistrict.getF_coefficient());
        return_node.put("cCoefficient", savedRedistrict.getC_coefficient());
        return_node.put("population_deviation", savedRedistrict.getPopulation_deviation());
        return_node.put("state_id", savedRedistrict.getState_id());
        return_node.put("moves", savedRedistrict.getMovesList());
        return_node.put("excluded_precincts", savedRedistrict.getexcluded_precincts_geo_ids());
        return_node.put("included_congressional_districts", savedRedistrict.getIncluded_congressional_ids());
        return_node.put("population_deviation", selectedState.calculatePopulationDeviation());
        return_node.put("compactness", selectedState.calculateCompactness());
        return_node.put("political_fairness", selectedState.calculatePoliticalFairness());
        return build200(return_node);
    }
}
