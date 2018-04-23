package com.sbu.data.entitys;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "congressional_districts")
public class CongressionalDistrict {

    @Id
    String congress_id;

    @NotNull
    String precincts;

    @NotNull
    long population;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    UsState state_id;

    boolean is_changed;

    int in_use;

    float compactness;

    public CongressionalDistrict(String congress_id, String precincts, long population, UsState state_id, boolean is_changed, int in_use, float compactness) {
        this.congress_id = congress_id;
        this.precincts = precincts;
        this.population = population;
        this.state_id = state_id;
        this.is_changed = is_changed;
        this.in_use = in_use;
        this.compactness = compactness;
    }

    public CongressionalDistrict() {
    }


    public int getIn_use() {
        return in_use;
    }

    public void setIn_use(int in_use) {
        this.in_use = in_use;
    }

    public float getCompactness() {
        return compactness;
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
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

    public String getPrecincts() {
        return precincts;
    }

    public void setPrecincts(String precincts) {
        this.precincts = precincts;
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
