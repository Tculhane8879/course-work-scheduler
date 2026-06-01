package com.scheduler.backend.preference;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class SchedulePreferenceController {

    private final SchedulePreferenceService schedulePreferenceService;

    public SchedulePreferenceController(SchedulePreferenceService schedulePreferenceService) {
        this.schedulePreferenceService = schedulePreferenceService;
    }

    @GetMapping
    public SchedulePreferenceResponse getPreferences() {
        return schedulePreferenceService.getPreferences();
    }

    @PutMapping
    public SchedulePreferenceResponse updatePreferences(
            @Valid @RequestBody SchedulePreferenceRequest request
    ) {
        return schedulePreferenceService.updatePreferences(request);
    }
}
