package com.sbu.controller;

import com.sbu.main.Constants;
import com.sbu.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build400;


@RequestMapping("/state")
@RestController
@CrossOrigin
public class StateController {

    @Autowired
    StateService stateService;

    @RequestMapping(value = "/congressional/{id}",method = RequestMethod.GET)
    Response getCongressionalGeo(@PathVariable(value="id") String id){
        if(!checkEagleState(id)){
            return build400(Constants.NOT_EAGLE_STATE);
        }
        return build200( stateService.getBoundaries(Constants.CONGRESS,id));
    }

    @RequestMapping(value = "/upper/{id}",method = RequestMethod.GET)
    Response getStateLegistaltiveGeo(@PathVariable(value="id") String id){

        if(!checkEagleState(id)){
            return build400(Constants.NOT_EAGLE_STATE);
        }
        return build200( stateService.getBoundaries(Constants.UPPER,id));
    }

    @RequestMapping(value = "/lower/{id}",method = RequestMethod.GET)
    Response getSenateGeo(@PathVariable(value="id") String id){
        if(!checkEagleState(id)){
            return build400(Constants.NOT_EAGLE_STATE);
        }

        return build200( stateService.getBoundaries(Constants.LOWER,id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    Response getStateBoundriesGeo(@PathVariable(value="id") String id){
        if(!checkEagleState(id)){
            return build400(Constants.NOT_EAGLE_STATE);
        }

        return build200(stateService.getBoundaries(Constants.STATE,id));
    }

    @RequestMapping(value = "/sateInfo/{id}",method = RequestMethod.GET)
    Response getStateInfo(@PathVariable(value="id") String id){
        if(!checkEagleState(id)){
            return build400(Constants.NOT_EAGLE_STATE);
        }
        //TODO: this one....
        return build200("Test");
    }

    @RequestMapping(value = "/electionInfo/{id}",method = RequestMethod.GET)
    Response getElectionInfo(@PathVariable(value="id") String id){
        if(!checkEagleState(id)){
            return build400(Constants.NOT_EAGLE_STATE);
        }

        //TODO: and this one....
        return build200("Test");
    }


    public boolean checkEagleState(String state_id){

        if(state_id.toUpperCase().equals(Constants.WEST_VIRGINA)
                ||state_id.toUpperCase().equals(Constants.INDIANA)
                ||state_id.toUpperCase().equals(Constants.ARKSANSAS)){
            return true;
        }

        return false;
    }




}
