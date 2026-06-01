package com.scheduler.backend.schedule;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerationRequest {

    @NotEmpty(message = "At least one course is required")
    private List<Long> courseIds = new ArrayList<>();

    @Min(value = 1, message = "Max results must be at least 1")
    @Max(value = 25, message = "Max results cannot be greater than 25")
    private int maxResults = 10;

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds == null ? new ArrayList<>() : courseIds;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
