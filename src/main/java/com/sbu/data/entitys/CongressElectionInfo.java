package com.sbu.data.entitys;

public class CongressElectionInfo {


    int election_year;
    String state_id;
    String congress_id;
    String party;
    float percent_of_votes;
    boolean is_winner;

    public CongressElectionInfo(int election_year, String state_id, String congress_id, String party, float percent_of_votes, boolean is_winner) {
        this.election_year = election_year;
        this.state_id = state_id;
        this.congress_id = congress_id;
        this.party = party;
        this.percent_of_votes = percent_of_votes;
        this.is_winner = is_winner;

    }

    public CongressElectionInfo() {
    }

    public int getElection_year() {
        return election_year;
    }

    public void setElection_year(int election_year) {
         this.election_year = election_year;
    }

    public String getState_id() {
         return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCongress_id() {
        return congress_id;
    }

    public void setCongress_id(String congress_id) {
        this.congress_id = congress_id;
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
