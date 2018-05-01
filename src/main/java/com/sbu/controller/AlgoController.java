package com.sbu.controller;

import com.sbu.data.AppUserRepository;
import com.sbu.data.entitys.AppUser;
import com.sbu.data.entitys.Update;
import com.sbu.data.entitys.UsState;
import com.sbu.services.AlgoService;
import com.sbu.services.AppUserService;
import com.sbu.services.StateService;
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

    @RequestMapping(method = RequestMethod.GET)
    Response getAlgoUpdate(@RequestParam("state") String stateName,
                          @RequestParam("populationDeviation") float populationDeviation,
                          @RequestParam("ccoefficient") int cCoefficient,
                          @RequestParam("fcoefficient") int fCoefficient) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = (AppUser) appUserService.getUserByUsername(username);
        appUser.setPopulation_coefficient(populationDeviation);
        appUser.setCompactness_coefficient(cCoefficient);
        appUser.setFairness_coefficient(fCoefficient);
        appUserRepository.save(appUser);
        UsState selectedState = appUserService.getStateforUser(username, stateName);
        if(selectedState == null) {
            selectedState = stateService.getStatebyId(stateName);
            appUserService.addStateforUser(username, selectedState);
        }
        Update update = algoService.startAlgorithm(selectedState, populationDeviation, cCoefficient, fCoefficient);
        if(update.isFinished()) appUserService.removeStateforUser(username);
        return build200(update);
    }
}
