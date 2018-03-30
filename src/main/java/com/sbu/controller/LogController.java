package com.sbu.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogController {

    @RequestMapping(method = RequestMethod.POST)
    Response postLog(){

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
