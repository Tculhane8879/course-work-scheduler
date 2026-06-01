package com.scheduler.backend.preference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.Instant;

@Entity
@Table(name = "schedule_preferences")
public class SchedulePreference {

    public static final Long SINGLETON_ID = 1L;

    @Id
    private Long id;

    @Column(nullable = false)
    private boolean avoidEarlyMorning;

    @Column(nullable = false)
    private boolean avoidLateEvening;

    @Column(nullable = false)
    private boolean preferCompactSchedule;

    @Column(nullable = false)
    private boolean preferNoFridayClasses;

    @Min(1)
    @Column(nullable = false)
    private int maxClassesPerDay;

    @Min(0)
    @Column(nullable = false)
    private int minimumGapMinutes;

    @Column(nullable = false)
    private Instant updatedAt;

    protected SchedulePreference() {
        // Required by JPA
    }

    public SchedulePreference(
            boolean avoidEarlyMorning,
            boolean avoidLateEvening,
            boolean preferCompactSchedule,
            boolean preferNoFridayClasses,
            int maxClassesPerDay,
            int minimumGapMinutes
    ) {
        this.id = SINGLETON_ID;
        apply(
                avoidEarlyMorning,
                avoidLateEvening,
                preferCompactSchedule,
                preferNoFridayClasses,
                maxClassesPerDay,
                minimumGapMinutes
        );
    }

    public static SchedulePreference defaults() {
        return new SchedulePreference(false, false, true, false, 3, 30);
    }

    public void apply(
            boolean avoidEarlyMorning,
            boolean avoidLateEvening,
            boolean preferCompactSchedule,
            boolean preferNoFridayClasses,
            int maxClassesPerDay,
            int minimumGapMinutes
    ) {
        this.avoidEarlyMorning = avoidEarlyMorning;
        this.avoidLateEvening = avoidLateEvening;
        this.preferCompactSchedule = preferCompactSchedule;
        this.preferNoFridayClasses = preferNoFridayClasses;
        this.maxClassesPerDay = maxClassesPerDay;
        this.minimumGapMinutes = minimumGapMinutes;
        this.updatedAt = Instant.now();
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
