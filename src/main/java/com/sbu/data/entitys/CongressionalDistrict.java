package com.sbu.data.entitys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.main.Constants;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

    @Transient
    Area areaObject;

    @Transient
    float area;

    @NotNull
    int in_use;

    @NotNull
    double compactness;

    @NotNull
    String boundaries;

    public CongressionalDistrict(String congress_id, String precincts, long population, String state_id,
                                 boolean is_changed, int in_use, float compactness, String color, String boundaries) {

        this.congress_id = congress_id;
        this.precincts = precincts;
        this.population = population;
        this.state_id = state_id;
        this.is_changed = is_changed;
        this.in_use = in_use;
        this.compactness = compactness;
        this.color = color;
        this.boundaries = boundaries;
    }

    public CongressionalDistrict() {
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public boolean in_use() {
        return in_use == 1;
    }

    public void setIn_use(int in_use) {
        this.in_use = in_use;
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

    public double getCompactness() {
        return this.compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }

    public double calculateCompactness() {
        double r = Math.sqrt((area / 2589988) / Math.PI);
        double equalAreaPerimeter = 2 * Math.PI * r;
        double perimeter = getPerimeter();
        return 1 / (perimeter / equalAreaPerimeter);
    }

    public boolean isContiguous(Precinct movePrecinct) {
        Iterator<Precinct> iterator = movePrecinct.getNeighborPrecinctSet().iterator();
        ArrayList<Precinct> finalSet = new ArrayList<>();
        while (iterator.hasNext()) {
            Precinct currentPrecinct = iterator.next();
            if (!currentPrecinct.getCongress_id().equals(this.congress_id)) continue;
            finalSet.add(currentPrecinct);
        }
        HashMap<String, Boolean> pathChecked = new HashMap<>();
        for (int i = 0; i < finalSet.size(); i++) {
            for (int j = 0; j < finalSet.size(); j++) {
                if (pathChecked.containsKey(pathChecked.get(finalSet.get(i).getPrecinct_id() + finalSet.get(j).getPrecinct_id())))
                    continue;
                if (i == j) continue;
                pathChecked.put(finalSet.get(i).getPrecinct_id() + finalSet.get(j).getPrecinct_id(), true);
                pathChecked.put(finalSet.get(j).getPrecinct_id() + finalSet.get(i).getPrecinct_id(), true);
                if (!isReachable(finalSet.get(i), finalSet.get(j))) return false;
            }
        }
        return true;
    }

    public boolean isReachable(Precinct start, Precinct end) {
        LinkedList<Integer> temp;
        HashMap<String, Boolean> visitedMap = new HashMap<>();
        Iterator<Precinct> iterator = precinctHashSet.iterator();
        while (iterator.hasNext()) {
            visitedMap.put(iterator.next().getPrecinct_id(), false);
        }
        LinkedList<Precinct> queue = new LinkedList<>();
        visitedMap.put(start.getPrecinct_id(), true);
        queue.add(start);
        Iterator<Precinct> neighborsIterator;
        while (queue.size() != 0) {
            start = queue.poll();
            Precinct next;
            neighborsIterator = start.getNeighborPrecinctSet().iterator();
            while (neighborsIterator.hasNext()) {
                next = neighborsIterator.next();
                if (!next.getCongress_id().equals(this.congress_id)) continue;
                if (next.getPrecinct_id().equals(end.getPrecinct_id()))
                    return true;
                if (!visitedMap.get(next.getPrecinct_id())) {
                    visitedMap.put(next.getPrecinct_id(), true);
                    queue.add(next);
                }
            }
        }
        return false;
    }

    public void addPrecinct(Precinct precinct, boolean updateAreaObj) {
        precinct.setCongress_id(this.congress_id);
        this.precinctHashSet.add(precinct);
        Iterator<Precinct> iterator = precinct.getInnerPrecinctSet().iterator();
        while (iterator.hasNext()) {
            Precinct innerPrecinct = iterator.next();
            innerPrecinct.setCongress_id(this.congress_id);
            this.precinctHashSet.add(innerPrecinct);
        }
        this.population += precinct.getPopulation();
        if (updateAreaObj) updateAreaObject(precinct.getAreaObject(), true);
        updateArea(precinct.getArea(), true);
    }

    public void removePrecinct(Precinct precinct) {
        if (precinctHashSet.contains(precinct)) {
            precinctHashSet.remove(precinct);
            Iterator<Precinct> iterator = precinct.getInnerPrecinctSet().iterator();
            while (iterator.hasNext()) {
                Precinct innerPrecinct = iterator.next();
                this.precinctHashSet.remove(innerPrecinct);
            }
            this.population -= precinct.getPopulation();
            updateAreaObject(precinct.getAreaObject(), false);
            updateArea(precinct.getArea(), false);
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
        while (precinctIterator.hasNext()) {
            Precinct currentPrecinct = precinctIterator.next();
            Iterator<Precinct> neighborIterator = currentPrecinct.getNeighborPrecinctSet().iterator();
            while (neighborIterator.hasNext()) {
                if (!neighborIterator.next().getCongress_id().equals(currentPrecinct.getCongress_id())) {
                    boundaryPrecinctHashSet.add(currentPrecinct);
                    break;
                }
            }
        }
    }

    public void updateCompactness(Precinct precinct, boolean addition) {
        compactness = (addition) ? compactness + precinct.getCompactness() : compactness - precinct.getCompactness();
    }

    public double getPerimeter() {
        Feature border = null;
        try {
            border = createFeature(this.areaObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<LngLatAlt> points = ((Polygon) border.getGeometry()).getExteriorRing();
        double perimeter = 0;
        double latMiles, lngMiles;
        if (this.state_id.equalsIgnoreCase(Constants.ARKANSAS)) {
            latMiles = 68.935125;
            lngMiles = 56.723705;
        } else if (this.state_id.equalsIgnoreCase(Constants.INDIANA)) {
            latMiles = 68.993567;
            lngMiles = 53.061157;
        } else {
            latMiles = 68.969859;
            lngMiles = 54.576432;
        }
        for (int i = 0; i < points.size() - 1; i++) {
            perimeter += Math.sqrt(Math.pow(lngMiles * (points.get(i + 1).getLongitude() - points.get(i).getLongitude()), 2) + Math.pow(latMiles * (points.get(i + 1).getLatitude() - points.get(i).getLatitude()), 2));
        }
        return perimeter;
    }

    public void createArea(String type) throws IOException {

        String dir = System.getProperty("user.dir");
        FileReader reader = new FileReader(dir + "/src/main/resources/" + this.boundaries);
        Feature location = new ObjectMapper().readValue(reader, Feature.class);
        List<LngLatAlt> locationLngLatAlt;
        if (type.equalsIgnoreCase(Constants.CD)) {
            locationLngLatAlt = ((Polygon) location.getGeometry()).getExteriorRing();
        } else {
            locationLngLatAlt = new ArrayList<>();
        }
        java.awt.Polygon locationPolygon = new java.awt.Polygon();
        for (LngLatAlt point : locationLngLatAlt) {
            locationPolygon.addPoint((int) (point.getLongitude() * 1000000), (int) (point.getLatitude() * 1000000));
        }
        this.areaObject = new Area(locationPolygon);
    }

    public void updateAreaObject(Area precinctArea, boolean additon) {
        if (additon) this.areaObject.add(precinctArea);
        else this.areaObject.subtract(precinctArea);
    }

    public void updateArea(float precinctArea, boolean additon) {
        this.area = (additon) ? area + precinctArea : area - precinctArea;
    }

    public Area getAreaObject() {
        return this.areaObject;
    }

    public float getArea() {
        return this.area;
    }

    public Feature createFeature(Area toConvert) throws IOException {

        PathIterator lngPath = toConvert.getPathIterator(null);
        List<LngLatAlt> lngFinal = new ArrayList<>();
        while (!lngPath.isDone()) {
            float[] point = new float[6];
            int numPoints = lngPath.currentSegment(point);
            switch (numPoints) {
                case 0:
                    lngFinal.add((new LngLatAlt(point[0] / 1000000, point[1] / 1000000)));
                    break;
                case 1:
                    lngFinal.add((new LngLatAlt(point[0] / 1000000, point[1] / 1000000)));
                    break;
                case 2:
                    lngFinal.add((new LngLatAlt(point[0] / 1000000, point[1] / 1000000)));
                    lngFinal.add((new LngLatAlt(point[2] / 1000000, point[3] / 1000000)));
                    break;
                case 3:
                    lngFinal.add((new LngLatAlt(point[0] / 1000000, point[1] / 1000000)));
                    lngFinal.add((new LngLatAlt(point[2] / 1000000, point[3] / 1000000)));
                    lngFinal.add((new LngLatAlt(point[4] / 1000000, point[5] / 1000000)));
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

    public double calculateFairness() {
        Iterator<Precinct> precinctIterator = precinctHashSet.iterator();
        float demFloat = 0, repFloat = 0;
        while(precinctIterator.hasNext()){
            Precinct currentPrecinct = precinctIterator.next();
            demFloat += currentPrecinct.getPopulation()*currentPrecinct.getD_leaning();
            repFloat += currentPrecinct.getPopulation()*currentPrecinct.getR_leaning();
        }
        int demVotes = (int)demFloat;
        int repVotes = (int)repFloat;
        int totalVotesLosingParty, totalVotesCast, wastedVotesWinning;
        totalVotesCast = demVotes + repVotes;
        if(demVotes < repVotes){
            totalVotesLosingParty = demVotes;
            wastedVotesWinning = (int)Math.abs(repVotes - 0.5 * (totalVotesCast));
        }
        else{
            totalVotesLosingParty = repVotes;
            wastedVotesWinning = (int)Math.abs(demVotes - 0.5 * (totalVotesCast));
        }
        return (1 - ((double) (Math.abs(totalVotesLosingParty - wastedVotesWinning)) / totalVotesCast));
    }
}
