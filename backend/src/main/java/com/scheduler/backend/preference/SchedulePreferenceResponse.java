package com.scheduler.backend.preference;

import java.time.Instant;

public class SchedulePreferenceResponse {

    private Long id;
    private boolean avoidEarlyMorning;
    private boolean avoidLateEvening;
    private boolean preferCompactSchedule;
    private boolean preferNoFridayClasses;
    private int maxClassesPerDay;
    private int minimumGapMinutes;
    private Instant updatedAt;

    public SchedulePreferenceResponse(
            Long id,
            boolean avoidEarlyMorning,
            boolean avoidLateEvening,
            boolean preferCompactSchedule,
            boolean preferNoFridayClasses,
            int maxClassesPerDay,
            int minimumGapMinutes,
            Instant updatedAt
    ) {
        this.id = id;
        this.avoidEarlyMorning = avoidEarlyMorning;
        this.avoidLateEvening = avoidLateEvening;
        this.preferCompactSchedule = preferCompactSchedule;
        this.preferNoFridayClasses = preferNoFridayClasses;
        this.maxClassesPerDay = maxClassesPerDay;
        this.minimumGapMinutes = minimumGapMinutes;
        this.updatedAt = updatedAt;
    }

    public static SchedulePreferenceResponse fromEntity(SchedulePreference preference) {
        return new SchedulePreferenceResponse(
                preference.getId(),
                preference.isAvoidEarlyMorning(),
                preference.isAvoidLateEvening(),
                preference.isPreferCompactSchedule(),
                preference.isPreferNoFridayClasses(),
                preference.getMaxClassesPerDay(),
                preference.getMinimumGapMinutes(),
                preference.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public boolean isAvoidEarlyMorning() {
        return avoidEarlyMorning;
    }

    public boolean isAvoidLateEvening() {
        return avoidLateEvening;
    }

    public boolean isPreferCompactSchedule() {
        return preferCompactSchedule;
    }

    public boolean isPreferNoFridayClasses() {
        return preferNoFridayClasses;
    }

    public int getMaxClassesPerDay() {
        return maxClassesPerDay;
    }

    public int getMinimumGapMinutes() {
        return minimumGapMinutes;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
