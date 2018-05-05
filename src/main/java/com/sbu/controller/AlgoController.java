package com.sbu.controller;

import com.sbu.data.AppUserRepository;
import com.sbu.data.entitys.AppUser;
import com.sbu.data.entitys.StartAlgoObject;
import com.sbu.data.entitys.Update;
import com.sbu.data.entitys.UsState;
import com.sbu.services.AlgoService;
import com.sbu.services.AppUserService;
import com.sbu.services.StateService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;

import static com.sbu.utils.ResponseUtil.build200;

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
        //user name or security context added the regex because we have the .com in our usernames
        return build200("ok");
    }

}
