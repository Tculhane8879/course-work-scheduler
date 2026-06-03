package com.scheduler.backend.savedschedule;

import com.scheduler.backend.section.SectionModality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "saved_schedule_sections")
public class SavedScheduleSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "saved_schedule_id", nullable = false)
    private SavedSchedule savedSchedule;

    private Long courseId;

    @NotBlank
    @Column(nullable = false)
    private String courseCode;

    @NotBlank
    @Column(nullable = false)
    private String courseName;

    private Long sectionId;

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
    private List<SavedScheduleMeeting> meetings = new ArrayList<>();

    protected SavedScheduleSection() {
        // Required by JPA
    }

    public SavedScheduleSection(
            Long courseId,
            String courseCode,
            String courseName,
            Long sectionId,
            String sectionCode,
            String instructor,
            String location,
            SectionModality modality
    ) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionId = sectionId;
        this.sectionCode = sectionCode;
        this.instructor = instructor;
        this.location = location;
        this.modality = modality;
    }

    void setSavedSchedule(SavedSchedule savedSchedule) {
        this.savedSchedule = savedSchedule;
    }

    public void addMeeting(SavedScheduleMeeting meeting) {
        meeting.setSection(this);
        meetings.add(meeting);
    }

    public Long getId() {
        return id;
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

    public List<SavedScheduleMeeting> getMeetings() {
        return meetings;
    }
}
