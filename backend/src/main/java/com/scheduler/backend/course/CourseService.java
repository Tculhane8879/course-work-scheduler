package com.scheduler.backend.course;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }

    public CourseResponse createCourse(CourseRequest request) {
        Course course = new Course(
                request.getCode().trim(),
                request.getName().trim(),
                request.getPriority()
        );

        Course savedCourse = courseRepository.save(course);
        return CourseResponse.fromEntity(savedCourse);
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        return CourseResponse.fromEntity(course);
    }

    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        course.setCode(request.getCode().trim());
        course.setName(request.getName().trim());
        course.setPriority(request.getPriority());

        Course updatedCourse = courseRepository.save(course);
        return CourseResponse.fromEntity(updatedCourse);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }

        courseRepository.deleteById(id);
    }
}