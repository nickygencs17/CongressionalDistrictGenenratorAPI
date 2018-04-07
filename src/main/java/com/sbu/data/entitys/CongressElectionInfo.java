package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "congress_election_info")
public class CongressElectionInfo {


    @Id
    int id;

    @NotNull
    int election_year;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @NotNull
    UsState state_id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "congress_id")
    @NotNull
    CongressionalDistrict congress_id;


    @NotNull
    String party;

    @NotNull
    float percent_of_votes;

    @NotNull
    boolean is_winner;

    public CongressElectionInfo(int election_year, UsState state_id, CongressionalDistrict congress_id, String party, float percent_of_votes, boolean is_winner) {
        this.election_year = election_year;
        this.state_id = state_id;
        this.congress_id = congress_id;
        this.party = party;
        this.percent_of_votes = percent_of_votes;
        this.is_winner = is_winner;
    }

    public CongressElectionInfo() {
    }

    public CongressionalDistrict getCongress_id() {
        return congress_id;
    }

    public void setCongress_id(CongressionalDistrict congress_id) {
        this.congress_id = congress_id;
    }

    public int getElection_year() {
        return election_year;
    }

    public void setElection_year(int election_year) {
        this.election_year = election_year;
    }

    public UsState getState_id() {
        return state_id;
    }

    public void setState_id(UsState state_id) {
        this.state_id = state_id;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public float getPercent_of_votes() {
        return percent_of_votes;
    }

    public void setPercent_of_votes(float percent_of_votes) {
        this.percent_of_votes = percent_of_votes;
    }

    public boolean isIs_winner() {
        return is_winner;
    }

    public void setIs_winner(boolean is_winner) {
        this.is_winner = is_winner;
    }
}


