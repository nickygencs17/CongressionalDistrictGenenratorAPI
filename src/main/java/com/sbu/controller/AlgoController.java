package com.sbu.controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@RequestMapping("/algo")
public class AlgoController {

    @RequestMapping(method = RequestMethod.GET)
    Response getStartAlgo(@RequestParam("var1") String var1, @RequestParam("var2") String var2){
        return build200("Test");
    }

}
