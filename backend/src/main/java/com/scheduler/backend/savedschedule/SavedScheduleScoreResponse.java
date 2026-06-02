package com.scheduler.backend.savedschedule;

public class SavedScheduleScoreResponse {

    private int total;
    private int preferencePenalty;
    private int compactnessScore;
    private int gapScore;
    private int maxClassesPenalty;

    public SavedScheduleScoreResponse(
            int total,
            int preferencePenalty,
            int compactnessScore,
            int gapScore,
            int maxClassesPenalty
    ) {
        this.total = total;
        this.preferencePenalty = preferencePenalty;
        this.compactnessScore = compactnessScore;
        this.gapScore = gapScore;
        this.maxClassesPenalty = maxClassesPenalty;
    }

    public int getTotal() {
        return total;
    }

    public int getPreferencePenalty() {
        return preferencePenalty;
    }

    public int getCompactnessScore() {
        return compactnessScore;
    }

    public int getGapScore() {
        return gapScore;
    }

    public int getMaxClassesPenalty() {
        return maxClassesPenalty;
    }
}
