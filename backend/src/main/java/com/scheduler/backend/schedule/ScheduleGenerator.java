package com.scheduler.backend.schedule;

import com.scheduler.backend.availability.AvailabilityBlock;
import com.scheduler.backend.availability.AvailabilityBlockType;
import com.scheduler.backend.preference.SchedulePreference;
import com.scheduler.backend.section.Section;
import com.scheduler.backend.section.SectionMeeting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ScheduleGenerator {

    private final ScheduleConflictDetector conflictDetector;
    private final ScheduleScorer scheduleScorer;

    public ScheduleGenerator(ScheduleConflictDetector conflictDetector, ScheduleScorer scheduleScorer) {
        this.conflictDetector = conflictDetector;
        this.scheduleScorer = scheduleScorer;
    }

    public List<ScheduleOptionResponse> generate(
            List<List<Section>> sectionOptionsByCourse,
            List<AvailabilityBlock> availabilityBlocks,
            SchedulePreference preferences,
            int maxResults
    ) {
        List<List<Section>> combinations = new ArrayList<>();
        buildCombinations(sectionOptionsByCourse, 0, new ArrayList<>(), combinations);

        List<TimeBlock> hardConstraintBlocks = availabilityBlocks.stream()
                .filter(block -> block.getBlockType() == AvailabilityBlockType.WORK
                        || block.getBlockType() == AvailabilityBlockType.UNAVAILABLE)
                .map(block -> new TimeBlock(block.getDayOfWeek(), block.getStartTime(), block.getEndTime()))
                .toList();

        return combinations.stream()
                .filter(combination -> isValid(combination, hardConstraintBlocks))
                .map(combination -> toResponse(combination, preferences))
                .sorted(Comparator.comparing((ScheduleOptionResponse option) -> option.getScore().getTotal()).reversed())
                .limit(maxResults)
                .toList();
    }

    private void buildCombinations(
            List<List<Section>> sectionOptionsByCourse,
            int courseIndex,
            List<Section> currentCombination,
            List<List<Section>> combinations
    ) {
        if (courseIndex == sectionOptionsByCourse.size()) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (Section section : sectionOptionsByCourse.get(courseIndex)) {
            currentCombination.add(section);
            buildCombinations(sectionOptionsByCourse, courseIndex + 1, currentCombination, combinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private boolean isValid(List<Section> sections, List<TimeBlock> hardConstraintBlocks) {
        List<TimeBlock> scheduleBlocks = toTimeBlocks(sections);
        return !conflictDetector.hasInternalConflict(scheduleBlocks)
                && !conflictDetector.conflictsWithExternalBlocks(scheduleBlocks, hardConstraintBlocks);
    }

    private ScheduleOptionResponse toResponse(List<Section> sections, SchedulePreference preferences) {
        List<TimeBlock> scheduleBlocks = toTimeBlocks(sections);
        ScheduleScoreResponse score = scheduleScorer.score(scheduleBlocks, preferences);

        List<ScheduleSectionResponse> sectionResponses = sections.stream()
                .map(section -> new ScheduleSectionResponse(
                        section.getCourse().getId(),
                        section.getCourse().getCode(),
                        section.getCourse().getName(),
                        section.getId(),
                        section.getSectionCode(),
                        section.getInstructor(),
                        section.getLocation(),
                        section.getModality(),
                        section.getMeetings()
                                .stream()
                                .map(meeting -> new ScheduleMeetingResponse(
                                        meeting.getDayOfWeek(),
                                        meeting.getStartTime(),
                                        meeting.getEndTime()
                                ))
                                .toList()
                ))
                .toList();

        return new ScheduleOptionResponse(score, sectionResponses);
    }

    private List<TimeBlock> toTimeBlocks(List<Section> sections) {
        return sections.stream()
                .flatMap(section -> section.getMeetings().stream())
                .map(this::toTimeBlock)
                .toList();
    }

    private TimeBlock toTimeBlock(SectionMeeting meeting) {
        return new TimeBlock(meeting.getDayOfWeek(), meeting.getStartTime(), meeting.getEndTime());
    }
}
