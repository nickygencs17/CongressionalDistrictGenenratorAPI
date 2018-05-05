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
    UsState selectedState;
    float populationDeviation;
    int ccoefficient;
    int fcoefficient;

    @RequestMapping(method = RequestMethod.POST)
    Response getStartAlgo(@RequestBody StartAlgoObject startAlgoObject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        AppUser appUser = (AppUser) appUserService.getUserByUsername(username);
        appUser.setPopulation_coefficient(startAlgoObject.getPopulation_deviation());
        appUser.setCompactness_coefficient(startAlgoObject.getC_coefficient());
        appUser.setFairness_coefficient(startAlgoObject.getF_coefficient());
        appUserRepository.save(appUser);
        this.populationDeviation = startAlgoObject.getPopulation_deviation();
        this.ccoefficient = startAlgoObject.getC_coefficient();
        this.fcoefficient = startAlgoObject.getF_coefficient();
        selectedState = stateService.getStatebyId(startAlgoObject.getState_id());
        return getUpdate();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    Response getUpdate() {
        Update update = algoService.startAlgorithm(selectedState, populationDeviation, ccoefficient, fcoefficient);
        return build200(update);
    }
}
