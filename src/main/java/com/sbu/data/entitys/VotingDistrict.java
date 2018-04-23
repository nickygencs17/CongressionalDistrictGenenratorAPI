package com.sbu.data.entitys;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "voting_districts")
public class VotingDistrict {

    @NotNull
    String state_id;

    @NotNull
    String congress_id;

    @Id
    @NotNull
    String vd_id;

    @Lob
    @NotNull
    String neighbor_vds;


    @NotNull
    Float d_leaning;

    @NotNull
    Float r_leaning;

    @NotNull
    Long population;

    @Lob
    @NotNull
    String vd_boundries;

    public VotingDistrict() {
    }

    public VotingDistrict(String state_id, String congress_id, String vd_id, String neighbor_vds, Float d_leaning, Float r_leaning, Long population, String vd_boundries) {
        this.state_id = state_id;
        this.congress_id = congress_id;
        this.vd_id = vd_id;
        this.neighbor_vds = neighbor_vds;
        this.d_leaning = d_leaning;
        this.r_leaning = r_leaning;
        this.population = population;
        this.vd_boundries = vd_boundries;
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

    public Float getD_leaning() {
        return d_leaning;
    }

    public void setD_leaning(Float d_leaning) {
        this.d_leaning = d_leaning;
    }

    public Float getR_leaning() {
        return r_leaning;
    }

    public void setR_leaning(Float r_leaning) {
        this.r_leaning = r_leaning;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getVd_boundries() {
        return vd_boundries;
    }

    public void setVd_boundries(String vd_boundries) {
        this.vd_boundries = vd_boundries;
    }
}
