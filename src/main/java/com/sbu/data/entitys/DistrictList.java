
package com.sbu.data.entitys;

/**
 *
 * @author Rahul S Agasthya
 */

public class DistrictList {
    VoterDistrict district;
    DistrictNode neighbors;

    public DistrictList(VoterDistrict district, DistrictNode neighbors) {
        this.district = district;
        this.neighbors = neighbors;
    }

    public VoterDistrict getDistrict() {
        return district;
    }

    public void setDistrict(VoterDistrict district) {
        this.district = district;
    }

    public DistrictNode getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(DistrictNode neighbors) {
        this.neighbors = neighbors;
    }
}
