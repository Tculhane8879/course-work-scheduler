package com.scheduler.backend.schedule;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/generate")
    public List<ScheduleOptionResponse> generateSchedules(
            @Valid @RequestBody ScheduleGenerationRequest request
    ) {
        return scheduleService.generateSchedules(request);
    }
}
