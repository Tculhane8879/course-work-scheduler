package com.scheduler.backend.preference;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class SchedulePreferenceRequest {

    private boolean avoidEarlyMorning;
    private boolean avoidLateEvening;
    private boolean preferCompactSchedule;
    private boolean preferNoFridayClasses;

    @Min(value = 1, message = "Max classes per day must be at least 1")
    @Max(value = 8, message = "Max classes per day cannot be greater than 8")
    private int maxClassesPerDay = 3;

    @Min(value = 0, message = "Minimum gap must be zero or greater")
    @Max(value = 240, message = "Minimum gap cannot be greater than 240 minutes")
    private int minimumGapMinutes = 30;

    public boolean isAvoidEarlyMorning() {
        return avoidEarlyMorning;
    }

    public void setAvoidEarlyMorning(boolean avoidEarlyMorning) {
        this.avoidEarlyMorning = avoidEarlyMorning;
    }

    public boolean isAvoidLateEvening() {
        return avoidLateEvening;
    }

    public void setAvoidLateEvening(boolean avoidLateEvening) {
        this.avoidLateEvening = avoidLateEvening;
    }

    public boolean isPreferCompactSchedule() {
        return preferCompactSchedule;
    }

    public void setPreferCompactSchedule(boolean preferCompactSchedule) {
        this.preferCompactSchedule = preferCompactSchedule;
    }

    public boolean isPreferNoFridayClasses() {
        return preferNoFridayClasses;
    }

    public void setPreferNoFridayClasses(boolean preferNoFridayClasses) {
        this.preferNoFridayClasses = preferNoFridayClasses;
    }

    public int getMaxClassesPerDay() {
        return maxClassesPerDay;
    }

    public void setMaxClassesPerDay(int maxClassesPerDay) {
        this.maxClassesPerDay = maxClassesPerDay;
    }

    public int getMinimumGapMinutes() {
        return minimumGapMinutes;
    }

    public void setMinimumGapMinutes(int minimumGapMinutes) {
        this.minimumGapMinutes = minimumGapMinutes;
    }
}
