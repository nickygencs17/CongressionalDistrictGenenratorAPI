package com.sbu.controller;

import com.sbu.main.Constants;
import com.sbu.services.PreProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build400;

@RestController
@CrossOrigin
@RequestMapping("/prep")
public class PreProcessController {

    @Autowired
    PreProcessingService preProcessingService;

    @RequestMapping(value = "/process", method = RequestMethod.PUT)
    Response putPreProcessing() throws IOException {

        if (preProcessingService.startPreprocessor()) {
            return build200(Constants.SUCCESS);
        }

        return build400(Constants.PREPROCESS_FAIL);

    }
}
