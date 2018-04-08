package com.sbu.controller;

import com.sbu.data.entitys.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogController {

    @RequestMapping(method = RequestMethod.POST)
    Response postLog(@RequestBody @Valid Log log){

        return build200("Test");
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    Response getAllLogs(){

        return build200("Test");
    }

    @RequestMapping(method = RequestMethod.GET)
    Response getSummary(){

        return build200("Test");
    }

}
