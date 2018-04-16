package com.sbu.controller;
import com.sbu.main.Constants;
import com.sbu.services.PreprocessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import java.io.IOException;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build400;

@RestController
@CrossOrigin
@RequestMapping("/prep")
public class PreprocessController {

    @Autowired
    PreprocessService preprocessService;

    @RequestMapping(value = "/process",method = RequestMethod.PUT)
    Response putPreprocessing(){
        boolean finish;
        try {
            finish = (boolean)preprocessService.startPreprocess();
        }
        catch(IOException e){
            return build400(Constants.PREPROCESS_FAIL);
        }
        if(finish){
            return build200("Success");
        }
        else{
            return build400(Constants.PREPROCESS_FAIL);
        }

    }
}
