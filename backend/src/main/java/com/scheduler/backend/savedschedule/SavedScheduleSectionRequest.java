package com.scheduler.backend.savedschedule;

import com.scheduler.backend.section.SectionModality;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SavedScheduleSectionRequest {

    private Long courseId;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotBlank(message = "Course name is required")
    private String courseName;

    private Long sectionId;

    @NotBlank(message = "Section code is required")
    private String sectionCode;

    private String instructor;

    private String location;

    @NotNull(message = "Section modality is required")
    private SectionModality modality;

    @Valid
    private List<SavedScheduleMeetingRequest> meetings = new ArrayList<>();

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

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

    public List<SavedScheduleMeetingRequest> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<SavedScheduleMeetingRequest> meetings) {
        this.meetings = meetings == null ? new ArrayList<>() : meetings;
    }
}
