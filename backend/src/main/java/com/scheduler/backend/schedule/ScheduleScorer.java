package com.scheduler.backend.schedule;

import com.scheduler.backend.preference.SchedulePreference;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduleScorer {

    private static final LocalTime EARLY_MORNING = LocalTime.of(9, 0);
    private static final LocalTime LATE_EVENING = LocalTime.of(18, 0);

    public ScheduleScoreResponse score(List<TimeBlock> blocks, SchedulePreference preferences) {
        int preferencePenalty = calculatePreferencePenalty(blocks, preferences);
        int maxClassesPenalty = calculateMaxClassesPenalty(blocks, preferences);
        int compactnessScore = calculateCompactnessScore(blocks, preferences);
        int gapScore = calculateGapScore(blocks, preferences);
        int total = Math.max(0, Math.min(100, 70 + compactnessScore + gapScore - preferencePenalty - maxClassesPenalty));

        return new ScheduleScoreResponse(
                total,
                preferencePenalty,
                compactnessScore,
                gapScore,
                maxClassesPenalty
        );
    }

    private int calculatePreferencePenalty(List<TimeBlock> blocks, SchedulePreference preferences) {
        int penalty = 0;

        if (preferences.isAvoidEarlyMorning()) {
            penalty += (int) blocks.stream()
                    .filter(block -> block.getStartTime().isBefore(EARLY_MORNING))
                    .count() * 8;
        }

        if (preferences.isAvoidLateEvening()) {
            penalty += (int) blocks.stream()
                    .filter(block -> block.getEndTime().isAfter(LATE_EVENING))
                    .count() * 8;
        }

        if (preferences.isPreferNoFridayClasses()) {
            penalty += (int) blocks.stream()
                    .filter(block -> block.getDayOfWeek() == DayOfWeek.FRIDAY)
                    .count() * 6;
        }

        return penalty;
    }

    private int calculateMaxClassesPenalty(List<TimeBlock> blocks, SchedulePreference preferences) {
        Map<DayOfWeek, Long> classesByDay = blocks.stream()
                .collect(Collectors.groupingBy(TimeBlock::getDayOfWeek, Collectors.counting()));

        return classesByDay.values()
                .stream()
                .filter(count -> count > preferences.getMaxClassesPerDay())
                .mapToInt(count -> (int) (count - preferences.getMaxClassesPerDay()) * 10)
                .sum();
    }

    private int calculateCompactnessScore(List<TimeBlock> blocks, SchedulePreference preferences) {
        if (!preferences.isPreferCompactSchedule() || blocks.isEmpty()) {
            return 10;
        }

        long daysOnCampus = blocks.stream()
                .map(TimeBlock::getDayOfWeek)
                .distinct()
                .count();

        return Math.max(0, 24 - (int) daysOnCampus * 4);
    }

    private int calculateGapScore(List<TimeBlock> blocks, SchedulePreference preferences) {
        Map<DayOfWeek, List<TimeBlock>> blocksByDay = blocks.stream()
                .collect(Collectors.groupingBy(TimeBlock::getDayOfWeek));

        int score = 12;

        for (List<TimeBlock> dayBlocks : blocksByDay.values()) {
            List<TimeBlock> sortedBlocks = dayBlocks.stream()
                    .sorted(Comparator.comparing(TimeBlock::getStartTime))
                    .toList();

            for (int index = 0; index < sortedBlocks.size() - 1; index++) {
                long gap = ChronoUnit.MINUTES.between(
                        sortedBlocks.get(index).getEndTime(),
                        sortedBlocks.get(index + 1).getStartTime()
                );

                if (gap < preferences.getMinimumGapMinutes()) {
                    score -= 3;
                } else if (gap > 180) {
                    score -= 2;
                }
            }
        }

        return Math.max(0, score);
    }
}
