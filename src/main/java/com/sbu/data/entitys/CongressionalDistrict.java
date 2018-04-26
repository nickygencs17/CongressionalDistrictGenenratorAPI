package com.sbu.data.entitys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.main.Constants;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.geojson.Polygon;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
    String state_id;

    @NotNull
    String color;

    @NotNull
    boolean is_changed;

    @Transient
    HashSet<Precinct> precinctHashSet = new HashSet<>();

    @Transient
    HashSet<Precinct> boundaryPrecinctHashSet = new HashSet<>();

    @NotNull
    int in_use;

    @NotNull
    float compactness;

    public CongressionalDistrict(String congress_id, String precincts, long population, String state_id,
                                 boolean is_changed, int in_use, float compactness, String color) {

        this.congress_id = congress_id;
        this.precincts = precincts;
        this.population = population;
        this.state_id = state_id;
        this.is_changed = is_changed;
        this.in_use = in_use;
        this.compactness = compactness;
        this.color = color;
    }


    public String getState_id() {
        return state_id;
    }

    public CongressionalDistrict() {
    }


    public boolean in_use() {
        return in_use == 1;
    }

    public void setIn_use(int in_use) {
        this.in_use = in_use;
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


    public String getPrecincts() {

        return precincts;
    }

    public void setPrecincts(String precincts) {
        this.precincts = precincts;
    }

    public HashSet<Precinct> getBoundaryPrecinctHashSet() {

        return boundaryPrecinctHashSet;
    }

    public void setBoundaryPrecinctHashSet(HashSet<Precinct> boundaryPrecinctHashSet) {
        this.boundaryPrecinctHashSet = boundaryPrecinctHashSet;

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

    public float getActualCompactness() {
        return compactness / precinctHashSet.size();
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }

    public boolean isContiguous() {
        Iterator<Precinct> precinctIterator = this.precinctHashSet.iterator();
        while(precinctIterator.hasNext()) {
            Precinct currentPrecinct = precinctIterator.next();
            Iterator<Precinct> neighborIterator = currentPrecinct.getNeighborPrecinctSet().iterator();
            int neighbours = 0;
            while(neighborIterator.hasNext()) {
                if(precinctHashSet.contains(neighborIterator.next())) {
                    neighbours++;
                    break;
                }
            }
            if(neighbours == 0) return false;
        }
        return true;
    }


    public void addPrecinct(Precinct precinct) {
        precinct.setCongress_id(this.congress_id);
        this.precinctHashSet.add(precinct);
        this.population += precinct.getPopulation();
        updateCompactness(precinct, true);
    }

    public void removePrecinct(Precinct precinct) {
        if(precinctHashSet.contains(precinct)) {
            precinctHashSet.remove(precinct);
            this.population -= precinct.getPopulation();
            updateCompactness(precinct, false);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void updateBoundaryPrecincts() {
        boundaryPrecinctHashSet.clear();
        Iterator<Precinct> precinctIterator = this.precinctHashSet.iterator();
        //System.out.println("currentCongress : " + congress_id);
        while(precinctIterator.hasNext()) {
            Precinct currentPrecinct = precinctIterator.next();
            // System.out.println("currentPrecinct : " + currentPrecinct.getPrecinct_id());
            if(currentPrecinct == null) {
                System.out.println("NULL");
            }
            Iterator<Precinct> neighborIterator = currentPrecinct.getNeighborPrecinctSet().iterator();
            while(neighborIterator.hasNext()) {
                if(!neighborIterator.next().getCongress_id().equals(currentPrecinct.getCongress_id())) {
                    boundaryPrecinctHashSet.add(currentPrecinct);
                    break;
                }
            }
        }
    }

    public void updateCompactness(Precinct precinct, boolean addition) {
        compactness = (addition) ? compactness + precinct.getCompactness() : compactness - precinct.getCompactness();
    }

    public float getPerimeter(Feature border){
        List<LngLatAlt> points = ((Polygon)border.getGeometry()).getExteriorRing();
        float perim = 0;
        for(int i = 0; i < points.size()-1; i++){
            perim += Math.sqrt(Math.pow(points.get(i+1).getLongitude()-points.get(i).getLongitude(), 2) + Math.pow(points.get(i+1).getLatitude()-points.get(i).getLatitude(), 2));
        }
        return perim;
    }

    public Area createArea(String districtLocation, String type) throws IOException {

        FileReader reader = new FileReader(districtLocation);
        Feature location = new ObjectMapper().readValue(reader, Feature.class);

        //Geojson polygon or multipolygon
        List<LngLatAlt> locationLngLatAlt;
        if(type.equalsIgnoreCase(Constants.VD)){
            locationLngLatAlt = ((MultiPolygon)location.getGeometry()).getCoordinates().get(0).get(0);
        }
        else if(type.equalsIgnoreCase(Constants.CD)){
            locationLngLatAlt = ((Polygon)location.getGeometry()).getExteriorRing();
        }
        else{
            locationLngLatAlt = new ArrayList<>();
        }

        //java.awt Polygons
        java.awt.Polygon locationPolygon = new java.awt.Polygon();

        for(LngLatAlt point: locationLngLatAlt){
            locationPolygon.addPoint((int)(point.getLongitude()*1000000), (int)(point.getLatitude()*1000000));
        }

        return new Area(locationPolygon);
    }

    public Feature createFeature(Area toConvert) throws IOException {

        PathIterator lngPath = toConvert.getPathIterator(null);
        List<LngLatAlt> lngFinal = new ArrayList<>();
        while(!lngPath.isDone()){
            float[] point = new float[6];
            int numPoints = lngPath.currentSegment(point);
            switch(numPoints){
                case 0:
                    lngFinal.add((new LngLatAlt(point[0]/1000000, point[1]/1000000)));
                    break;
                case 1:
                    lngFinal.add((new LngLatAlt(point[0]/1000000, point[1]/1000000)));
                    break;
                case 2:
                    lngFinal.add((new LngLatAlt(point[0]/1000000, point[1]/1000000)));
                    lngFinal.add((new LngLatAlt(point[2]/1000000, point[3]/1000000)));
                    break;
                case 3:
                    lngFinal.add((new LngLatAlt(point[0]/1000000, point[1]/1000000)));
                    lngFinal.add((new LngLatAlt(point[2]/1000000, point[3]/1000000)));
                    lngFinal.add((new LngLatAlt(point[4]/1000000, point[5]/1000000)));
                    break;
                default:
                    break;
            }
            lngPath.next();
        }

        Feature featureFinal = new Feature();
        org.geojson.Polygon polyFinal = new org.geojson.Polygon();
        polyFinal.setExteriorRing(lngFinal);
        featureFinal.setGeometry(polyFinal);
        return featureFinal;
    }
}
