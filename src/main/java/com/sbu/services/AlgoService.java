package com.sbu.services;

import com.sbu.data.entitys.CongressionalDistrict;
import com.sbu.data.entitys.Move;
import com.sbu.data.entitys.UsState;
import com.sbu.data.entitys.VotingDistrict;
import com.sbu.main.Constants;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class AlgoService {

//    ArrayList<Move> moves;
//    int unChangedChecks;
//    int maxMovesPerUpdate;
//    int pCoefficient;
//    int cCoefficient;
//    int fCoefficient;
//    public ArrayList<Move> startAlgorithm(UsState state, int pCoefficient, int cCoefficient, int fCoefficient, ArrayList<Move> moves) {
//
//        this.moves = moves;
//        this.pCoefficient = pCoefficient;
//        this.cCoefficient = cCoefficient;
//        this.fCoefficient = fCoefficient;
//        unChangedChecks = 0;
//        maxMovesPerUpdate = 0;
//        //Get all Congressional districts that share boundary with other congressional districts
//        HashMap<String, CongressionalDistrict> congressionalDistricts = state.getCongressionalDistricts();
//        String[] keys = congressionalDistricts.keySet().stream().toArray(String[]::new);
//        while(!checkTermination()) {
//            sortByPoulation(congressionalDistricts, keys);
//            for(int i = 0; i < keys.length; i++) {
//                //Loop through precincts for better changes and update them
//                if(!congressionalDistricts.get(keys[i]).needsRevision()) continue;
//                boolean districtChanged = traversePrecinctsforChanges(state, congressionalDistricts.get(i));
//                if(districtChanged || checkTermination()) break;
//            }
//        }
//        return moves;
//    }
//
//    public boolean traversePrecinctsforChanges(UsState state, CongressionalDistrict congressionalDistrict) {
//
//        //Get all voting districts that share boundary with other congressional districts
//        ArrayList<VotingDistrict> boundaryPrecincts = congressionalDistrict.getBoundaryPrecincts();
//        boolean anyChange = false;
//        for(int i = 0; i < boundaryPrecincts.size(); i++) {
//            VotingDistrict currentPrecinct = boundaryPrecincts.get(i);
//            boolean change = traverseBoundarycongressionalDistrictsforChanges(state, congressionalDistrict, currentPrecinct);
//            if(change) anyChange = true;
//        }
//        return anyChange;
//    }
//
//    public boolean traverseBoundarycongressionalDistrictsforChanges(UsState state,
//                                                               CongressionalDistrict congressionalDistrict,
//                                                               VotingDistrict currentPrecinct) {
//
//        //Get all congressional districts that share boundary with currentVotingDistrict
//        ArrayList<CongressionalDistrict> boundarycongressionalDistricts = currentPrecinct.getBoundaryCongressionalDistricts();
//        boolean anyAcceptedChange = false;
//        for(int i = 0; i < boundarycongressionalDistricts.size(); i++) {
//
//            double oldObjective = state.calculateObjective(pCoefficient, cCoefficient, fCoefficient);
//
//            CongressionalDistrict currentCongressionalDistrict = currentPrecinct.getCongressionalDistrict();
//            movePrecinct(currentCongressionalDistrict, boundarycongressionalDistricts.get(i), currentPrecinct);
//
//            double newObjective = state.calculateObjective(pCoefficient, cCoefficient, fCoefficient);
//            if(changeAccepted(newObjective, oldObjective)) {
//                //Update the neighbours of current precinct to be the new voting districts that have boundary with congressional district
//                congressionalDistrict.updateBoundaryprecincts(currentPrecinct);
//                congressionalDistrict.setNeedsRevision(true);
//                resetUnChangedChecks();
//                anyAcceptedChange = true;
//                addMove(state, currentCongressionalDistrict, boundarycongressionalDistricts.get(i), currentPrecinct);
//            }
//            else {
//                movePrecinct(boundarycongressionalDistricts.get(i), currentCongressionalDistrict, currentPrecinct);
//                unChangedChecks++;
//            }
//        }
//        if(!anyAcceptedChange) congressionalDistrict.setNeedsRevision(false);
//        return anyAcceptedChange;
//    }
//
//    public void sortByPoulation(HashMap<String,CongressionalDistrict> congressionalDistricts, String[] keys) {}
//
//    public boolean changeAccepted(double newObjective, double oldObjective) {
//        if(newObjective < oldObjective) return true;
//        return false;
//    }
//
//    public boolean checkTermination() {
//        if(unChangedChecks <= Constants.MAX_UNCHANGED_CHECKS || maxMovesPerUpdate <= Constants.MAX_MOVES_PER_UPDATE) return false;
//        return true;
//    }
//
//    public void resetUnChangedChecks() {
//        unChangedChecks = 0;
//    }
//
//    public void movePrecinct(CongressionalDistrict originDistrict, CongressionalDistrict targetDistrict, VotingDistrict movingPrecinct) {
//        targetDistrict.addPrecinct(movingPrecinct);
//        originDistrict.removePrecinct(movingPrecinct);
//        movingPrecinct.setCongress_id(targetDistrict.getCongress_id());
//    }
//
//    public void addMove(UsState state, CongressionalDistrict originDistrict,
//                        CongressionalDistrict targetDistrict, VotingDistrict currentPrecinct) {
//        this.maxMovesPerUpdate++;
//       // moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(), currentPrecinct.getVd_id()));
//    }
}

