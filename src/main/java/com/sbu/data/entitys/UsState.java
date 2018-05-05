package com.sbu.data.entitys;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.main.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    @Transient
    @NotNull
    double objective;

    @NotNull
    int number_of_congress_districts;

    @Id
    String state_id;

    @Transient
    double populationDeviation;

    @Transient
    HashMap<String, CongressionalDistrict> congressionalDistricts;

    @Transient
    HashMap<String, Precinct> precincts;

    float compactness;

    public UsState() {
    }

    public UsState(String state_boundaries, String lower_boundaries, String upper_boundaries, String congress_boundaries,
                   int number_of_congress_districts, String state_id, float compactness) {
        this.state_boundaries = state_boundaries;
        this.lower_boundaries = lower_boundaries;
        this.upper_boundaries = upper_boundaries;
        this.congress_boundaries = congress_boundaries;
        this.number_of_congress_districts = number_of_congress_districts;
        this.state_id = state_id;
        this.compactness = compactness;
    }

    public float getCompactness() {
        return compactness;
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
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

    public HashMap<String, CongressionalDistrict> getCongressionalDistricts() {
        return congressionalDistricts;
    }

    public void setCongressionalDistricts(HashMap<String, CongressionalDistrict> congressionalDistricts) {
        this.congressionalDistricts = congressionalDistricts;
    }

    public double getPopulationDeviation() {
        return populationDeviation;
    }

    public void setPopulationDeviation(double populationDeviation) {
        this.populationDeviation = populationDeviation;
    }

    public CongressionalDistrict getCongressionalDistrictbyId(String id) {
        return congressionalDistricts.get(id);
    }

    public HashMap<String, Precinct> getPrecincts() {
        return precincts;
    }

    public void setCongressionalDistrictPrecincts(HashMap<String, Precinct> precincts) throws IOException {
        this.precincts = precincts;
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        for (String key : keys) {
            CongressionalDistrict district = congressionalDistricts.get(key);
            district.setPopulation(Constants.ZERO);
            district.createArea(Constants.CD);
            setPrecinctsforDistrict(district);
            district.updateBoundaryPrecincts();
        }
    }

    public void setPrecinctsforDistrict(CongressionalDistrict district) throws IOException {
        String precinctsIds = district.getPrecincts();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodes = mapper.readTree(precinctsIds);
        for (int i = 0; i < nodes.size(); i++) {
            String precinctId = nodes.get(i).asText();
            Precinct currentPrecinct = precincts.get(precinctId);
            currentPrecinct.createArea(Constants.VD);
            connectPrecinctDependencies(currentPrecinct);
            district.addPrecinct(currentPrecinct, false);
        }
    }

    public void connectPrecinctDependencies(Precinct currentPrecinct) throws IOException {
        if (currentPrecinct.isIs_inner()) return;
        String neighborPrecinctIds = currentPrecinct.getNeighbor_precincts();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodes = mapper.readTree(neighborPrecinctIds);
        for (int j = 0; j < nodes.size(); j++) {
            Precinct neighbor = precincts.get(nodes.get(j).asText());
            currentPrecinct.addNeighborPrecinct(neighbor);
        }
        String innerPrecinctIds = currentPrecinct.getInner_precincts();
        mapper = new ObjectMapper();
        nodes = mapper.readTree(innerPrecinctIds);
        for (int j = 0; j < nodes.size(); j++) {
            Precinct innerPrecinct = precincts.get(nodes.get(j).asText());
            currentPrecinct.addInnerPrecinct(innerPrecinct);
        }
    }

    public Precinct getPrecinctbyId(String id) {
        return precincts.get(id);
    }

    public double getObjective() {
        return objective;
    }

    public void setObjective(double objective) {
        this.objective = objective;
    }

    public void setIncludes(StartAlgoObject startAlgoObject) {
        List<String> excludedPrecincts = startAlgoObject.getExcluded_precinct_ids();
        for(int i = 0; i < excludedPrecincts.size(); i++) {
            Iterator<String> iterator = precincts.keySet().iterator();
            while(iterator.hasNext()) {
                Precinct thisPrecinct = precincts.get(iterator.next());
                if(thisPrecinct.getGeo_id().equals(excludedPrecincts.get(i))) {
                    thisPrecinct.setInclude(false);
                    break;
                }
            }
        }

        List<String> includedDistricts = startAlgoObject.getIncluded_districts_ids();
        for(int i = 0; i < includedDistricts.size(); i++) {
            congressionalDistricts.get(includedDistricts.get(i)).setInclude(true);
        }
    }

    public double calculateCompactness() {
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        double totalCompactness = 0;
        for (int i = 0; i < keys.length; i++) {
            totalCompactness += congressionalDistricts.get(keys[i]).calculateCompactness();
        }
        return totalCompactness / keys.length;
    }

    public double calculatePopulationDeviation() {
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        long highestPopulation = congressionalDistricts.get(keys[0]).getPopulation();
        long lowestPopulation = highestPopulation;
        long totalPopulation = 0;
        for (int i = 0; i < keys.length; i++) {
            long population = congressionalDistricts.get(keys[i]).getPopulation();
            totalPopulation += population;
            highestPopulation = Long.max(highestPopulation, population);
            lowestPopulation = Long.min(lowestPopulation, population);
        }
        this.setPopulationDeviation(((double) (highestPopulation - lowestPopulation) / (double) totalPopulation) * 100);
        return this.getPopulationDeviation();
    }

    public double calculatePoliticalFairness() {
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        double efficiencyGap = 0;
        for (int i = 0; i < keys.length; i++) {
            efficiencyGap += congressionalDistricts.get(keys[i]).calculateFairness();
        }
        efficiencyGap /= keys.length;
        return efficiencyGap;
    }

    public double calculateObjective(int cCoefficient, int fCoefficient) {
        return  cCoefficient * calculateCompactness() + fCoefficient * calculatePoliticalFairness();
    }
}
