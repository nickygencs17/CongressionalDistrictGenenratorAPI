package com.sbu.data.entitys;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.main.Constants;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.geojson.Polygon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.geom.Area;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "precinct")
public class Precinct {

    @NotNull
    String state_id;

    @NotNull
    String congress_id;

    @Id
    String precinct_id;

    @Lob
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

    @NotNull
    String precinct_name;

    @NotNull
    String geo_id;

    @NotNull
    float perimeter;

    @NotNull
    float area;

    @NotNull
    float compactness;

    @Transient
    HashSet<Precinct> neighborPrecinctSet = new HashSet<>();

    @Transient
    Area areaObject;

    public Precinct(String state_id, String congress_id, String precinct_id, String neighbor_precincts, float d_leaning, float r_leaning, long population, String precinct_boundaries, String precinct_name, String geo_id, float perimeter, float area, float compactness) {
        this.state_id = state_id;
        this.congress_id = congress_id;
        this.precinct_id = precinct_id;
        this.neighbor_precincts = neighbor_precincts;
        this.d_leaning = d_leaning;
        this.r_leaning = r_leaning;
        this.population = population;
        this.precinct_boundaries = precinct_boundaries;
        this.precinct_name = precinct_name;
        this.geo_id = geo_id;
        this.perimeter = perimeter;
        this.area = area;
        this.compactness = compactness;
    }

    public Precinct() {
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

    public String getPrecinct_id() {
        return precinct_id;
    }

    public void setPrecinct_id(String precinct_id) {
        this.precinct_id = precinct_id;
    }

    public String getNeighbor_precincts() {
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

    public String getPrecinct_boundaries() {
        return precinct_boundaries;
    }

    public void setPrecinct_boundaries(String precinct_boundaries) {
        this.precinct_boundaries = precinct_boundaries;
    }

    public String getPrecinct_name() {
        return precinct_name;
    }

    public void setPrecinct_name(String precinct_name) {
        this.precinct_name = precinct_name;
    }

    public String getGeo_id() {
        return geo_id;
    }

    public void setGeo_id(String geo_id) {
        this.geo_id = geo_id;
    }

    public float getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(float perimeter) {
        this.perimeter = perimeter;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getCompactness() {
        return compactness;
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }

    public HashSet<Precinct> getNeighborPrecinctSet() {
        return neighborPrecinctSet;
    }

    public void setNeighborPrecinctSet(HashSet<Precinct> neighborPrecinctSet) {
        this.neighborPrecinctSet = neighborPrecinctSet;
    }
    public void addNeighborPrecinct(Precinct precinct) {
        neighborPrecinctSet.add(precinct);
    }

    public Area getAreaObject() {
        return areaObject;
    }

    public void createArea(String type) throws IOException {

        String dir = System.getProperty("user.dir");
        FileReader reader = new FileReader(dir + "/src/main/resources/" + this.precinct_boundaries);
        Feature location = new ObjectMapper().readValue(reader, Feature.class);
        List<LngLatAlt> locationLngLatAlt;
        if(type.equalsIgnoreCase(Constants.VD)){
            locationLngLatAlt = ((MultiPolygon)location.getGeometry()).getCoordinates().get(0).get(0);
        }
        else{
            locationLngLatAlt = new ArrayList<>();
        }
        java.awt.Polygon locationPolygon = new java.awt.Polygon();
        for(LngLatAlt point: locationLngLatAlt){
            locationPolygon.addPoint((int)(point.getLongitude()*1000000), (int)(point.getLatitude()*1000000));
        }
        this.areaObject = new Area(locationPolygon);
    }

}
