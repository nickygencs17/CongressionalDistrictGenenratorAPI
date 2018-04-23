package com.sbu.data.entitys;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
@Table(name = "precincts")
public class VotingDistrict {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @NotNull
    UsState state_id;

    @NotNull
    String congress_id;

    @Id
    String precinct_id;

    @NotNull
    String neighbor_precincts;

    @NotNull
    float d_leaning;

    @NotNull
    float r_leaning;

    @NotNull
    long population;

    @NotNull
    String precinct_boundaries;

    @Transient
    HashSet<String> neighbor_precinct_ids;

    public VotingDistrict(UsState state_id, String congress_id, String precinct_id, String neighbor_precincts, float d_leaning, float r_leaning, long population, String precinct_boundaries) {
        this.state_id = state_id;
        this.congress_id = congress_id;
        this.precinct_id = precinct_id;
        this.neighbor_precincts = neighbor_precincts;
        this.d_leaning = d_leaning;
        this.r_leaning = r_leaning;
        this.population = population;
        this.precinct_boundaries = precinct_boundaries;
    }

    public UsState getState_id() {
        return state_id;
    }

    public void setState_id(UsState state_id) {
        this.state_id = state_id;
    }

    public String getCongressionalDistrict_id() {
        return congress_id;
    }

    public void setCongress_id(String congress_id) {
        this.congress_id = congress_id;
    }

    public String getPrecinct_id() {
        return precinct_id;
    }

    public void setPrecinct_id(String precinct_id) {
        this.precinct_id = precinct_id;
    }

    public String getNeighbor_vds() {
        return neighbor_precincts;
    }

    public void setNeighbor_precincts(String neighbor_precincts) {
        this.neighbor_precincts = neighbor_precincts;
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

    public void setPrecinct_boundaries(String precinct_boundaries) {
        this.precinct_boundaries = precinct_boundaries;
    }

    public String getCongress_id() {
        return congress_id;
    }

    public String getNeighbor_precincts() {
        return neighbor_precincts;
    }

    public String getPrecinct_boundaries() {
        return precinct_boundaries;
    }

    public HashSet<String> getNeighbor_precinct_ids() {
        return neighbor_precinct_ids;
    }

    public void setNeighbor_precinct_ids(HashSet<String> neighbor_precinct_ids) {
        this.neighbor_precinct_ids = neighbor_precinct_ids;
    }
}
