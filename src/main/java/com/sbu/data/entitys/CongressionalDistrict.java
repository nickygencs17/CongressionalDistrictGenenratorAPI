package com.sbu.data.entitys;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "congressional_districts")
public class CongressionalDistrict {

    @Id
    String congress_id;

    @NotNull
    String voting_districts;

    @NotNull
    long population;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    UsState state_id;


    boolean is_changed;


    public CongressionalDistrict(String congress_id, String voting_districts, long population, UsState state_id, boolean is_changed) {
        this.congress_id = congress_id;
        this.voting_districts = voting_districts;
        this.population = population;
        this.state_id = state_id;
        this.is_changed = is_changed;
    }

    public CongressionalDistrict() {
    }

    public UsState getState_id() {
        return state_id;
    }

    public void setState_id(UsState state_id) {
        this.state_id = state_id;
    }

    public String getCongress_id() {
        return congress_id;
    }

    public void setCongress_id(String congress_id) {
        this.congress_id = congress_id;
    }

    public String getVoting_districts() {
        return voting_districts;
    }

    public void setVoting_districts(String voting_districts) {
        this.voting_districts = voting_districts;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }


    public boolean isIs_changed() {
        return is_changed;
    }

    public void setIs_changed(boolean is_changed) {
        this.is_changed = is_changed;
    }
}
