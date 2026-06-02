package com.scheduler.backend.savedschedule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "saved_schedules")
public class SavedSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean preferred;

    @Column(nullable = false)
    private int scoreTotal;

    @Column(nullable = false)
    private int preferencePenalty;

    @Column(nullable = false)
    private int compactnessScore;

    @Column(nullable = false)
    private int gapScore;

    @Column(nullable = false)
    private int maxClassesPenalty;

    @OneToMany(mappedBy = "savedSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedScheduleSection> sections = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected SavedSchedule() {
        // Required by JPA
    }

    public SavedSchedule(
            String name,
            int scoreTotal,
            int preferencePenalty,
            int compactnessScore,
            int gapScore,
            int maxClassesPenalty
    ) {
        this.name = name;
        this.scoreTotal = scoreTotal;
        this.preferencePenalty = preferencePenalty;
        this.compactnessScore = compactnessScore;
        this.gapScore = gapScore;
        this.maxClassesPenalty = maxClassesPenalty;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public void addSection(SavedScheduleSection section) {
        section.setSavedSchedule(this);
        sections.add(section);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public int getScoreTotal() {
        return scoreTotal;
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

    public List<SavedScheduleSection> getSections() {
        return sections;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
