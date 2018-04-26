package com.sbu.data.entitys;
import java.util.List;

public class Update {
    List<Move> moves;
    float compactness;
    double populationDeviation;
    boolean finished;

    public Update(List<Move> moves, float compactness, float populationDeviation, boolean finished) {
        this.moves = moves;
        this.compactness = compactness;
        this.populationDeviation = populationDeviation;
        this.finished = finished;
    }
    public Update() {

    }

    public Update(List<Move> moves) {

    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public float getCompactness() {
        return compactness;
    }

    public void setCompactness(float compactness) {
        this.compactness = compactness;
    }

    public double getPopulationDeviation() {
        return populationDeviation;
    }

    public void setPopulationDeviation(double populationDeviation) {
        this.populationDeviation = populationDeviation;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
