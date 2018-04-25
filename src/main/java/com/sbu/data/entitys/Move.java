package com.sbu.data.entitys;

public class Move {
    String stateId;
    String originCongressionalDistrictId;
    String targetCongressionalDistrictId;
    String movingPrecinctId;
    String geoId;
    String colorChange;

    public Move(String stateId, String originCongressionalDistrictId, String targetCongressionalDistrictId,
                String movingPrecinctId, String geoId, String colorChange) {
        this.stateId = stateId;
        this.originCongressionalDistrictId = originCongressionalDistrictId;
        this.targetCongressionalDistrictId = targetCongressionalDistrictId;
        this.movingPrecinctId = movingPrecinctId;
        this.geoId = geoId;
        this.colorChange = colorChange;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getOriginCongressionalDistrictId() {
        return originCongressionalDistrictId;
    }

    public void setOriginCongressionalDistrictId(String originCongressionalDistrictId) {
        this.originCongressionalDistrictId = originCongressionalDistrictId;
    }

    public String getTargetCongressionalDistrictId() {
        return targetCongressionalDistrictId;
    }

    public void setTargetCongressionalDistrictId(String targetCongressionalDistrictId) {
        this.targetCongressionalDistrictId = targetCongressionalDistrictId;
    }

    public String getMovingPrecinctId() {
        return movingPrecinctId;
    }

    public void setMovingPrecinctId(String movingPrecinctId) {
        this.movingPrecinctId = movingPrecinctId;
    }

    public String getColorChange() {
        return colorChange;
    }

    public void setColorChange(String colorChange) {
        this.colorChange = colorChange;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }
}
