package com.sbu.data.entitys;

import java.util.ArrayList;

/**
 *
 * @author Rahul S Agasthya
 */
public class VoterDistrictsGraph {
    ArrayList<DistrictList> districts;

    public VoterDistrictsGraph(ArrayList<DistrictList> districts) {
        this.districts = districts;
    }

    public ArrayList<DistrictList> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<DistrictList> districts) {
        this.districts = districts;
    }
}
