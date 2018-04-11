package com.sbu.controller;

import com.sbu.main.Constants;
import com.sbu.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;


@RequestMapping("/state")
@RestController
@CrossOrigin
public class StateController {

    @Autowired
    StateService stateService;

    @RequestMapping(value = "/congressional/{id}",method = RequestMethod.GET)
    Response getCongressionalGeo(@PathVariable(value="id") String id){

        return build200( stateService.getBoundaries(Constants.CONGRESS,id));
    }

    @RequestMapping(value = "/upper/{id}",method = RequestMethod.GET)
    Response getStateLegistaltiveGeo(@PathVariable(value="id") String id){

        return build200( stateService.getBoundaries(Constants.UPPER,id));
    }

    @RequestMapping(value = "/lower/{id}",method = RequestMethod.GET)
    Response getSenateGeo(@PathVariable(value="id") String id){

        return build200( stateService.getBoundaries(Constants.LOWER,id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    Response getStateBoundriesGeo(@PathVariable(value="id") String id){

        return build200(stateService.getBoundaries(Constants.STATE,id));
    }

    @RequestMapping(value = "/sateInfo/{id}",method = RequestMethod.GET)
    Response getStateInfo(@PathVariable(value="id") String id){
        //TODO: this one....
        return build200("Test");
    }

    @RequestMapping(value = "/electionInfo/{id}",method = RequestMethod.GET)
    Response getElectionInfo(@PathVariable(value="id") String id){
        //TODO: and this one....
        return build200("Test");
    }




}
