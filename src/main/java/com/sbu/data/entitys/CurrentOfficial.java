package com.sbu.data.entitys;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "current_officials")
public class CurrentOfficial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotNull
    String state_id;

    @NotNull
    String type_office;

    @NotNull
    String full_name;

    @NotNull
    String party;

    @NotNull
    String district_id;


    public CurrentOfficial(String state_id, String type_office, String full_name, String party, String district_id) {
        this.state_id = state_id;
        this.type_office = type_office;
        this.full_name = full_name;
        this.party = party;
        this.district_id = district_id;
    }

    public CurrentOfficial() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getType_office() {
        return type_office;
    }

    public void setType_office(String type_office) {
        this.type_office = type_office;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }
}
