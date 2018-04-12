package com.sbu.data.entitys;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "us_state")
public class UsState {


    @Lob
    @NotNull
    String state_boundaries;

    @Lob
    @NotNull
    String lower_boundaries;

    @Lob
    @NotNull
    String upper_boundaries;

    @Lob
    @NotNull
    String congress_boundaries;

    @NotNull
    int number_of_congress_districts;

    @Id
    String state_id;

    public UsState() {
    }

    public UsState(String state_boundaries, String lower_boundaries, String upper_boundaries, String congress_boundaries, int number_of_congress_districts, String state_id) {

        this.state_boundaries = state_boundaries;
        this.lower_boundaries = lower_boundaries;
        this.upper_boundaries = upper_boundaries;
        this.congress_boundaries = congress_boundaries;
        this.number_of_congress_districts = number_of_congress_districts;
        this.state_id = state_id;
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
