package com.sbu.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.data.VotingDistrictRepository;
import com.sbu.data.entitys.VotingDistrict;
import com.sbu.data.CongressionalDistrictRepository;
import com.sbu.data.entitys.CongressionalDistrict;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.geojson.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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
        List<List<LngLatAlt>> mpoly1 = ((MultiPolygon)feature1.getGeometry()).getCoordinates().get(0);
        List<List<LngLatAlt>> mpoly2 = ((MultiPolygon)feature2.getGeometry()).getCoordinates().get(0);

        //put all polygon points in 1 array per multipolygon
        List<LngLatAlt> points = new ArrayList<>();
        List<LngLatAlt> points2 = new ArrayList<>();


        for(List<LngLatAlt> poly:mpoly1){
            points.addAll(poly);
            //null to separate polygons
            points.add(null);
        }
        for(List<LngLatAlt> poly:mpoly2){
            points2.addAll(poly);
            points2.add(null);
        }


        for(int i = 0; i < points.size()-1; i++){
            for(int j = 0; j < points2.size()-1; j++){
                LngLatAlt p1 = points.get(i);
                LngLatAlt q1 = points.get(i+1);
                LngLatAlt p2 = points2.get(j);
                LngLatAlt q2 = points2.get(j+1);
                if(p1!=null && q1!=null && p2!=null && q2!=null){
                    if(doIntersect(p1, q1, p2, q2)){
                        return true;
                    }
                }
                else if(p1 == null || q1 == null){
                    break;
                }
            }
        }
        return false;
    }

    private boolean onSegment(LngLatAlt p, LngLatAlt q, LngLatAlt r)
    {
        return q.getLongitude() <= Math.max(p.getLongitude(), r.getLongitude()) && q.getLongitude() >= Math.min(p.getLongitude(), r.getLongitude()) &&
                q.getLatitude() <= Math.max(p.getLatitude(), r.getLatitude()) && q.getLatitude() >= Math.min(p.getLatitude(), r.getLatitude());

    }

    private int orientation(LngLatAlt p, LngLatAlt q, LngLatAlt r)
    {

        double val = (q.getLatitude() - p.getLatitude()) * (r.getLongitude() - q.getLongitude()) -
                (q.getLongitude() - p.getLongitude()) * (r.getLatitude() - q.getLatitude());

        if (val == 0) return 0;

        return (val > 0)? 1: 2;
    }

    private boolean doIntersect(LngLatAlt p1, LngLatAlt q1, LngLatAlt p2, LngLatAlt q2)
    {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(p1, p2, q1)) return true;
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false;
    }
}
