package com.sbu.controller;

import com.sbu.data.entitys.Log;
import com.sbu.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build401;
import static com.sbu.utils.Utils.checkIfUserIsAdmin;

@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogController {

    @Autowired
    private final InMemoryUserDetailsManager userManager;

    @Autowired
    LogService logService;

    @Autowired
    public LogController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @RequestMapping(method = RequestMethod.POST)
    Response postLog(@RequestBody @Valid Log log){
        if (handleAdminCall()) {
            return build401();
        }

        return build200(logService.postLog(log));
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    Response getAllLogs(){
        if (handleAdminCall()) {
            return build401();
        }

        return build200(logService.getAllLogs());
    }

    @RequestMapping(method = RequestMethod.GET)
    Response getSummary(){

        //TODO: what is this as well
        if (handleAdminCall()) {
            return build401();
        }

        return build200(logService.getAllLogs());
    }

    private boolean handleAdminCall() {
        String requesting_username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!checkIfUserIsAdmin(requesting_username,userManager)){
            return true;
        }
        return false;
    }

}
