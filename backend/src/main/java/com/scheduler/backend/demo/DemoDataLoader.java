package com.scheduler.backend.demo;

import com.scheduler.backend.availability.AvailabilityBlock;
import com.scheduler.backend.availability.AvailabilityBlockRepository;
import com.scheduler.backend.availability.AvailabilityBlockType;
import com.scheduler.backend.course.Course;
import com.scheduler.backend.course.CoursePriority;
import com.scheduler.backend.course.CourseRepository;
import com.scheduler.backend.preference.SchedulePreference;
import com.scheduler.backend.preference.SchedulePreferenceRepository;
import com.scheduler.backend.section.Section;
import com.scheduler.backend.section.SectionMeeting;
import com.scheduler.backend.section.SectionModality;
import com.scheduler.backend.section.SectionRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
@ConditionalOnProperty(name = "app.demo-data.enabled", havingValue = "true")
public class DemoDataLoader implements ApplicationRunner {

    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final AvailabilityBlockRepository availabilityBlockRepository;
    private final SchedulePreferenceRepository schedulePreferenceRepository;

    public DemoDataLoader(
            CourseRepository courseRepository,
            SectionRepository sectionRepository,
            AvailabilityBlockRepository availabilityBlockRepository,
            SchedulePreferenceRepository schedulePreferenceRepository
    ) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.availabilityBlockRepository = availabilityBlockRepository;
        this.schedulePreferenceRepository = schedulePreferenceRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedCourses();
        seedAvailabilityBlocks();
        seedSchedulePreferences();
    }

    private void seedCourses() {
        if (courseRepository.count() > 0) {
            return;
        }

        Course dataStructures = courseRepository.save(new Course(
                "CS 201",
                "Data Structures",
                CoursePriority.REQUIRED
        ));
        saveSection(dataStructures, "A", "Dr. Rivera", "Science Hall 214", SectionModality.IN_PERSON,
                meeting(DayOfWeek.MONDAY, "10:00", "11:15"),
                meeting(DayOfWeek.WEDNESDAY, "10:00", "11:15"));
        saveSection(dataStructures, "B", "Prof. Chen", "Engineering 118", SectionModality.IN_PERSON,
                meeting(DayOfWeek.TUESDAY, "14:00", "15:15"),
                meeting(DayOfWeek.THURSDAY, "14:00", "15:15"));

        Course linearAlgebra = courseRepository.save(new Course(
                "MATH 241",
                "Linear Algebra",
                CoursePriority.REQUIRED
        ));
        saveSection(linearAlgebra, "A", "Dr. Malik", "Math 302", SectionModality.IN_PERSON,
                meeting(DayOfWeek.TUESDAY, "09:30", "10:45"),
                meeting(DayOfWeek.THURSDAY, "09:30", "10:45"));
        saveSection(linearAlgebra, "B", "Dr. Brooks", "Math 210", SectionModality.HYBRID,
                meeting(DayOfWeek.MONDAY, "13:00", "13:50"),
                meeting(DayOfWeek.WEDNESDAY, "13:00", "13:50"),
                meeting(DayOfWeek.FRIDAY, "13:00", "13:50"));

        Course technicalWriting = courseRepository.save(new Course(
                "ENG 210",
                "Technical Writing",
                CoursePriority.ELECTIVE
        ));
        saveSection(technicalWriting, "A", "Prof. Nguyen", "Humanities 104", SectionModality.IN_PERSON,
                meeting(DayOfWeek.TUESDAY, "16:00", "17:15"),
                meeting(DayOfWeek.THURSDAY, "16:00", "17:15"));
        saveSection(technicalWriting, "B", "Prof. Walker", "Online", SectionModality.ONLINE,
                meeting(DayOfWeek.WEDNESDAY, "09:00", "10:15"));

        Course microeconomics = courseRepository.save(new Course(
                "ECON 101",
                "Microeconomics",
                CoursePriority.ELECTIVE
        ));
        saveSection(microeconomics, "A", "Dr. Patel", "Business 120", SectionModality.IN_PERSON,
                meeting(DayOfWeek.MONDAY, "14:00", "15:15"),
                meeting(DayOfWeek.WEDNESDAY, "14:00", "15:15"));
        saveSection(microeconomics, "B", "Dr. Foster", "Business 210", SectionModality.IN_PERSON,
                meeting(DayOfWeek.FRIDAY, "10:00", "12:00"));
    }

    private void seedAvailabilityBlocks() {
        if (availabilityBlockRepository.count() > 0) {
            return;
        }

        availabilityBlockRepository.save(new AvailabilityBlock(
                "Part-time job",
                DayOfWeek.TUESDAY,
                LocalTime.parse("12:00"),
                LocalTime.parse("14:00"),
                AvailabilityBlockType.WORK
        ));
        availabilityBlockRepository.save(new AvailabilityBlock(
                "Part-time job",
                DayOfWeek.THURSDAY,
                LocalTime.parse("12:00"),
                LocalTime.parse("14:00"),
                AvailabilityBlockType.WORK
        ));
        availabilityBlockRepository.save(new AvailabilityBlock(
                "Weekly lab shift",
                DayOfWeek.WEDNESDAY,
                LocalTime.parse("15:00"),
                LocalTime.parse("18:00"),
                AvailabilityBlockType.UNAVAILABLE
        ));
        availabilityBlockRepository.save(new AvailabilityBlock(
                "Keep Friday afternoons open",
                DayOfWeek.FRIDAY,
                LocalTime.parse("13:00"),
                LocalTime.parse("17:00"),
                AvailabilityBlockType.PREFERENCE
        ));
    }

    private void seedSchedulePreferences() {
        schedulePreferenceRepository.findById(SchedulePreference.SINGLETON_ID)
                .orElseGet(() -> schedulePreferenceRepository.save(new SchedulePreference(
                        true,
                        true,
                        true,
                        true,
                        3,
                        45
                )));
    }

    private void saveSection(
            Course course,
            String sectionCode,
            String instructor,
            String location,
            SectionModality modality,
            SectionMeeting... meetings
    ) {
        Section section = new Section(course, sectionCode, instructor, location, modality);
        for (SectionMeeting meeting : meetings) {
            section.addMeeting(meeting);
        }
        sectionRepository.save(section);
    }

    private SectionMeeting meeting(DayOfWeek dayOfWeek, String startTime, String endTime) {
        return new SectionMeeting(dayOfWeek, LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
}
