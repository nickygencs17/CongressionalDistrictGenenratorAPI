package com.sbu.services;

import com.sbu.data.*;
import com.sbu.data.entitys.CongressionalDistrict;
import com.sbu.data.entitys.Precinct;
import com.sbu.data.entitys.UsState;
import com.sbu.main.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

@Component
public class StateService {


    @Autowired
    CongressElectionInfoRepository congressElectionInfoRepository;

    @Autowired
    CongressionalDistrictRepository congressionalDistrictRepository;

    @Autowired
    PrecinctRepository precinctRepository;

    @Autowired
    PresidentElectionInfoRepository presidentElectionInfoRepository;

    @Autowired
    CurrentOfficialRepository currentOfficialRepository;

    @Autowired
    UsStateRepository usStateRepository;


    public Object getBoundaries(String type, String state_id) {
        UsState usState = usStateRepository.findOne(state_id);
        if (type.equals(Constants.STATE)) {
            return usState.getState_boundaries();
        } else if (type.equals(Constants.LOWER)) {
            return usState.getLower_boundaries();
        } else if (type.equals(Constants.UPPER)) {
            return usState.getUpper_boundaries();
        } else {
            return usState.getCongress_boundaries();

        }
    }

    public Object getElectionInfo(String id) {
        JSONObject return_node = new JSONObject();
        return_node.put(Constants.CONGRESS_ELECTION_INFO, congressElectionInfoRepository.findByState_id(id));
        return_node.put(Constants.PRESIDENT_ELECTION_INFO, presidentElectionInfoRepository.findByState_id(id));
        return return_node;
    }

    public Object getStateInfo(String id) {
        JSONObject return_node = new JSONObject();
        UsState usState = usStateRepository.findOne(id);
        return_node.put(Constants.NUMBER_OF_CONGRESS_DISTRICTS, usState.getNumber_of_congress_districts());
        return_node.put(Constants.CURRENT_OFFICIALS, currentOfficialRepository.findByState_id(id));
        return return_node;
    }

    public UsState getStatebyId(String id) {
        UsState selectedState = usStateRepository.findOne(id);
        selectedState.setCongressionalDistricts(this.getCongressionalDistrictsbyState(selectedState.getState_id()));
        try {
            selectedState.setCongressionalDistrictPrecincts(this.getPrecinctsbyState(selectedState.getState_id()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedState;
    }

    public HashMap<String, CongressionalDistrict> getCongressionalDistrictsbyState(String id) {
        Iterator<CongressionalDistrict> districts = congressionalDistrictRepository.findByState_id(id).iterator();
        HashMap<String, CongressionalDistrict> districtHashMap = new HashMap<>();
        while (districts.hasNext()) {
            CongressionalDistrict currentDistrict = districts.next();
            districtHashMap.put(currentDistrict.getCongress_id(), currentDistrict);
        }
        return districtHashMap;
    }

    public HashMap<String, Precinct> getPrecinctsbyState(String id) {
        Iterator<Precinct> districts = precinctRepository.findByState_id(id).iterator();
        HashMap<String, Precinct> precinctHashMap = new HashMap<>();
        while (districts.hasNext()) {
            Precinct currentPrecinct = districts.next();
            precinctHashMap.put(currentPrecinct.getPrecinct_id(), currentPrecinct);
        }
        return precinctHashMap;
    }
}
