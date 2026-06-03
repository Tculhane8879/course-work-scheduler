package com.scheduler.backend.schedule;

import java.util.List;

public class ScheduleOptionResponse {

    private ScheduleScoreResponse score;
    private List<ScheduleSectionResponse> sections;

    public ScheduleOptionResponse(ScheduleScoreResponse score, List<ScheduleSectionResponse> sections) {
        this.score = score;
        this.sections = sections;
    }

    public ScheduleScoreResponse getScore() {
        return score;
    }

    public List<ScheduleSectionResponse> getSections() {
        return sections;
    }
}
