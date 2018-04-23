package com.sbu.services;


import com.sbu.data.entitys.*;
import com.sbu.main.Constants;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class AlgoService {

    ArrayList<Move> moves;
    Update update;
    int unChangedChecks;
    int maxMovesPerUpdate;
    int pCoefficient;
    int cCoefficient;
    int fCoefficient;
    boolean isFinished;
    HashMap<String, CongressionalDistrict> congressionalDistricts;
    HashMap<String, Precinct> precincts;
    public Update startAlgorithm(UsState state, int pCoefficient, int cCoefficient, int fCoefficient) {

        this.moves = new ArrayList<>();
        this.update = new Update(moves);
        this.pCoefficient = pCoefficient;
        this.cCoefficient = cCoefficient;
        this.fCoefficient = fCoefficient;
        this.isFinished = false;
        unChangedChecks = 0;
        maxMovesPerUpdate = 0;
        //Get all Congressional districts that share boundary with other congressional districts
        this.congressionalDistricts = state.getCongressionalDistricts();
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        while(!checkTermination()) {
            sortByPoulation(congressionalDistricts, keys);
            for(int i = 0; i < keys.length; i++) {
                //Loop through precincts for better changes and update them
                if(!congressionalDistricts.get(keys[i]).needsRevision()) {
                    if (i == keys.length - 1)  isFinished = true;
                    continue;
                }
                boolean districtChanged = traversePrecinctsforChanges(state, congressionalDistricts.get(i));
                if(districtChanged || checkTermination()) break;
            }
        }
        return fillUpdate();
    }

    public boolean traversePrecinctsforChanges(UsState state, CongressionalDistrict congressionalDistrict) {

        //Get all voting districts that share boundary with other congressional districts
        Iterator<String> boundaryPrecincts_Ids = congressionalDistrict.getBoundaryPrecinct_ids().iterator();
        boolean anyChange = false;
        while(boundaryPrecincts_Ids.hasNext()) {
            Precinct currentPrecinct = precincts.get(boundaryPrecincts_Ids.next());
            boolean change = traverseBoundarycongressionalDistrictsforChanges(state, congressionalDistrict, currentPrecinct);
            if(change) anyChange = true;
        }
        return anyChange;
    }

    public boolean traverseBoundarycongressionalDistrictsforChanges(UsState state,
                                                               CongressionalDistrict congressionalDistrict,
                                                               Precinct currentPrecinct) {

        //Get all congressional districts that share boundary with currentPrecinct
        ArrayList<CongressionalDistrict> boundarycongressionalDistricts = getBoundaryCongressionalDistricts(currentPrecinct);
        boolean anyAcceptedChange = false;
        for(int i = 0; i < boundarycongressionalDistricts.size(); i++) {
            double oldObjective = state.calculateObjective(pCoefficient, cCoefficient, fCoefficient);
            CongressionalDistrict currentCongressionalDistrict = congressionalDistricts.get(currentPrecinct.getCongress_id());
            movePrecinct(currentCongressionalDistrict, boundarycongressionalDistricts.get(i), currentPrecinct);
            double newObjective = state.calculateObjective(pCoefficient, cCoefficient, fCoefficient);
            if(changeAccepted(newObjective, oldObjective)) {
                //Update the neighbours of current precinct to be the new voting districts that have boundary with congressional district
                congressionalDistrict.updateBoundaryPrecincts(currentPrecinct);
                congressionalDistrict.setNeedsRevision(true);
                resetUnChangedChecks();
                anyAcceptedChange = true;
                addMove(state, currentCongressionalDistrict, boundarycongressionalDistricts.get(i), currentPrecinct);
            }
            else {
                movePrecinct(boundarycongressionalDistricts.get(i), currentCongressionalDistrict, currentPrecinct);
                unChangedChecks++;
            }
        }
        if(!anyAcceptedChange) congressionalDistrict.setNeedsRevision(false);
        return anyAcceptedChange;
    }

    public void sortByPoulation(HashMap<String,CongressionalDistrict> congressionalDistricts, String[] keys) {}

    public boolean changeAccepted(double newObjective, double oldObjective) {
        if(newObjective < oldObjective) return true;
        return false;
    }

    public boolean checkTermination() {
        if((unChangedChecks <= Constants.MAX_UNCHANGED_CHECKS || maxMovesPerUpdate <= Constants.MAX_MOVES_PER_UPDATE) && !isFinished) return false;
        return true;
    }

    public void resetUnChangedChecks() {
        unChangedChecks = 0;
    }

    public void movePrecinct(CongressionalDistrict originDistrict, CongressionalDistrict targetDistrict, Precinct movingPrecinct) {
        targetDistrict.addPrecinct(movingPrecinct.getPrecinct_id());
        originDistrict.removePrecinct(movingPrecinct.getPrecinct_id());
        movingPrecinct.setCongress_id(targetDistrict.getCongress_id());
    }

    public void addMove(UsState state, CongressionalDistrict originDistrict,
                        CongressionalDistrict targetDistrict, Precinct currentPrecinct) {
        this.maxMovesPerUpdate++;
        moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(), currentPrecinct.getPrecinct_id()));
    }

    public ArrayList<CongressionalDistrict> getBoundaryCongressionalDistricts(Precinct precinct) {
        ArrayList<CongressionalDistrict> neighbourDistricts = new ArrayList<>();
        Iterator<String> iterator = precinct.getNeighbor_precinct_ids().iterator();
        while(iterator.hasNext()) {
            String neighbourDistrictId = precincts.get(iterator.next()).getCongress_id();
            if(!neighbourDistrictId.equals(precinct.getPrecinct_id())) {
                neighbourDistricts.add(congressionalDistricts.get(neighbourDistrictId));
            }
        }
        return neighbourDistricts;
    }

    public Update fillUpdate() {
        update.setFinished(isFinished);
        return update;
    }
}

