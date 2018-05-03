package com.sbu.services;

import com.sbu.data.entitys.*;
import com.sbu.main.Constants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component
public class AlgoService {

    ArrayList<Move> moves;
    List<String> congressExclude;
    List<String> precinctExclude;
    Update update;
    int unChangedChecks;
    int movesinCurrentUpdate;
    float populationDeviation;
    int cCoefficient;
    int fCoefficient;
    int totalIterations;
    int simulatedMoves;
    boolean isFinished;
    double temperature;
    UsState selectedState;
    HashMap<String, CongressionalDistrict> congressionalDistricts;

    public Update startAlgorithm(UsState state, float populationDeviation, int cCoefficient, int fCoefficient) {

        congressExclude = new ArrayList<>();
        precinctExclude = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.update = new Update();
        this.populationDeviation = populationDeviation;
        this.cCoefficient = cCoefficient;
        this.fCoefficient = fCoefficient;
        this.isFinished = false;
        this.selectedState = state;
        this.temperature = (-((double) (cCoefficient + fCoefficient)) / 2) / Math.log(0.8);
        unChangedChecks = 0;
        movesinCurrentUpdate = 0;
        simulatedMoves = 0;
        this.congressionalDistricts = state.getCongressionalDistricts();
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        while (!checkTermination()) {
            sortByPoulation(keys);
            for (int i = 0; i < keys.length; i++) {
                if (inExcludeCongress(congressionalDistricts.get(keys[i]).getCongress_id()) || !congressionalDistricts.get(keys[i]).needsRevision()) {
                    if (i == keys.length - 1) isFinished = true;
                    continue;
                }
                boolean districtChanged = traversePrecinctsforChanges(congressionalDistricts.get(keys[i]));
                if (districtChanged || checkTermination()) break;
            }
        }
        return fillUpdate();
    }

    public boolean traversePrecinctsforChanges(CongressionalDistrict congressionalDistrict) {
        Iterator<Precinct> boundaryPrecincts = congressionalDistrict.getBoundaryPrecinctHashSet().iterator();
        while (boundaryPrecincts.hasNext()) {
            Precinct currentPrecinct = boundaryPrecincts.next();
            boolean change = false;
            if (!inExcludePrecinct(currentPrecinct.getPrecinct_id())) {
                change = checkBoundarycongressionalDistrictforChanges(congressionalDistrict, currentPrecinct);
            }
            if (change) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoundarycongressionalDistrictforChanges(CongressionalDistrict congressionalDistrict,
                                                                Precinct currentPrecinct) {
        //Get congressional district that share boundary with currentPrecinct
        CongressionalDistrict boundarycongressionalDistrict = getBoundaryCongressionalDistrict(currentPrecinct);
        if (inExcludeCongress(boundarycongressionalDistrict.getCongress_id())) {
            congressionalDistrict.setNeedsRevision(false);
            return false;
        }
        double oldObjective = selectedState.calculateObjective(cCoefficient, fCoefficient);
        this.totalIterations++;
        CongressionalDistrict currentCongressionalDistrict = congressionalDistricts.get(currentPrecinct.getCongress_id());
        movePrecinct(currentCongressionalDistrict, boundarycongressionalDistrict, currentPrecinct);
        double newObjective = selectedState.calculateObjective(cCoefficient, fCoefficient);
        if (changeAccepted(oldObjective, newObjective, currentCongressionalDistrict, currentPrecinct)) {
            //Update the neighbours of current precinct to be the new voting districts that have boundary with congressional district
            congressionalDistrict.updateBoundaryPrecincts();
            boundarycongressionalDistrict.updateBoundaryPrecincts();
            congressionalDistrict.setNeedsRevision(true);
            boundarycongressionalDistrict.setNeedsRevision(true);
            resetUnChangedChecks();
            addMove(selectedState, currentCongressionalDistrict, boundarycongressionalDistrict, currentPrecinct);
            return true;
        } else {
            movePrecinct(boundarycongressionalDistrict, currentCongressionalDistrict, currentPrecinct);
            unChangedChecks++;
        }
        congressionalDistrict.setNeedsRevision(false);
        return false;
    }

    public void sortByPoulation(String[] keys) {
        int n = keys.length;
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
                if (congressionalDistricts.get(keys[j]).getPopulation() < congressionalDistricts.get(keys[min_idx]).getPopulation())
                    min_idx = j;
            String temp = keys[min_idx];
            keys[min_idx] = keys[i];
            keys[i] = temp;
        }
    }

    public boolean checkTermination() {
        if (unChangedChecks >= Constants.MAX_UNCHANGED_CHECKS) isFinished = true;
        if (unChangedChecks <= Constants.MAX_UNCHANGED_CHECKS && movesinCurrentUpdate <= Constants.MAX_MOVES_PER_UPDATE && !isFinished) {
            return false;
        }
        return true;
    }

    public boolean changeAccepted(double oldObjective, double newObjective, CongressionalDistrict currentCongressionalDistrict,
                                  Precinct currentPrecinct) {
        if (!(currentCongressionalDistrict.isContiguous(currentPrecinct))) return false;
        if (selectedState.calculatePopulationDeviation() > populationDeviation) return false;
        if (newObjective > oldObjective) return true;
        return false;
    }

    public void resetUnChangedChecks() {
        unChangedChecks = 0;
    }

    public void movePrecinct(CongressionalDistrict originDistrict, CongressionalDistrict targetDistrict, Precinct movingPrecinct) {
        originDistrict.removePrecinct(movingPrecinct);
        originDistrict.calculateCompactness();
        targetDistrict.addPrecinct(movingPrecinct, true);
        targetDistrict.calculateCompactness();
    }

    public void addMove(UsState state, CongressionalDistrict originDistrict,
                        CongressionalDistrict targetDistrict, Precinct currentPrecinct) {
        this.movesinCurrentUpdate++;
        moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(),
                currentPrecinct.getPrecinct_id(), currentPrecinct.getGeo_id(), targetDistrict.getColor()));
        Iterator<Precinct> iterator = currentPrecinct.getInnerPrecinctSet().iterator();
        while (iterator.hasNext()) {
            Precinct innerPrecinct = iterator.next();
            moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(),
                    innerPrecinct.getPrecinct_id(), innerPrecinct.getGeo_id(), targetDistrict.getColor()));
            this.movesinCurrentUpdate++;
        }
    }

    public CongressionalDistrict getBoundaryCongressionalDistrict(Precinct precinct) {
        Iterator<Precinct> iterator = precinct.getNeighborPrecinctSet().iterator();
        while (iterator.hasNext()) {
            String neighbourDistrictId = iterator.next().getCongress_id();
            if (!neighbourDistrictId.equals(precinct.getCongress_id())) {
                return congressionalDistricts.get(neighbourDistrictId);
            }
        }
        return null;
    }

    public Update fillUpdate() {
        update.setMoves(moves);
        update.setFinished(isFinished);
        update.setCompactness(selectedState.calculateCompactness());
        update.setPopulationDeviation(selectedState.getPopulationDeviation());
        return update;
    }

    private boolean inExcludeCongress(String congressid) {
        return congressExclude.contains(congressid);
    }

    private boolean inExcludePrecinct(String precinctid) {
        return precinctExclude.contains(precinctid);
    }
}

