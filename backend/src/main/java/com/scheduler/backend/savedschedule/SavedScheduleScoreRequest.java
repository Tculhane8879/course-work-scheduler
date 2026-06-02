package com.scheduler.backend.savedschedule;

public class SavedScheduleScoreRequest {

    private int total;
    private int preferencePenalty;
    private int compactnessScore;
    private int gapScore;
    private int maxClassesPenalty;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPreferencePenalty() {
        return preferencePenalty;
    }

    public void setPreferencePenalty(int preferencePenalty) {
        this.preferencePenalty = preferencePenalty;
    }

    public int getCompactnessScore() {
        return compactnessScore;
    }

    public void setCompactnessScore(int compactnessScore) {
        this.compactnessScore = compactnessScore;
    }

    public int getGapScore() {
        return gapScore;
    }

    public void setGapScore(int gapScore) {
        this.gapScore = gapScore;
    }

    public int getMaxClassesPenalty() {
        return maxClassesPenalty;
    }

    public void setMaxClassesPenalty(int maxClassesPenalty) {
        this.maxClassesPenalty = maxClassesPenalty;
    }
}
