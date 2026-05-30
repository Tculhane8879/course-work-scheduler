package com.scheduler.backend.course;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }
}