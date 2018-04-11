package com.sbu.services;


import com.sbu.data.LogRepository;
import com.sbu.data.entitys.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogService {


    @Autowired
    LogRepository logRepository;
    public Object postLog(Log log) {

        return logRepository.save(log);
    }

    public Object getAllLogs() {
        return logRepository.findAll();
    }

    public Object getSummary() {

        //TODO: Summary?
        return logRepository.findAll();

    }
}
