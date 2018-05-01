package com.sbu.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.data.PrecinctRepository;
import com.sbu.data.entitys.Precinct;
import com.sbu.main.Constants;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PreProcessingService {

    @Autowired
    PrecinctRepository precinctRepository;

    List<Precinct> arPrecincts;
    List<Precinct> inPrecincts;
    List<Precinct> wvPrecincts;

    public boolean startPreprocessor() throws IOException {
        arPrecincts = precinctRepository.findByState_id(Constants.ARKANSAS);
        inPrecincts = precinctRepository.findByState_id(Constants.INDIANA);
        wvPrecincts = precinctRepository.findByState_id(Constants.WEST_VIRGINA);
        arPrecincts = findAdjacency(arPrecincts);
        inPrecincts = findAdjacency(inPrecincts);
        wvPrecincts = findAdjacency(wvPrecincts);
        arPrecincts = findCongress(arPrecincts);
        inPrecincts = findCongress(inPrecincts);
        wvPrecincts = findCongress(wvPrecincts);
        return true;
    }

    private List<Precinct> findBorders(List<Precinct> vds) {
        List<Precinct> borderDistricts = new ArrayList<>();
        for (Precinct vd : vds) {
            if (borderDistricts.contains(vd)) continue;

            String neighbors = vd.getNeighbor_precincts();
            List<String> neighborList = new ArrayList<>();
            Matcher matcher = Pattern.compile("'(.*?)'").matcher(neighbors);

            while (matcher.find()) {
                neighborList.add(matcher.group());
            }

            for (String s : neighborList) {
                for (Precinct v : vds) {
                    if (s.contains(v.getPrecinct_id()) && !v.getCongress_id().equals(vd.getCongress_id())) {
                        borderDistricts.add(v);
                        borderDistricts.add(vd);
                    }
                }
            }
        }
        return borderDistricts;
    }

    public List<Precinct> findCongressBorder(String state_id) {
        List<Precinct> borderDistricts = new ArrayList<>();
        switch (state_id) {
            case Constants.ARKANSAS:
                borderDistricts = findBorders(arPrecincts);
                break;
            case Constants.WEST_VIRGINA:
                borderDistricts = findBorders(wvPrecincts);
                break;
            case Constants.INDIANA:
                borderDistricts = findBorders(inPrecincts);
                break;
        }
        return borderDistricts;
    }

    private List<Precinct> findAdjacency(List<Precinct> precincts) throws IOException {
        for (Precinct precinct : precincts) {
            precinct.setNeighbor_precincts(Constants.ARRAY_START);
        }
        for (Precinct precinct : precincts) {
            for (Precinct precinct2 : precincts) {
                if (!(precinct.getPrecinct_id().equals(precinct2.getPrecinct_id())) && isAdjacent(precinct, precinct2)) {
                    precinct.setNeighbor_precincts(precinct.getNeighbor_precincts() + "\"\'" + precinct2.getPrecinct_id() + "\'\", ");
                }
            }
            precinct.setNeighbor_precincts(precinct.getNeighbor_precincts().substring(0, precinct.getNeighbor_precincts().length() - 2) + Constants.ARRAY_END);
        }
        return (List<Precinct>) precinctRepository.save(precincts);
    }

    private List<Precinct> findCongress(List<Precinct> precincts) {
        return precincts;
    }

    private boolean isAdjacent(Precinct precinct1, Precinct precinct2) throws IOException {
        String location = Constants.VD_RESOURCES + precinct1.getState_id() + Constants.VD_SUFFIX + precinct1.getPrecinct_id() + Constants.GEOJSON;
        String location2 = Constants.VD_RESOURCES + precinct2.getState_id() + Constants.VD_SUFFIX + precinct1.getPrecinct_id() + Constants.GEOJSON;
        FileReader reader = new FileReader(location);
        FileReader reader2 = new FileReader(location2);
        Feature feature1 = new ObjectMapper().readValue(reader, Feature.class);
        Feature feature2 = new ObjectMapper().readValue(reader2, Feature.class);
        List<List<LngLatAlt>> precinct1Mpoly = ((MultiPolygon) feature1.getGeometry()).getCoordinates().get(0);
        List<List<LngLatAlt>> precinct2Mpoly = ((MultiPolygon) feature2.getGeometry()).getCoordinates().get(0);
        //put all polygon points in 1 array per multipolygon
        List<LngLatAlt> precinct1Points = new ArrayList<>();
        List<LngLatAlt> precinct2Points = new ArrayList<>();
        for (List<LngLatAlt> poly : precinct1Mpoly) {
            precinct1Points.addAll(poly);
            //null to separate polygons
            precinct1Points.add(null);
        }
        for (List<LngLatAlt> poly : precinct2Mpoly) {
            precinct2Points.addAll(poly);
            precinct2Points.add(null);
        }
        for (int i = 0; i < precinct1Points.size() - 1; i++) {
            for (int j = 0; j < precinct2Points.size() - 1; j++) {
                LngLatAlt p1 = precinct1Points.get(i);
                LngLatAlt q1 = precinct1Points.get(i + 1);
                LngLatAlt p2 = precinct2Points.get(j);
                LngLatAlt q2 = precinct2Points.get(j + 1);
                if (p1 != null && q1 != null && p2 != null && q2 != null) {
                    if (doIntersect(p1, q1, p2, q2)) {
                        return true;
                    }
                } else if (p1 == null || q1 == null) {
                    break;
                }
            }
        }
        return false;
    }

    private boolean onSegment(LngLatAlt p, LngLatAlt q, LngLatAlt r) {
        return q.getLongitude() <= Math.max(p.getLongitude(), r.getLongitude()) && q.getLongitude() >= Math.min(p.getLongitude(), r.getLongitude()) &&
                q.getLatitude() <= Math.max(p.getLatitude(), r.getLatitude()) && q.getLatitude() >= Math.min(p.getLatitude(), r.getLatitude());

    }

    private int orientation(LngLatAlt p, LngLatAlt q, LngLatAlt r) {
        double val = (q.getLatitude() - p.getLatitude()) * (r.getLongitude() - q.getLongitude()) -
                (q.getLongitude() - p.getLongitude()) * (r.getLatitude() - q.getLatitude());
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    private boolean doIntersect(LngLatAlt p1, LngLatAlt q1, LngLatAlt p2, LngLatAlt q2) {
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
