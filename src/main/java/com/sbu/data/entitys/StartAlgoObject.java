package com.sbu.data.entitys;

import java.util.List;

public class StartAlgoObject {

    List<String> excluded_precinct_ids;
    List<String> included_districts_ids;
    float population_deviation;
    int c_coefficient;
    int f_coefficient;
    String state_id;

    public StartAlgoObject() {
    }

    public StartAlgoObject(List<String> excluded_precinct_ids, List<String> included_districts_ids, float population_deviation, int c_coefficient, int f_coefficient, String state_id) {
        this.excluded_precinct_ids = excluded_precinct_ids;
        this.included_districts_ids = included_districts_ids;
        this.population_deviation = population_deviation;
        this.c_coefficient = c_coefficient;
        this.f_coefficient = f_coefficient;
        this.state_id = state_id;
    }

    public StartAlgoObject(Redistrict savedRedistrict) {
        this.population_deviation = savedRedistrict.getPopulation_deviation();
        this.c_coefficient = (int) savedRedistrict.getC_coefficient();
        this.f_coefficient = (int) savedRedistrict.getF_coefficient();
        this.state_id = savedRedistrict.getState_id();
        this.excluded_precinct_ids = savedRedistrict.getexcluded_precincts_geo_ids();
        this.included_districts_ids = savedRedistrict.getIncluded_congressional_ids();
    }

    public List<String> getExcluded_precinct_ids() {
        return excluded_precinct_ids;
    }

    public void setExcluded_precinct_ids(List<String> excluded_precinct_ids) {
        this.excluded_precinct_ids = excluded_precinct_ids;
    }

    public List<String> getIncluded_districts_ids() {
        return included_districts_ids;
    }

    public void setIncluded_districts_ids(List<String> included_districts_ids) {
        this.included_districts_ids = included_districts_ids;
    }

    public float getPopulation_deviation() {
        return population_deviation;
    }

    public void setPopulation_deviation(float population_deviation) {
        this.population_deviation = population_deviation;
    }

    public int getC_coefficient() {
        return c_coefficient;
    }

    public void setC_coefficient(int c_coefficient) {
        this.c_coefficient = c_coefficient;
    }

    public int getF_coefficient() {
        return f_coefficient;
    }

    public void setF_coefficient(int f_coefficient) {
        this.f_coefficient = f_coefficient;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }
}
