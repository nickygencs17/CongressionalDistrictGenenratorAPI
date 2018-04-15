package com.sbu.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.data.VotingDistrictRepository;
import com.sbu.data.entitys.VotingDistrict;
import com.sbu.data.CongressionalDistrictRepository;
import com.sbu.data.entitys.CongressionalDistrict;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class PreprocessService {


    @Autowired
    VotingDistrictRepository votingDistrictRepository;


    public Object findAdjacency() throws IOException {
        Iterable<VotingDistrict> allVoting = votingDistrictRepository.findAll();
        for(VotingDistrict vd: allVoting) {
            vd.setNeighbor_vds("[");
        }

        for(VotingDistrict vd: allVoting){
            for(VotingDistrict vd2: allVoting){
                if(isAdjacent(vd, vd2)){
                    vd.setNeighbor_vds(vd.getNeighbor_vds() + "\\\"\\'" + vd2.getVd_id() + "\\'\\\", ");
                }
            }
            vd.setNeighbor_vds(vd.getNeighbor_vds().substring(0, vd.getNeighbor_vds().length()-2) + "]");
        }
        votingDistrictRepository.save(allVoting);
        return true;
    }

    public Object findCongress(){
        return true;
    }

    public Object findBorders(){
        return true;
    }

    private boolean isAdjacent(VotingDistrict vd1, VotingDistrict vd2) throws IOException {
        Feature feature1 = new ObjectMapper().readValue(vd1.getVd_boundaries(), Feature.class);
        Feature feature2 = new ObjectMapper().readValue(vd2.getVd_boundaries(), Feature.class);
        List<LngLatAlt> poly1 = ((MultiPolygon)feature1.getGeometry()).getCoordinates().get(0).get(0);
        List<LngLatAlt> poly2 = ((MultiPolygon)feature2.getGeometry()).getCoordinates().get(0).get(0);
        for(LngLatAlt coord1:poly1){
            for(LngLatAlt coord2:poly2){
                if(coord1.equals(coord2)) return true;
            }
        }
        return false;
    }
}
