package com.sbu.services;

import com.sbu.data.entitys.CongressionalDistrict;
import com.sbu.data.entitys.Move;
import com.sbu.data.entitys.UsState;
import com.sbu.data.entitys.VotingDistrict;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlgoService {

//    ArrayList<Move> moves = new ArrayList<>();
//    int unChangedChecks = 0;
//    public ArrayList<Move> startAlgorithm(UsState state, int pCoefficient, int cCoefficient, int fCoefficient) {
//
//        //Get all Congressional districts that share boundary with other congressional districts
//        ArrayList<CongressionalDistrict> congressionalDistricts = state.getCongressDistricts();
//
//        while(!checkTermination()) {
//            sortByPoulation(congressionalDistricts);
//            for(int i = 0; i < congressionalDistricts.size(); i++) {
//                //Loop through voting districts for better changes and update them
//                if(!congressionalDistricts.get(i).needsRevision()) continue;
//                boolean districtChanged = traversePrecinctsforChanges(state, congressionalDistricts.get(i));
//                if(districtChanged) break;
//            }
//        }
//        return moves;
//    }
//
//    public boolean traversePrecinctsforChanges(UsState state, CongressionalDistrict congressDistrict,
//                                               int pCoefficient, int cCoefficient, int fCoefficient) {
//
//        //Get all voting districts that share boundary with other congressional districts
//        ArrayList<VotingDistrict> boundaryPrecincts = congressDistrict.getBoundaryPrecincts();
//        boolean anyChange = false;
//        for(int i = 0; i < boundaryPrecincts.size(); i++) {
//            VotingDistrict currentPrecinct = boundaryPrecincts.get(i);
//            boolean change = traverseBoundaryCongressDistrictsforChanges(state, congressDistrict, currentPrecinct,
//                                                        pCoefficient, cCoefficient, fCoefficient);
//            if(change) anyChange = true;
//        }
//        return anyChange;
//    }
//
//    public boolean traverseBoundaryCongressDistrictsforChanges(UsState state,
//                                                               CongressionalDistrict congressDistrict,
//                                                               VotingDistrict currentPrecinct,
//                                                               int pCoefficient, int cCoefficient, int fCoefficient) {
//
//        //Get all congressional districts that share boundary with currentVotingDistrict
//        ArrayList<CongressionalDistrict> boundaryCongressDistricts = currentPrecinct.getBoundaryCongressDistricts();
//        boolean anyAcceptedChange = false;
//        for(int i = 0; i < boundaryCongressDistricts.size(); i++) {
//
//            double oldEnergy = state.calculateEnergy(pCoefficient, cCoefficient, fCoefficient);
//
//            CongressionalDistrict currentCongressionalDistrict = currentPrecinct.getCongressionalDistrict();
//            movePrecinct(currentCongressionalDistrict, boundaryCongressDistricts.get(i), currentPrecinct);
//
//            double newEnergy = state.calculateEnergy(pCoefficient, cCoefficient, fCoefficient);
//            if(changeAccepted(newEnergy, oldEnergy)) {
//                //Update the neighbours of currentprecinct to be the new voting districts that have boundary with congressional district
//                congressDistrict.updateBoundaryprecincts(currentPrecinct);
//                congressDistrict.setNeedsRevision(true);
//                resetUnChangedChecks();
//                anyAcceptedChange = true;
//                addMove(state, currentCongressionalDistrict, boundaryCongressDistricts.get(i), currentPrecinct);
//            }
//            else {
//                movePrecinct(boundaryCongressDistricts.get(i), currentCongressionalDistrict, currentPrecinct);
//                unChangedChecks++;
//            }
//        }
//        if(!anyAcceptedChange) congressDistrict.setNeedsRevision(false);
//        return anyAcceptedChange;
//    }
//
//    public void sortByPoulation(ArrayList<CongressionalDistrict> congressionalDistricts) {}
//
//    public boolean changeAccepted(double newEnergy, double oldEnergy) {
//        if(newEnergy < oldEnergy) return true;
//        return false;
//    }
//
//    public boolean checkTermination() {
//        if(unChangedChecks <= 100) return true;
//        return false;
//    }
//
//    public void resetUnChangedChecks() {
//        unChangedChecks = 0;
//    }
//
//    public void calculateEnergy(UsState state, int pCoefficient, int cCoefficient, int fCoefficient) {
//       /* return pCoefficient * state.getPopulationDeviation()
//                + cCoefficient * (1 - boundaryCongressDistricts.get(i).getCompactnessScore())
//                + fCoefficient * (1 - state.getPoliticalFairness()); */
//    }
//
//    public void movePrecinct(CongressionalDistrict originDistrict, CongressionalDistrict targetDistrict, VotingDistrict movingPrecinct) {
//        targetDistrict.addPrecinct(movingPrecinct);
//        originDistrict.removePrecinct(movingPrecinct);
//        movingPrecinct.setCongressionalDistrict(targetDistrict);
//    }
//
//    public void addMove(UsState state, CongressionalDistrict originDistrict,
//                        CongressionalDistrict targetDistrict, VotingDistrict currentPrecinct) {
//        moves.add(new Move(state.getState_id(), originDistrict.getCongress_id(), targetDistrict.getCongress_id(), currentPrecinct.getVd_id()));
//    }

}

