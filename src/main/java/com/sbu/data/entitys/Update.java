package com.sbu.data.entitys;

import java.util.ArrayList;
import java.util.List;

public class Update {
    List<Move> moves;
    double compactness;
    double populationDeviation;
    boolean finished;

    public Update(List<Move> moves, float compactness, float populationDeviation, boolean finished) {
        this.moves = moves;
        this.compactness = compactness;
        this.populationDeviation = populationDeviation;
        this.finished = finished;
    }

    public Update(List<Move> moves) {
        this.moves = moves;
    }
    public Update(Update update) {
        this.moves = new ArrayList<>(update.getMoves());
        this.setCompactness(update.getCompactness());
        this.setFinished(update.isFinished());
        this.setPopulationDeviation(update.getPopulationDeviation());
    }


    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
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
