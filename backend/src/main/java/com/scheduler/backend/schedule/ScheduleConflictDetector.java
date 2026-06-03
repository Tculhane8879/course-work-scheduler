package com.scheduler.backend.schedule;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleConflictDetector {

    public boolean overlaps(TimeBlock first, TimeBlock second) {
        return first.getDayOfWeek() == second.getDayOfWeek()
                && first.getStartTime().isBefore(second.getEndTime())
                && first.getEndTime().isAfter(second.getStartTime());
    }

    public boolean hasInternalConflict(List<TimeBlock> blocks) {
        for (int firstIndex = 0; firstIndex < blocks.size(); firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < blocks.size(); secondIndex++) {
                if (overlaps(blocks.get(firstIndex), blocks.get(secondIndex))) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean conflictsWithExternalBlocks(List<TimeBlock> scheduleBlocks, List<TimeBlock> externalBlocks) {
        return scheduleBlocks.stream()
                .anyMatch(scheduleBlock -> externalBlocks.stream()
                        .anyMatch(externalBlock -> overlaps(scheduleBlock, externalBlock)));
    }
}
