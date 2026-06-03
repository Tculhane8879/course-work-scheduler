package com.scheduler.backend.schedule;

import com.scheduler.backend.availability.AvailabilityBlock;
import com.scheduler.backend.availability.AvailabilityBlockType;
import com.scheduler.backend.course.Course;
import com.scheduler.backend.course.CoursePriority;
import com.scheduler.backend.preference.SchedulePreference;
import com.scheduler.backend.section.Section;
import com.scheduler.backend.section.SectionMeeting;
import com.scheduler.backend.section.SectionModality;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleGeneratorTest {

    private final ScheduleGenerator generator = new ScheduleGenerator(
            new ScheduleConflictDetector(),
            new ScheduleScorer()
    );

    @Test
    void returnsValidCombinationsOnly() {
        Course firstCourse = new Course("CS 201", "Data Structures", CoursePriority.REQUIRED);
        Course secondCourse = new Course("MATH 120", "Calculus I", CoursePriority.REQUIRED);
        Section csMorning = section(firstCourse, "A", DayOfWeek.MONDAY, "09:00", "10:00");
        Section csConflict = section(firstCourse, "B", DayOfWeek.MONDAY, "11:00", "12:00");
        Section math = section(secondCourse, "A", DayOfWeek.MONDAY, "11:30", "12:30");

        List<ScheduleOptionResponse> options = generator.generate(
                List.of(List.of(csMorning, csConflict), List.of(math)),
                List.of(),
                SchedulePreference.defaults(),
                10
        );

        assertThat(options).hasSize(1);
        assertThat(options.get(0).getSections())
                .extracting(ScheduleSectionResponse::getSectionCode)
                .containsExactly("A", "A");
    }

    @Test
    void rejectsCombinationsThatConflictWithWorkBlocks() {
        Course course = new Course("CS 201", "Data Structures", CoursePriority.REQUIRED);
        Section morning = section(course, "A", DayOfWeek.TUESDAY, "09:00", "10:00");
        Section afternoon = section(course, "B", DayOfWeek.TUESDAY, "14:00", "15:00");
        AvailabilityBlock work = new AvailabilityBlock(
                "Work",
                DayOfWeek.TUESDAY,
                LocalTime.parse("08:30"),
                LocalTime.parse("10:30"),
                AvailabilityBlockType.WORK
        );

        List<ScheduleOptionResponse> options = generator.generate(
                List.of(List.of(morning, afternoon)),
                List.of(work),
                SchedulePreference.defaults(),
                10
        );

        assertThat(options).hasSize(1);
        assertThat(options.get(0).getSections().get(0).getSectionCode()).isEqualTo("B");
    }

    private Section section(Course course, String sectionCode, DayOfWeek dayOfWeek, String startTime, String endTime) {
        Section section = new Section(course, sectionCode, null, null, SectionModality.IN_PERSON);
        section.addMeeting(new SectionMeeting(dayOfWeek, LocalTime.parse(startTime), LocalTime.parse(endTime)));
        return section;
    }
}
