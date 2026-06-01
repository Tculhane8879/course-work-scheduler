package com.scheduler.backend.availability;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;

public class AvailabilityBlockResponse {

    private Long id;
    private String label;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private AvailabilityBlockType blockType;
    private Instant createdAt;

    public AvailabilityBlockResponse(
            Long id,
            String label,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            AvailabilityBlockType blockType,
            Instant createdAt
    ) {
        this.id = id;
        this.label = label;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.blockType = blockType;
        this.createdAt = createdAt;
    }

    public static AvailabilityBlockResponse fromEntity(AvailabilityBlock block) {
        return new AvailabilityBlockResponse(
                block.getId(),
                block.getLabel(),
                block.getDayOfWeek(),
                block.getStartTime(),
                block.getEndTime(),
                block.getBlockType(),
                block.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public AvailabilityBlockType getBlockType() {
        return blockType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
