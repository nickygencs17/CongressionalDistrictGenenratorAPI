package com.sbu.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@RequestMapping("/algo")
public class AlgoController {

    @RequestMapping(method = RequestMethod.GET)
    Response getStartAlgo(){

        return build200("Test");
    }

}
