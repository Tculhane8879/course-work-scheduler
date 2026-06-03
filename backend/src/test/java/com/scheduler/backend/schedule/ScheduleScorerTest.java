package com.scheduler.backend.schedule;

import com.scheduler.backend.preference.SchedulePreference;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleScorerTest {

    private final ScheduleScorer scorer = new ScheduleScorer();

    @Test
    void appliesPreferencePenalties() {
        SchedulePreference preferences = new SchedulePreference(
                true,
                true,
                true,
                true,
                3,
                30
        );
        List<TimeBlock> blocks = List.of(
                block(DayOfWeek.FRIDAY, "08:00", "09:00"),
                block(DayOfWeek.FRIDAY, "18:00", "19:15")
        );

        ScheduleScoreResponse score = scorer.score(blocks, preferences);

        assertThat(score.getPreferencePenalty()).isGreaterThan(0);
        assertThat(score.getTotal()).isLessThan(100);
    }

    @Test
    void penalizesDaysOverMaxClasses() {
        SchedulePreference preferences = new SchedulePreference(
                false,
                false,
                false,
                false,
                1,
                30
        );
        List<TimeBlock> blocks = List.of(
                block(DayOfWeek.MONDAY, "09:00", "10:00"),
                block(DayOfWeek.MONDAY, "11:00", "12:00")
        );

        ScheduleScoreResponse score = scorer.score(blocks, preferences);

        assertThat(score.getMaxClassesPenalty()).isEqualTo(10);
    }

    private TimeBlock block(DayOfWeek dayOfWeek, String startTime, String endTime) {
        return new TimeBlock(dayOfWeek, LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
}
