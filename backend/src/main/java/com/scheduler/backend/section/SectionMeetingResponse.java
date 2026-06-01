package com.scheduler.backend.section;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class SectionMeetingResponse {

    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public SectionMeetingResponse(Long id, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static SectionMeetingResponse fromEntity(SectionMeeting meeting) {
        return new SectionMeetingResponse(
                meeting.getId(),
                meeting.getDayOfWeek(),
                meeting.getStartTime(),
                meeting.getEndTime()
        );
    }

    public Long getId() {
        return id;
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
