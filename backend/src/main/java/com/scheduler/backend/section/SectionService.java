package com.scheduler.backend.section;

import com.scheduler.backend.course.Course;
import com.scheduler.backend.course.CourseNotFoundException;
import com.scheduler.backend.course.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SectionService {

    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    public SectionService(CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<SectionResponse> getSectionsForCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        return sectionRepository.findByCourseIdOrderBySectionCodeAsc(courseId)
                .stream()
                .map(SectionResponse::fromEntity)
                .toList();
    }

    @Transactional
    public SectionResponse createSection(Long courseId, SectionRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        Section section = new Section(
                course,
                request.getSectionCode().trim(),
                trimToNull(request.getInstructor()),
                trimToNull(request.getLocation()),
                request.getModality()
        );
        section.replaceMeetings(toMeetings(request));

        Section savedSection = sectionRepository.save(section);
        return SectionResponse.fromEntity(savedSection);
    }

    public SectionResponse getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));

        return SectionResponse.fromEntity(section);
    }

    @Transactional
    public SectionResponse updateSection(Long id, SectionRequest request) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new SectionNotFoundException(id));

        section.setSectionCode(request.getSectionCode().trim());
        section.setInstructor(trimToNull(request.getInstructor()));
        section.setLocation(trimToNull(request.getLocation()));
        section.setModality(request.getModality());
        section.replaceMeetings(toMeetings(request));

        Section updatedSection = sectionRepository.save(section);
        return SectionResponse.fromEntity(updatedSection);
    }

    @Transactional
    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new SectionNotFoundException(id);
        }

        sectionRepository.deleteById(id);
    }

    private List<SectionMeeting> toMeetings(SectionRequest request) {
        return request.getMeetings()
                .stream()
                .map(meeting -> new SectionMeeting(
                        meeting.getDayOfWeek(),
                        meeting.getStartTime(),
                        meeting.getEndTime()
                ))
                .toList();
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}
