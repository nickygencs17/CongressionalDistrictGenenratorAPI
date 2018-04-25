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
    UsState selectedState;
    HashMap<String, CongressionalDistrict> congressionalDistricts;
    HashMap<String, Precinct> precincts;
    public Update startAlgorithm(UsState state, int pCoefficient, int cCoefficient, int fCoefficient) {

        this.moves = new ArrayList<>();
        this.update = new Update(moves);
        this.pCoefficient = pCoefficient;
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
            sortByPoulation(congressionalDistricts, keys);
            for(int i = 0; i < keys.length; i++) {
                //Loop through precincts for better changes and update them
                if(!congressionalDistricts.get(keys[i]).needsRevision()) {
                    if (i == keys.length - 1)  isFinished = true;
                    continue;
                }
                boolean districtChanged = traversePrecinctsforChanges(state, congressionalDistricts.get(keys[i]));
                if(districtChanged || checkTermination()) break;
            }
        }
        return fillUpdate();
    }

    public boolean traversePrecinctsforChanges(UsState state, CongressionalDistrict congressionalDistrict) {

        //Get all voting districts that share boundary with other congressional districts
        Iterator<Precinct> boundaryPrecincts = congressionalDistrict.getBoundaryPrecinctHashSet().iterator();
        while(boundaryPrecincts.hasNext()) {
            Precinct currentPrecinct = boundaryPrecincts.next();
            boolean change = checkBoundarycongressionalDistrictforChanges(state, congressionalDistrict, currentPrecinct);
            if(change) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoundarycongressionalDistrictforChanges(UsState state,
                                                               CongressionalDistrict congressionalDistrict,
                                                               Precinct currentPrecinct) {
        //Get congressional district that share boundary with currentPrecinct
        CongressionalDistrict boundarycongressionalDistrict = getBoundaryCongressionalDistrict(currentPrecinct);
        double oldObjective = state.calculateObjective(pCoefficient, cCoefficient, fCoefficient);
        CongressionalDistrict currentCongressionalDistrict = congressionalDistricts.get(currentPrecinct.getCongress_id());
        movePrecinct(currentCongressionalDistrict, boundarycongressionalDistrict, currentPrecinct);
        double newObjective = state.calculateObjective(pCoefficient, cCoefficient, fCoefficient);
        if(changeAccepted(newObjective, oldObjective)) {
            //Update the neighbours of current precinct to be the new voting districts that have boundary with congressional district
            congressionalDistrict.updateBoundaryPrecincts();
            boundarycongressionalDistrict.updateBoundaryPrecincts();
            congressionalDistrict.setNeedsRevision(true);
            boundarycongressionalDistrict.setNeedsRevision(true);
            resetUnChangedChecks();
            addMove(state, currentCongressionalDistrict, boundarycongressionalDistrict, currentPrecinct);
            return true;
        }
        else {
            movePrecinct(boundarycongressionalDistrict, currentCongressionalDistrict, currentPrecinct);
            unChangedChecks++;
        }
        congressionalDistrict.setNeedsRevision(false);
        return false;
    }

    public void sortByPoulation(HashMap<String,CongressionalDistrict> congressionalDistricts, String[] keys) {}

    public boolean changeAccepted(double newObjective, double oldObjective) {
        System.out.println("newObjective : " + newObjective + "   oldObjective : " + oldObjective);
        if(newObjective > oldObjective) return true;
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
        if(originDistrict == null || targetDistrict == null || movingPrecinct == null) {
            System.out.println("Problem : " + originDistrict.getCongress_id() + targetDistrict + "Precinct : " + movingPrecinct.getPrecinct_id());
        }
        originDistrict.removePrecinct(movingPrecinct);
        targetDistrict.addPrecinct(movingPrecinct);
    }

    public void addMove(UsState state, CongressionalDistrict originDistrict,
                        CongressionalDistrict targetDistrict, Precinct currentPrecinct) {
        this.maxMovesPerUpdate++;
        moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(),
                currentPrecinct.getPrecinct_id(), targetDistrict.getColor()));
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
        update.setFinished(isFinished);
        update.setCompactness(selectedState.getCompactness());
        update.setPopulationDeviation(selectedState.calculatePopulationDeviation());
        return update;
    }
}

