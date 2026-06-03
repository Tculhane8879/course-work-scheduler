package com.scheduler.backend.savedschedule;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SavedScheduleRequest {

    @NotBlank(message = "Saved schedule name is required")
    private String name;

    @Valid
    @NotNull(message = "Saved schedule score is required")
    private SavedScheduleScoreRequest score;

    @Valid
    @NotEmpty(message = "Saved schedule must include at least one section")
    private List<SavedScheduleSectionRequest> sections = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SavedScheduleScoreRequest getScore() {
        return score;
    }

    public void setScore(SavedScheduleScoreRequest score) {
        this.score = score;
    }

    public List<SavedScheduleSectionRequest> getSections() {
        return sections;
    }

    public void setSections(List<SavedScheduleSectionRequest> sections) {
        this.sections = sections == null ? new ArrayList<>() : sections;
    }
}
