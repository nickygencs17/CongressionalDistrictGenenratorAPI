package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "congress_election_info")
public class CongressElectionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotNull
    boolean is_winner;

    @NotNull
    float percent_of_votes;

    @NotNull
    String party;

    @NotNull
    String candidate_name;

    @NotNull
    String congress_id;

    @NotNull
    String state_id;

    @NotNull
    int election_year;

    public CongressElectionInfo() {
    }

    public CongressElectionInfo(boolean is_winner, float percent_of_votes, String party, String candidate_name, String congress_id, String state_id, int election_year) {
        this.is_winner = is_winner;
        this.percent_of_votes = percent_of_votes;
        this.party = party;
        this.candidate_name = candidate_name;
        this.congress_id = congress_id;
        this.state_id = state_id;
        this.election_year = election_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_winner() {
        return is_winner;
    }

    public void setIs_winner(boolean is_winner) {
        this.is_winner = is_winner;
    }

    public float getPercent_of_votes() {
        return percent_of_votes;
    }

    public void setPercent_of_votes(float percent_of_votes) {
        this.percent_of_votes = percent_of_votes;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public String getCongress_id() {
        return congress_id;
    }

    public void setCongress_id(String congress_id) {
        this.congress_id = congress_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public int getElection_year() {
        return election_year;
    }

    public void setElection_year(int election_year) {
        this.election_year = election_year;
    }
}


