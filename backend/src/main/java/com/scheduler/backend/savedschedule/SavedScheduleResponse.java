package com.scheduler.backend.savedschedule;

import java.time.Instant;
import java.util.List;

public class SavedScheduleResponse {

    private Long id;
    private String name;
    private boolean preferred;
    private SavedScheduleScoreResponse score;
    private List<SavedScheduleSectionResponse> sections;
    private Instant createdAt;

    public SavedScheduleResponse(
            Long id,
            String name,
            boolean preferred,
            SavedScheduleScoreResponse score,
            List<SavedScheduleSectionResponse> sections,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.preferred = preferred;
        this.score = score;
        this.sections = sections;
        this.createdAt = createdAt;
    }

    public static SavedScheduleResponse fromEntity(SavedSchedule savedSchedule) {
        return new SavedScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getName(),
                savedSchedule.isPreferred(),
                new SavedScheduleScoreResponse(
                        savedSchedule.getScoreTotal(),
                        savedSchedule.getPreferencePenalty(),
                        savedSchedule.getCompactnessScore(),
                        savedSchedule.getGapScore(),
                        savedSchedule.getMaxClassesPenalty()
                ),
                savedSchedule.getSections()
                        .stream()
                        .map(SavedScheduleSectionResponse::fromEntity)
                        .toList(),
                savedSchedule.getCreatedAt()
        );
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

    public SavedScheduleScoreResponse getScore() {
        return score;
    }

    public List<SavedScheduleSectionResponse> getSections() {
        return sections;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
