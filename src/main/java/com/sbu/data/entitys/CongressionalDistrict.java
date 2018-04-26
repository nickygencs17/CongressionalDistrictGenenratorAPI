package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    public boolean isContiguous(Precinct movePrecinct) {

        Iterator<Precinct> iterator = movePrecinct.getNeighborPrecinctSet().iterator();
        ArrayList<Precinct> finalSet = new ArrayList<>();
        while(iterator.hasNext()) {
            Precinct currentPrecinct = iterator.next();
            if(!currentPrecinct.getCongress_id().equals(this.congress_id)) continue;
            finalSet.add(currentPrecinct);
        }
        HashMap<String, Boolean> pathChecked = new HashMap<>();
        for(int i = 0; i < finalSet.size(); i++) {
            for(int j = 0; j < finalSet.size(); j++) {
                if(pathChecked.containsKey(pathChecked.get(finalSet.get(i).getPrecinct_id() + finalSet.get(j).getPrecinct_id()))) continue;
                if(i == j) continue;
                pathChecked.put(finalSet.get(i).getPrecinct_id() + finalSet.get(j).getPrecinct_id(), true);
                pathChecked.put(finalSet.get(j).getPrecinct_id() + finalSet.get(i).getPrecinct_id(), true);
                if(!isReachable(finalSet.get(i), finalSet.get(j))) return false;
            }
        }
        return true;
    }

    public boolean isReachable(Precinct start, Precinct end) {
        LinkedList<Integer>temp;

        HashMap<String, Boolean> visitedMap = new HashMap<>();
        Iterator<Precinct> iterator = precinctHashSet.iterator();
        while(iterator.hasNext()) {
            visitedMap.put(iterator.next().getPrecinct_id(), false);
        }
        // Create a queue for BFS
        LinkedList<Precinct> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        visitedMap.put(start.getPrecinct_id(), true);
        queue.add(start);

        // 'i' will be used to get all adjacent vertices of a vertex
        Iterator<Precinct> neighborsIterator;
        while (queue.size()!=0)
        {
            // Dequeue a vertex from queue and print it
            start = queue.poll();

            Precinct next;
            neighborsIterator = start.getNeighborPrecinctSet().iterator();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            while (neighborsIterator.hasNext())
            {
                next = neighborsIterator.next();
                if(!next.getCongress_id().equals(this.congress_id)) continue;

                // If this adjacent node is the destination node,
                // then return true
                if (next.getPrecinct_id().equals(end.getPrecinct_id()))
                    return true;

                // Else, continue to do BFS
                if (!visitedMap.get(next.getPrecinct_id()))
                {
                    visitedMap.put(next.getPrecinct_id(), true);
                    queue.add(next);
                }
            }
        }

        // If BFS is complete without visited d
        return false;
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
}
