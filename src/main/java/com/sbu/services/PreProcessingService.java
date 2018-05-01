package com.sbu.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.data.PrecinctRepository;
import com.sbu.data.entitys.Precinct;
import com.sbu.main.Constants;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.MultiPolygon;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
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


    public boolean startPreprocessor() throws IOException {
        List<Precinct> arVoting = precinctRepository.findByState_id(Constants.ARKANSAS);
        List<Precinct> inVoting = precinctRepository.findByState_id(Constants.INDIANA);
        List<Precinct> wvVoting = precinctRepository.findByState_id(Constants.WEST_VIRGINA);


        arVoting = findAdjacency(arVoting);
        inVoting = findAdjacency(inVoting);
        wvVoting = findAdjacency(wvVoting);
        arVoting = findCongress(arVoting);
        inVoting = findCongress(inVoting);
        wvVoting = findCongress(wvVoting);
        //TODO: Use the border lists
        List<Precinct> arBorders = findBorders(arVoting);
        List<Precinct> inBorders = findBorders(inVoting);
        List<Precinct> wvBorders = findBorders(wvVoting);
        return true;
    }

    private List<Precinct> findCongress(List<Precinct> vds) {
        return vds;
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

    private List<Precinct> findAdjacency(List<Precinct> vds) throws IOException {
        for (Precinct vd : vds) {
            vd.setNeighbor_precincts(Constants.ARRAY_START);
        }

        for (Precinct vd : vds) {
            for (Precinct vd2 : vds) {
                if (!(vd.getPrecinct_id().equals(vd2.getPrecinct_id())) && isAdjacent(vd, vd2)) {
                    vd.setNeighbor_precincts(vd.getNeighbor_precincts() + "\\\"\\'" + vd2.getPrecinct_id() + "\\'\\\", ");
                }
            }
            vd.setNeighbor_precincts(vd.getNeighbor_precincts().substring(0, vd.getNeighbor_precincts().length() - 2) + Constants.ARRAY_END);
        }
        return (List<Precinct>) precinctRepository.save(vds);
    }

    private boolean isAdjacent(Precinct vd1, Precinct vd2) throws IOException {

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        JSONParser jsonParser = new JSONParser();

        String location = "src/main/resources/individual_vtds/" + vd1.getState_id() + "_vtd/" + vd1.getPrecinct_id() + ".geojson";


        JSONObject person = new JSONObject();

        try (FileReader reader = new FileReader(location)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            person = (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Feature feature1 = new ObjectMapper().readValue(person.get("type").toString(), Feature.class);

        System.out.print("here");
        Feature feature2 = new ObjectMapper().readValue(vd2.getPrecinct_boundaries(), Feature.class);
        List<List<LngLatAlt>> mpoly1 = ((MultiPolygon) feature1.getGeometry()).getCoordinates().get(0);
        List<List<LngLatAlt>> mpoly2 = ((MultiPolygon) feature2.getGeometry()).getCoordinates().get(0);

        //put all polygon points in 1 array per multipolygon
        List<LngLatAlt> points = new ArrayList<>();
        List<LngLatAlt> points2 = new ArrayList<>();


        for (List<LngLatAlt> poly : mpoly1) {
            points.addAll(poly);
            //null to separate polygons
            points.add(null);
        }
        for (List<LngLatAlt> poly : mpoly2) {
            points2.addAll(poly);
            points2.add(null);
        }


        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = 0; j < points2.size() - 1; j++) {
                LngLatAlt p1 = points.get(i);
                LngLatAlt q1 = points.get(i + 1);
                LngLatAlt p2 = points2.get(j);
                LngLatAlt q2 = points2.get(j + 1);
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
