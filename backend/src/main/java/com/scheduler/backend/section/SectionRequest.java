package com.scheduler.backend.section;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SectionRequest {

    @NotBlank(message = "Section code is required")
    private String sectionCode;

    private String instructor;

    private String location;

    @NotNull(message = "Section modality is required")
    private SectionModality modality;

    @Valid
    private List<SectionMeetingRequest> meetings = new ArrayList<>();

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SectionModality getModality() {
        return modality;
    }

    public void setModality(SectionModality modality) {
        this.modality = modality;
    }

    public List<SectionMeetingRequest> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<SectionMeetingRequest> meetings) {
        this.meetings = meetings == null ? new ArrayList<>() : meetings;
    }
}
