package com.sbu.controller;

import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;


@RequestMapping("/state")
@RestController
@CrossOrigin
public class StateController {

    @RequestMapping(value = "/congressional/{id}",method = RequestMethod.GET)
    Response getCongressionalGeo(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/legislative/{id}",method = RequestMethod.GET)
    Response getStateLegistaltiveGeo(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/senate/{id}",method = RequestMethod.GET)
    Response getSenateGeo(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    Response getStateBoundriesGeo(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/sateInfo/{id}",method = RequestMethod.GET)
    Response getStateInfo(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/electionInfo/{id}",method = RequestMethod.GET)
    Response getElectionInfo(@PathVariable(value="id") String id){

        return build200("Test");
    }




}
