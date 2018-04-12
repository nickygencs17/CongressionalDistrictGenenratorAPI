package com.sbu.services;
import com.sbu.data.UsStateRepository;
import com.sbu.data.entitys.UsState;
import com.sbu.main.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateService {

    @Autowired
    UsStateRepository usStateRepository;

    public Object getBoundaries(String type, String state_id) {
        UsState usState = usStateRepository.findOne(state_id);
        if(type.equals(Constants.STATE)){
            return usState.getState_boundaries();
        }
        else if(type.equals(Constants.LOWER)){
            return usState.getLower_boundaries();
        }
        else if(type.equals(Constants.UPPER)){
            return usState.getUpper_boundaries();
        }
        else {
            return usState.getCongress_boundaries();

        }
    }
}
