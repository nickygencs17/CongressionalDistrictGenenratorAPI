package com.sbu.controller;
import com.sbu.data.UsStateRepository;
import com.sbu.data.entitys.Move;
import com.sbu.data.entitys.UsState;
import com.sbu.services.AlgoService;
import com.sbu.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@Component
@RequestMapping("/algorithm")
public class AlgoController {

    @Autowired
    AlgoService algoService;

    @Autowired
    StateService stateService;
    ArrayList<Move> moves = new ArrayList<>();

    @RequestMapping(method = RequestMethod.GET)
    Response getStartAlgo(@RequestParam("state") String stateName, @RequestParam("pcoefficient") int pcoefficient,
                          @RequestParam("ccoefficient") int ccoefficient, @RequestParam("fcoefficient") int fcoefficient){
        UsState selectedState = stateService.getStatebyId(stateName);
        algoService.startAlgorithm(selectedState, pcoefficient, ccoefficient, fcoefficient, moves);
        return build200("Test");
    }

    @RequestMapping(value = "/updates",method = RequestMethod.GET)
    Response getMoves(){
        return build200(moves);
    }

}
