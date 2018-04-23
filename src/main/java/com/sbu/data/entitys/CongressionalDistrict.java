package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
    float compactness;

    @NotNull
    String state_id;

    @NotNull
    boolean is_changed;

    @Transient
    HashSet<String> precinct_ids;

    @Transient
    HashSet<String> boundaryPrecinct_ids;

    public CongressionalDistrict(String congress_id, String precincts, long population, String state_id, boolean is_changed) {
        this.congress_id = congress_id;
        this.precincts = precincts;
        this.population = population;
        this.state_id = state_id;
        this.is_changed = is_changed;
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

    public String getPricincts() {
        return precincts;
    }

    public void setPrecincts(String precincts) {
        this.precincts = precincts;
    }

    public HashSet<String> getBoundaryPrecinct_ids() {
        return boundaryPrecinct_ids;
    }

    public void setBoundaryPrecinct_ids(HashSet<String> boundaryPrecinct_ids) {
        this.boundaryPrecinct_ids = boundaryPrecinct_ids;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public boolean needsRevision() {
        return is_changed;
    }

    public void setNeedsRevision(boolean is_changed) {
        this.is_changed = is_changed;
    }

    public float getCompactness() {
        return compactness;
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }

    public boolean isIs_changed() {
        return is_changed;
    }

    public void setIs_changed(boolean is_changed) {
        this.is_changed = is_changed;
    }

    public void addPrecinct(String id) {
        this.precinct_ids.add(id);
    }

    public void removePrecinct(String id) {
        Iterator<String> iterator = precinct_ids.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().equals(id)) {
                precinct_ids.remove(id);
                break;
            }
        }
    }

    public void updateBoundaryPrecincts(VotingDistrict precinct) {

    }


}
