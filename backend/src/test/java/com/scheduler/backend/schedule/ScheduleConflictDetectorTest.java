package com.scheduler.backend.schedule;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleConflictDetectorTest {

    private final ScheduleConflictDetector detector = new ScheduleConflictDetector();

    @Test
    void overlappingBlocksOnSameDayConflict() {
        TimeBlock first = block(DayOfWeek.MONDAY, "09:00", "10:15");
        TimeBlock second = block(DayOfWeek.MONDAY, "10:00", "11:15");

        assertThat(detector.overlaps(first, second)).isTrue();
    }

    @Test
    void backToBackBlocksDoNotConflict() {
        TimeBlock first = block(DayOfWeek.MONDAY, "09:00", "10:15");
        TimeBlock second = block(DayOfWeek.MONDAY, "10:15", "11:15");

        assertThat(detector.overlaps(first, second)).isFalse();
    }

    @Test
    void blocksOnDifferentDaysDoNotConflict() {
        TimeBlock first = block(DayOfWeek.MONDAY, "09:00", "10:15");
        TimeBlock second = block(DayOfWeek.TUESDAY, "09:30", "10:30");

        assertThat(detector.overlaps(first, second)).isFalse();
    }

    @Test
    void detectsExternalBlockConflict() {
        List<TimeBlock> scheduleBlocks = List.of(block(DayOfWeek.WEDNESDAY, "13:00", "14:15"));
        List<TimeBlock> externalBlocks = List.of(block(DayOfWeek.WEDNESDAY, "12:00", "13:30"));

        assertThat(detector.conflictsWithExternalBlocks(scheduleBlocks, externalBlocks)).isTrue();
    }

    private TimeBlock block(DayOfWeek dayOfWeek, String startTime, String endTime) {
        return new TimeBlock(dayOfWeek, LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
}
