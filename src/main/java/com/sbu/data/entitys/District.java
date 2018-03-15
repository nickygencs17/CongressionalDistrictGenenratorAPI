
package com.sbu.data.entitys;

import java.util.ArrayList;

/**
 *
 * @author Rahul S Agasthya
 */
public class District extends Division {
    UsState usState;
    ArrayList<VoterDistrict> voterDistricts;
    
    public District(String id, String name, long population, String geoJsonFile, UsState initUsState) {
        super(id, name, population, geoJsonFile);
        usState = initUsState;
        voterDistricts = new ArrayList<>();
    }

    public UsState getUsState() {
        return usState;
    }

    public void setUsState(UsState usState) {
        this.usState = usState;
    }

    public ArrayList<VoterDistrict> getVoterDistricts() {
        return voterDistricts;
    }

    public void setVoterDistricts(ArrayList<VoterDistrict> voterDistricts) {
        this.voterDistricts = voterDistricts;
    }
}
