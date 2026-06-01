package com.scheduler.backend.schedule;

import com.scheduler.backend.section.SectionModality;

import java.util.List;

public class ScheduleSectionResponse {

    private Long courseId;
    private String courseCode;
    private String courseName;
    private Long sectionId;
    private String sectionCode;
    private String instructor;
    private String location;
    private SectionModality modality;
    private List<ScheduleMeetingResponse> meetings;

    public ScheduleSectionResponse(
            Long courseId,
            String courseCode,
            String courseName,
            Long sectionId,
            String sectionCode,
            String instructor,
            String location,
            SectionModality modality,
            List<ScheduleMeetingResponse> meetings
    ) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionId = sectionId;
        this.sectionCode = sectionCode;
        this.instructor = instructor;
        this.location = location;
        this.modality = modality;
        this.meetings = meetings;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

    public SectionModality getModality() {
        return modality;
    }

    public List<ScheduleMeetingResponse> getMeetings() {
        return meetings;
    }
}
