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
    Response getStartAlgo(@RequestBody StartAlgoObject startAlgoObject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = (AppUser) appUserService.getUserByUsername(username);
        UsState selectedState = stateService.getStatebyId(startAlgoObject.getState_id());
        selectedState.setIncludes(startAlgoObject);
        String id = UUID.randomUUID().toString();
        currentStates.put(id, selectedState);
        currentProperties.put(id, startAlgoObject);
        return build200(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    Response getUpdate(@RequestParam("id") String id) {
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

//    @RequestMapping(method = RequestMethod.POST)
//    Response getStartAlgo(@RequestBody SaveRedistrict saveRedistrict) {
//
//        return build200("Saved");
//    }

}
