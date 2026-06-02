package com.scheduler.backend.savedschedule;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class SavedScheduleMeetingRequest {

    @NotNull(message = "Meeting day is required")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Meeting start time is required")
    private LocalTime startTime;

    @NotNull(message = "Meeting end time is required")
    private LocalTime endTime;

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
}
