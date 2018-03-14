/**
 *
 * @author Rahul S Agasthya
 */
public class Division {
    String id;
    String name;
    long population;
    String geoJsonFile;

    public Division(String id, String name, long population, String geoJsonFile) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.geoJsonFile = geoJsonFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getGeoJsonFile() {
        return geoJsonFile;
    }

    public void setGeoJsonFile(String geoJsonFile) {
        this.geoJsonFile = geoJsonFile;
    }
}
