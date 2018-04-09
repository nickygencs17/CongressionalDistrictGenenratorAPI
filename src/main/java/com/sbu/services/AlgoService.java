package com.sbu.services;

import com.sbu.data.entitys.CongressionalDistrict;
import com.sbu.data.entitys.UsState;
import com.sbu.data.entitys.VotingDistrict;

import java.util.ArrayList;

public class AlgoService {

//    public void startAlgo(UsState state, int pCoefficient, int cCoefficient, int fCoefficient, float temperature, float temperatureGradient) {
//
//        //Get all Congressional districts that share boundary with other congressional districts
//        ArrayList<CongressionalDistrict> congressDistricts = state.getCongressDistricts();
//        randomShuffle(CongressDistricts);
//
//        for(int i = 0; i < congressDistricts.size(); i++) {
//
//            //Loop through voting districts for better changes and update them
//
//            traverseVotingDistrictsforChanges(state, congressDistricts.get(i));
//        }
//
//    }
//
//    // Shuffles Congressional Districts Randomly
//    public void randomShuffle(ArrayList<CongressionalDistrict> congressionalDistricts) {}
//
//    // Calculates probability of keeping the new change
//
//    public boolean changeAccepted(double newEnergy, double oldEnergy, double temperature) {
//        if(newEnergy < oldEnergy) return true;
//        double probability = Math.exp(-((newEnergy - oldEnergy) / temperature));
//        if(probability > 0.5) return true;
//        return false;
//    }
//
//    public void traverseVotingDistrictsforChanges(UsState state, CongressionalDistrict congressDistrict) {
//
//        //Get all voting districts that share boundary with other congressional districts
//        ArrayList<VotingDistrict> boundaryVotingdistricts = congressDistrict.getBoundaryVotingDistricts();
//
//        for(int i = 0; i < boundaryVotingdistricts.size(); i++) {
//            VotingDistrict currentVotingDistrict = boundaryVotingdistricts.get(i);
//
//
//            traverseBoundaryCongressDistrictsforChanges(state, congressDistrict, currentVotingDistrict);
//        }
//    }
//
//
//
//    public void traverseBoundaryCongressDistrictsforChanges(UsState state, CongressionalDistrict congressDistrict, VotingDistrict currentVotingDistrict) {
//
//        //Get all congressional districts that share boundary with currentVotingDistrict
//        ArrayList<CongressionalDistrict> boundaryCongressDistricts = currentVotingDistrict.getBoundaryCongressDistricts();
//
//        for(int i = 0; i < boundaryCongressDistricts.size(); i++) {
//
//            double oldEnergy = pCoefficient * state.getPopulationDeviation()
//                    + cCoefficient * (1 - boundaryCongressDistricts.get(i).getCompactnessScore())
//                    + fCoefficient * (1 - state.getPoliticalFairness());
//
//            boundaryCongressDistricts.get(i).addVotingDistrict(currentVotingDistrict);
//            currentVotingDistrict.getCongressionalDistrict().removeVotingDistrict(CurrentVotingDistrict);
//            currentVotingDistrict.setCongressionalDistrict(boundaryCongressDistricts.get(i));
//
//            double newEnergy = pCoefficient * state.getPopulationDeviation()
//                    + cCoefficient * (1 - boundaryCongressDistricts.get(i).getCompactnessScore())
//                    + fCoefficient * (1 - state.getPoliticalFairness());
//            if(changeAccepted(newEnergy, oldEnergy, temperature)) {
//
//                //Update the neighbours of currentvotingdistrict to be the new voting districts that have boundary with congressional district
//                congressDistrict.updateBoundaryVotingDistricts(currentVotingDistrict);
//            }
//            else {
//                boundaryCongressDistricts.get(i).removeVotingDistrict(currentVotingDistrict);
//                congressDistrict.addVotingDistrict(currentVotingDistrict);
//                currentVotingDistrict.setCongressionalDistrict(congressDistrict);
//            }
//        }
//    }

}
