package com.sbu.data.entitys;

public class VotingDistrict {


    String state_id;
    String congress_id;
    String vd_id;
    String neighbor_vds;
    float d_leaning;
    float r_leaning;
    long population;
    String vd_boundaries;


    public VotingDistrict(String state_id, String congress_id, String vd_id, String neighbor_vds, float d_leaning, float r_leaning, long population, String vd_boundaries) {
        this.state_id = state_id;
        this.congress_id = congress_id;
        this.vd_id = vd_id;
        this.neighbor_vds = neighbor_vds;
        this.d_leaning = d_leaning;
        this.r_leaning = r_leaning;
        this.population = population;
        this.vd_boundaries = vd_boundaries;
    }

    public VotingDistrict() {
    }


    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCongress_id() {
        return congress_id;
    }

    public void setCongress_id(String congress_id) {
        this.congress_id = congress_id;
    }

    public String getVd_id() {
        return vd_id;
    }

    public void setVd_id(String vd_id) {
        this.vd_id = vd_id;
    }

    public String getNeighbor_vds() {
        return neighbor_vds;
    }

    public void setNeighbor_vds(String neighbor_vds) {
        this.neighbor_vds = neighbor_vds;
    }

    public float getD_leaning() {
        return d_leaning;
    }

    public void setD_leaning(float d_leaning) {
        this.d_leaning = d_leaning;
    }

    public float getR_leaning() {
        return r_leaning;
    }

    public void setR_leaning(float r_leaning) {
        this.r_leaning = r_leaning;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getVd_boundaries() {
        return vd_boundaries;
    }

    public void setVd_boundaries(String vd_boundaries) {
        this.vd_boundaries = vd_boundaries;
    }
}
