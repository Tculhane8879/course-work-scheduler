package com.scheduler.backend.section;

import com.scheduler.backend.course.Course;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotBlank
    @Column(nullable = false)
    private String sectionCode;

    private String instructor;

    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SectionModality modality;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionMeeting> meetings = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Section() {
        // Required by JPA
    }

    public Section(
            Course course,
            String sectionCode,
            String instructor,
            String location,
            SectionModality modality
    ) {
        this.course = course;
        this.sectionCode = sectionCode;
        this.instructor = instructor;
        this.location = location;
        this.modality = modality;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public void replaceMeetings(List<SectionMeeting> replacementMeetings) {
        meetings.clear();
        replacementMeetings.forEach(this::addMeeting);
    }

    public void addMeeting(SectionMeeting meeting) {
        meeting.setSection(this);
        meetings.add(meeting);
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
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

    public List<SectionMeeting> getMeetings() {
        return meetings;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
