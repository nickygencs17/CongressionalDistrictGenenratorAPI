import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Rahul S Agasthya
 */

public class VoterDistrict extends Division {
    String party;
    HashMap<String, Long> demographics;

    public VoterDistrict(String id, String name, long population, String geoJsonFile) {
        super(id, name, population, geoJsonFile);
        demographics = new HashMap<>();
    }
    
    public boolean updateDemographics(String file) throws FileNotFoundException, IOException {
        File demFile = new File(file);
        FileReader demFileReader = new FileReader(demFile);
        BufferedReader demBufferedReader = new BufferedReader(demFileReader);
        String line = demBufferedReader.readLine();
        this.party = line;
        while((line = demBufferedReader.readLine()) != null) {  
            String[] elements = line.split("=");
            if(demographics.containsKey(elements[0])) demographics.put(elements[0], demographics.get(elements[0]) + 1);
            else demographics.put(elements[0], (long) 1);
        }
        
        if(demographics.isEmpty()) return false;
        return true;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public HashMap<String, Long> getDemographics() {
        return demographics;
    }

    public void setDemographics(HashMap<String, Long> demographics) {
        this.demographics = demographics;
    }
}
