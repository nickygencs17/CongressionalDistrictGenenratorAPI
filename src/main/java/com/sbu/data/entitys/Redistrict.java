package com.sbu.data.entitys;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@Table(name = "saved_redistricting")
public class Redistrict {

    @NotNull
    String id;

    @NotNull
    String username;

    @NotNull
    float f_coefficient;

    @NotNull
    float c_coefficient;

    @NotNull
    float population_deviation;

    @NotNull
    String state_id;

    @NotNull
    String moves;

    @NotNull
    String included_congressional_districts;

    @NotNull
    String excluded_precincts;

    @NotNull
    Date timestamp;

    @Transient
    ArrayList<Move> movesList = new ArrayList<>();

    @Transient
    ArrayList<String> included_congressional_ids = new ArrayList<>();

    @Transient
    ArrayList<String> excluded_precincts_ids = new ArrayList<>();

    public Redistrict(String id, String username, float f_coefficient, float c_coefficient,
                      float population_deviation, String state_id, String moves, String included_congressional_districts,
                      String excluded_precincts, Date timestamp) {
        this.id = id;
        this.username = username;
        this.f_coefficient = f_coefficient;
        this.c_coefficient = c_coefficient;
        this.population_deviation = population_deviation;
        this.state_id = state_id;
        this.moves = moves;
        this.included_congressional_districts = included_congressional_districts;
        this.excluded_precincts = excluded_precincts;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getF_coefficient() {
        return f_coefficient;
    }

    public void setF_coefficient(float f_coefficient) {
        this.f_coefficient = f_coefficient;
    }

    public float getC_coefficient() {
        return c_coefficient;
    }

    public void setC_coefficient(float c_coefficient) {
        this.c_coefficient = c_coefficient;
    }

    public float getPopulation_deviation() {
        return population_deviation;
    }

    public void setPopulation_deviation(float population_deviation) {
        this.population_deviation = population_deviation;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public String getIncluded_congressional_districts() {
        return included_congressional_districts;
    }

    public void setIncluded_congressional_districts(String included_congressional_districts) {
        this.included_congressional_districts = included_congressional_districts;
    }

    public String getExcluded_precincts() {
        return excluded_precincts;
    }

    public void setExcluded_precincts(String excluded_precincts) {
        this.excluded_precincts = excluded_precincts;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Move> getMovesList() {
        return movesList;
    }

    public void setMovesList(ArrayList<Move> movesList) {
        this.movesList = movesList;
    }

    public ArrayList<String> getIncluded_congressional_ids() {
        return included_congressional_ids;
    }

    public void setIncluded_congressional_ids(ArrayList<String> included_congressional_ids) {
        this.included_congressional_ids = included_congressional_ids;
    }

    public ArrayList<String> getExcluded_precincts_ids() {
        return excluded_precincts_ids;
    }

    public void setExcluded_precincts_ids(ArrayList<String> excluded_precincts_ids) {
        this.excluded_precincts_ids = excluded_precincts_ids;
    }
}