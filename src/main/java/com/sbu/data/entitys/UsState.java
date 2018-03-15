package com.sbu.data.entitys;

import java.util.ArrayList;




/**
 *
 * @author Rahul S Agasthya
 */
public class UsState extends Division {
    ArrayList<VoterDistrict> voterDistricts;
    ArrayList<AssemblyDistrict> assemblyDistricts;
    ArrayList<SenateDistrict> senateDistricts;
    ArrayList<CongressionalDistrict> congressionalDistricts;
    
    public UsState(String id, String name, long population, String geoJsonFile) {
        super(id, name, population, geoJsonFile);
        voterDistricts = new ArrayList<>();
        assemblyDistricts = new ArrayList<>();
        senateDistricts = new ArrayList<>();
        congressionalDistricts = new ArrayList<>();
    }
}
