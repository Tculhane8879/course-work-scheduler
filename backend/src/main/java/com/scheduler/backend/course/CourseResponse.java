package com.scheduler.backend.course;

import java.time.Instant;

public class CourseResponse {

    private Long id;
    private String code;
    private String name;
    private CoursePriority priority;
    private Instant createdAt;

    public CourseResponse(Long id, String code, String name, CoursePriority priority, Instant createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public static CourseResponse fromEntity(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCode(),
                course.getName(),
                course.getPriority(),
                course.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public CoursePriority getPriority() {
        return priority;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}