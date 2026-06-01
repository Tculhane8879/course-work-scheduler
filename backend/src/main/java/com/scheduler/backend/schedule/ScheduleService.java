package com.scheduler.backend.schedule;

import com.scheduler.backend.availability.AvailabilityBlockRepository;
import com.scheduler.backend.course.Course;
import com.scheduler.backend.course.CourseNotFoundException;
import com.scheduler.backend.course.CourseRepository;
import com.scheduler.backend.preference.SchedulePreference;
import com.scheduler.backend.preference.SchedulePreferenceRepository;
import com.scheduler.backend.section.Section;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final CourseRepository courseRepository;
    private final AvailabilityBlockRepository availabilityBlockRepository;
    private final SchedulePreferenceRepository schedulePreferenceRepository;
    private final ScheduleGenerator scheduleGenerator;

    public ScheduleService(
            CourseRepository courseRepository,
            AvailabilityBlockRepository availabilityBlockRepository,
            SchedulePreferenceRepository schedulePreferenceRepository,
            ScheduleGenerator scheduleGenerator
    ) {
        this.courseRepository = courseRepository;
        this.availabilityBlockRepository = availabilityBlockRepository;
        this.schedulePreferenceRepository = schedulePreferenceRepository;
        this.scheduleGenerator = scheduleGenerator;
    }

    public List<ScheduleOptionResponse> generateSchedules(ScheduleGenerationRequest request) {
        List<Long> courseIds = new LinkedHashSet<>(request.getCourseIds()).stream().toList();
        List<Course> courses = courseRepository.findAllById(courseIds);

        for (Long courseId : courseIds) {
            if (courses.stream().noneMatch(course -> course.getId().equals(courseId))) {
                throw new CourseNotFoundException(courseId);
            }
        }

        List<List<Section>> sectionOptionsByCourse = courseIds.stream()
                .map(courseId -> courses.stream()
                        .filter(course -> course.getId().equals(courseId))
                        .findFirst()
                        .orElseThrow(() -> new CourseNotFoundException(courseId))
                )
                .map(Course::getSections)
                .filter(sections -> !sections.isEmpty())
                .toList();

        if (sectionOptionsByCourse.size() != courseIds.size()) {
            return List.of();
        }

        SchedulePreference preferences = schedulePreferenceRepository
                .findById(SchedulePreference.SINGLETON_ID)
                .orElseGet(SchedulePreference::defaults);

        return scheduleGenerator.generate(
                sectionOptionsByCourse,
                availabilityBlockRepository.findAll(),
                preferences,
                request.getMaxResults()
        );
    }
}
