package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "president_election_info")
public class PresidentElectionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotNull
    int election_year;

    @NotNull
    String party;

    @NotNull
    String pres_name;

    @NotNull
    String vpres_name;

    @NotNull
    long votes_for;

    @NotNull
    float vote_percent;

    @NotNull
    int ec_vote;

    @NotNull
    String state_id;

    @NotNull
    boolean is_winner;

    public PresidentElectionInfo() {
    }

    public PresidentElectionInfo(int election_year, String party, String pres_name, String vpres_name, long votes_for, float vote_percent, int ec_vote, String state_id, boolean is_winner) {
        this.election_year = election_year;
        this.party = party;
        this.pres_name = pres_name;
        this.vpres_name = vpres_name;
        this.votes_for = votes_for;
        this.vote_percent = vote_percent;
        this.ec_vote = ec_vote;
        this.state_id = state_id;
        this.is_winner = is_winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElection_year() {
        return election_year;
    }

    public void setElection_year(int election_year) {
        this.election_year = election_year;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPres_name() {
        return pres_name;
    }

    public void setPres_name(String pres_name) {
        this.pres_name = pres_name;
    }

    public String getVpres_name() {
        return vpres_name;
    }

    public void setVpres_name(String vpres_name) {
        this.vpres_name = vpres_name;
    }

    public long getVotes_for() {
        return votes_for;
    }

    public void setVotes_for(long votes_for) {
        this.votes_for = votes_for;
    }

    public float getVote_percent() {
        return vote_percent;
    }

    public void setVote_percent(float vote_percent) {
        this.vote_percent = vote_percent;
    }

    public int getEc_vote() {
        return ec_vote;
    }

    public void setEc_vote(int ec_vote) {
        this.ec_vote = ec_vote;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public boolean isIs_winner() {
        return is_winner;
    }

    public void setIs_winner(boolean is_winner) {
        this.is_winner = is_winner;
    }
}
