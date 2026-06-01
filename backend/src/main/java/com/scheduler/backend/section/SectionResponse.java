package com.scheduler.backend.section;

import java.time.Instant;
import java.util.List;

public class SectionResponse {

    private Long id;
    private Long courseId;
    private String sectionCode;
    private String instructor;
    private String location;
    private SectionModality modality;
    private List<SectionMeetingResponse> meetings;
    private Instant createdAt;

    public SectionResponse(
            Long id,
            Long courseId,
            String sectionCode,
            String instructor,
            String location,
            SectionModality modality,
            List<SectionMeetingResponse> meetings,
            Instant createdAt
    ) {
        this.id = id;
        this.courseId = courseId;
        this.sectionCode = sectionCode;
        this.instructor = instructor;
        this.location = location;
        this.modality = modality;
        this.meetings = meetings;
        this.createdAt = createdAt;
    }

    public static SectionResponse fromEntity(Section section) {
        return new SectionResponse(
                section.getId(),
                section.getCourse().getId(),
                section.getSectionCode(),
                section.getInstructor(),
                section.getLocation(),
                section.getModality(),
                section.getMeetings()
                        .stream()
                        .map(SectionMeetingResponse::fromEntity)
                        .toList(),
                section.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
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

    public List<SectionMeetingResponse> getMeetings() {
        return meetings;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
