package com.sbu.data.entitys;



public class UsState {



    String voting_district_ids;
    String state_boundaries;
    String lower_boundaries;
    String upper_boundaries;
    String congress_boundaries;
    int number_of_congress_districts;
    String state_id;

    public UsState() {
    }

    public UsState(String voting_district_ids, String state_boundaries, String lower_boundaries, String upper_boundaries, String congress_boundaries, int number_of_congress_districts, String state_id) {
        this.voting_district_ids = voting_district_ids;
        this.state_boundaries = state_boundaries;
        this.lower_boundaries = lower_boundaries;
        this.upper_boundaries = upper_boundaries;
        this.congress_boundaries = congress_boundaries;
        this.number_of_congress_districts = number_of_congress_districts;
        this.state_id = state_id;
    }

    public String getVoting_district_ids() {
        return voting_district_ids;
    }

    public void setVoting_district_ids(String voting_district_ids) {
        this.voting_district_ids = voting_district_ids;
    }

    public String getState_boundaries() {
        return state_boundaries;
    }

    public void setState_boundaries(String state_boundaries) {
        this.state_boundaries = state_boundaries;
    }

    public String getLower_boundaries() {
        return lower_boundaries;
    }

    public void setLower_boundaries(String lower_boundaries) {
        this.lower_boundaries = lower_boundaries;
    }

    public String getUpper_boundaries() {
        return upper_boundaries;
    }

    public void setUpper_boundaries(String upper_boundaries) {
        this.upper_boundaries = upper_boundaries;
    }

    public String getCongress_boundaries() {
        return congress_boundaries;
    }

    public void setCongress_boundaries(String congress_boundaries) {
        this.congress_boundaries = congress_boundaries;
    }

    public int getNumber_of_congress_districts() {
        return number_of_congress_districts;
    }

    public void setNumber_of_congress_districts(int number_of_congress_districts) {
        this.number_of_congress_districts = number_of_congress_districts;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }
}
