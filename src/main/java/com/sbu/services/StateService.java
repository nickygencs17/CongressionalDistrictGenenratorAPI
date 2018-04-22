package com.sbu.services;
import com.sbu.data.CongressElectionInfoRepository;
import com.sbu.data.CurrentOfficialRepository;
import com.sbu.data.PresidentElectionInfoRepository;
import com.sbu.data.UsStateRepository;
import com.sbu.data.entitys.UsState;
import com.sbu.main.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateService {


    @Autowired
    CongressElectionInfoRepository congressElectionInfoRepository;

    @Autowired
    PresidentElectionInfoRepository presidentElectionInfoRepository;

    @Autowired
    CurrentOfficialRepository currentOfficialRepository;

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

    public Object getElectionInfo(String id) {
        JSONObject return_node = new JSONObject();
        return_node.put(Constants.CONGRESS_ELECTION_INFO,congressElectionInfoRepository.findByState_id(id));
        return_node.put(Constants.PRESIDENT_ELECTION_INFO,presidentElectionInfoRepository.findByState_id(id));
        return return_node;


    }

    public Object getStateInfo(String id) {
        JSONObject return_node = new JSONObject();
        UsState usState = usStateRepository.findOne(id);
        return_node.put(Constants.NUMBER_OF_CONGRESS_DISTRICTS,usState.getNumber_of_congress_districts());
        return_node.put(Constants.CURRENT_OFFICIALS,currentOfficialRepository.findByState_id(id));
        return return_node;
    }

    public UsState getStatebyId(String id) {
        return usStateRepository.findOne(id);
    }

}
