package com.scheduler.backend.savedschedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class SavedScheduleMeetingResponse {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public SavedScheduleMeetingResponse(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static SavedScheduleMeetingResponse fromEntity(SavedScheduleMeeting meeting) {
        return new SavedScheduleMeetingResponse(
                meeting.getDayOfWeek(),
                meeting.getStartTime(),
                meeting.getEndTime()
        );
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
}
