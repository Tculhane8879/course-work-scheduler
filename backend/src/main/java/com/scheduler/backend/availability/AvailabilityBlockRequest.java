package com.scheduler.backend.availability;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilityBlockRequest {

    @NotBlank(message = "Availability label is required")
    private String label;

    @NotNull(message = "Availability day is required")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Availability start time is required")
    private LocalTime startTime;

    @NotNull(message = "Availability end time is required")
    private LocalTime endTime;

    @NotNull(message = "Availability type is required")
    private AvailabilityBlockType blockType;

    @AssertTrue(message = "Availability end time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        if (startTime == null || endTime == null) {
            return true;
        }

        return endTime.isAfter(startTime);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public AvailabilityBlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(AvailabilityBlockType blockType) {
        this.blockType = blockType;
    }
}
