import java.util.ArrayList;

/**
 *
 * @author Rahul S Agasthya
 */
public class District extends Division {
    State state;
    ArrayList<VoterDistrict> voterDistricts;
    
    public District(String id, String name, long population, String geoJsonFile, State initState) {
        super(id, name, population, geoJsonFile);
        state = initState;
        voterDistricts = new ArrayList<>();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ArrayList<VoterDistrict> getVoterDistricts() {
        return voterDistricts;
    }

    public void setVoterDistricts(ArrayList<VoterDistrict> voterDistricts) {
        this.voterDistricts = voterDistricts;
    }
}
