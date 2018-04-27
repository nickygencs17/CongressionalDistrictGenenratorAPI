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
    float populationDeviation;
    int cCoefficient;
    int fCoefficient;
    boolean isFinished;
    UsState selectedState;
    HashMap<String, CongressionalDistrict> congressionalDistricts;
    HashMap<String, Precinct> precincts;
    public Update startAlgorithm(UsState state, float populationDeviation, int cCoefficient, int fCoefficient) {

        this.moves = new ArrayList<>();
        this.update = new Update();
        this.populationDeviation = populationDeviation;
        this.cCoefficient = cCoefficient;
        this.fCoefficient = fCoefficient;
        this.isFinished = false;
        this.selectedState = state;
        unChangedChecks = 0;
        maxMovesPerUpdate = 0;
        //Get all Congressional districts that share boundary with other congressional districts
        this.congressionalDistricts = state.getCongressionalDistricts();
        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
        while(!checkTermination()) {
            sortByPoulation(keys);
            for(int i = 0; i < keys.length; i++) {
                //Loop through precincts for better changes and update them
                if(!congressionalDistricts.get(keys[i]).needsRevision()) {
                    if (i == keys.length - 1)  isFinished = true;
                    continue;
                }
                boolean districtChanged = traversePrecinctsforChanges(congressionalDistricts.get(keys[i]));
                if(districtChanged || checkTermination()) break;
            }
        }
        return fillUpdate();
    }

    public boolean traversePrecinctsforChanges(CongressionalDistrict congressionalDistrict) {

        //Get all voting districts that share boundary with other congressional districts
        Iterator<Precinct> boundaryPrecincts = congressionalDistrict.getBoundaryPrecinctHashSet().iterator();
        while(boundaryPrecincts.hasNext()) {
            Precinct currentPrecinct = boundaryPrecincts.next();
            boolean change = checkBoundarycongressionalDistrictforChanges(congressionalDistrict, currentPrecinct);
            if(change) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoundarycongressionalDistrictforChanges(CongressionalDistrict congressionalDistrict,
                                                               Precinct currentPrecinct) {
        if(currentPrecinct.getGeo_id().equals("05145027")) {
            System.out.println("here");
        }
        //Get congressional district that share boundary with currentPrecinct
        CongressionalDistrict boundarycongressionalDistrict = getBoundaryCongressionalDistrict(currentPrecinct);
        double oldObjective = selectedState.calculateObjective(cCoefficient, fCoefficient);
        CongressionalDistrict currentCongressionalDistrict = congressionalDistricts.get(currentPrecinct.getCongress_id());
        movePrecinct(currentCongressionalDistrict, boundarycongressionalDistrict, currentPrecinct);
        double newObjective = selectedState.calculateObjective(cCoefficient, fCoefficient);
        if(changeAccepted(oldObjective, newObjective, currentCongressionalDistrict, currentPrecinct)) {
            //Update the neighbours of current precinct to be the new voting districts that have boundary with congressional district
            congressionalDistrict.updateBoundaryPrecincts();
            boundarycongressionalDistrict.updateBoundaryPrecincts();
            congressionalDistrict.setNeedsRevision(true);
            boundarycongressionalDistrict.setNeedsRevision(true);
            resetUnChangedChecks();
            addMove(selectedState, currentCongressionalDistrict, boundarycongressionalDistrict, currentPrecinct);
            return true;
        }
        else {
            movePrecinct(boundarycongressionalDistrict, currentCongressionalDistrict, currentPrecinct);
            unChangedChecks++;
        }
        congressionalDistrict.setNeedsRevision(false);
        return false;
    }

    public void sortByPoulation(String[] keys) {
        int n = keys.length;
        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (congressionalDistricts.get(keys[j]).getPopulation() < congressionalDistricts.get(keys[min_idx]).getPopulation())
                    min_idx = j;
            String temp = keys[min_idx];
            keys[min_idx] = keys[i];
            keys[i] = temp;
        }
    }

    public boolean checkTermination() {
        if(unChangedChecks >= Constants.MAX_UNCHANGED_CHECKS) isFinished = true;
        if(unChangedChecks <= Constants.MAX_UNCHANGED_CHECKS && maxMovesPerUpdate <= Constants.MAX_MOVES_PER_UPDATE && !isFinished) {
            return false;
        }
        return true;
    }

    public boolean changeAccepted(double oldObjective, double newObjective, CongressionalDistrict currentCongressionalDistrict,
                                  Precinct currentPrecinct) {
        if(!((newObjective > oldObjective) && currentCongressionalDistrict.isContiguous(currentPrecinct))) return false;
        if(selectedState.calculatePopulationDeviation() > populationDeviation) return false;
        return true;
    }

    public void resetUnChangedChecks() {
        unChangedChecks = 0;
    }

    public void movePrecinct(CongressionalDistrict originDistrict, CongressionalDistrict targetDistrict, Precinct movingPrecinct) {
        if(originDistrict == null || targetDistrict == null || movingPrecinct == null) {
            System.out.println("Problem : " + originDistrict.getCongress_id() + targetDistrict + "Precinct : " + movingPrecinct.getPrecinct_id());
        }
        //System.out.println("Moving from : " + originDistrict.getCongress_id() + targetDistrict + "Precinct : " + movingPrecinct.getPrecinct_id());
        originDistrict.removePrecinct(movingPrecinct);
        targetDistrict.addPrecinct(movingPrecinct, true);
    }

    public void addMove(UsState state, CongressionalDistrict originDistrict,
                        CongressionalDistrict targetDistrict, Precinct currentPrecinct) {
        this.maxMovesPerUpdate++;
        moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(),
                currentPrecinct.getPrecinct_id(), currentPrecinct.getGeo_id(), targetDistrict.getColor()));
    }

    public CongressionalDistrict getBoundaryCongressionalDistrict(Precinct precinct) {
        Iterator<Precinct> iterator = precinct.getNeighborPrecinctSet().iterator();
        while(iterator.hasNext()) {
            String neighbourDistrictId = iterator.next().getCongress_id();
            if(!neighbourDistrictId.equals(precinct.getCongress_id())) {
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
}

