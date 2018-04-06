package com.sbu.data.entitys;

public class CongressionalDirstrict {

    String congress_id;
    String voting_dirticts;
    long population;
    String state_id;
    boolean is_changed;

    public CongressionalDirstrict(String congress_id, String voting_dirticts, long population, String state_id, boolean is_changed) {
        this.congress_id = congress_id;
        this.voting_dirticts = voting_dirticts;
        this.population = population;
        this.state_id = state_id;
        this.is_changed = is_changed;
    }

    public CongressionalDirstrict() {
    }

    public String getCongress_id() {
        return congress_id;
    }

    public void setCongress_id(String congress_id) {
        this.congress_id = congress_id;
    }

    public String getVoting_dirticts() {
        return voting_dirticts;
    }

    public void setVoting_dirticts(String voting_dirticts) {
        this.voting_dirticts = voting_dirticts;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public boolean isIs_changed() {
        return is_changed;
    }

    public void setIs_changed(boolean is_changed) {
        this.is_changed = is_changed;
    }
}
