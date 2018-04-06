package com.sbu.data.entitys;

public class UsState {



    String voting_district_ids;
    String state_boundries;
    String lower_boundries;
    String upper_boundries;
    String congress_boundries;
    int number_of_congress_districts;
    String state_id;

    public UsState() {
    }

    public UsState(String voting_district_ids, String state_boundries, String lower_boundries, String upper_boundries, String congress_boundries, int number_of_congress_districts, String state_id) {
        this.voting_district_ids = voting_district_ids;
        this.state_boundries = state_boundries;
        this.lower_boundries = lower_boundries;
        this.upper_boundries = upper_boundries;
        this.congress_boundries = congress_boundries;
        this.number_of_congress_districts = number_of_congress_districts;
        this.state_id = state_id;
    }

    public String getVoting_district_ids() {
        return voting_district_ids;
    }

    public void setVoting_district_ids(String voting_district_ids) {
        this.voting_district_ids = voting_district_ids;
    }

    public String getState_boundries() {
        return state_boundries;
    }

    public void setState_boundries(String state_boundries) {
        this.state_boundries = state_boundries;
    }

    public String getLower_boundries() {
        return lower_boundries;
    }

    public void setLower_boundries(String lower_boundries) {
        this.lower_boundries = lower_boundries;
    }

    public String getUpper_boundries() {
        return upper_boundries;
    }

    public void setUpper_boundries(String upper_boundries) {
        this.upper_boundries = upper_boundries;
    }

    public String getCongress_boundries() {
        return congress_boundries;
    }

    public void setCongress_boundries(String congress_boundries) {
        this.congress_boundries = congress_boundries;
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
