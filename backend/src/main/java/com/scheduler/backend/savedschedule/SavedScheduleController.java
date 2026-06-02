package com.scheduler.backend.savedschedule;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-schedules")
public class SavedScheduleController {

    private final SavedScheduleService savedScheduleService;

    public SavedScheduleController(SavedScheduleService savedScheduleService) {
        this.savedScheduleService = savedScheduleService;
    }

    @GetMapping
    public List<SavedScheduleResponse> getAllSavedSchedules() {
        return savedScheduleService.getAllSavedSchedules();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SavedScheduleResponse createSavedSchedule(@Valid @RequestBody SavedScheduleRequest request) {
        return savedScheduleService.createSavedSchedule(request);
    }

    @GetMapping("/{id}")
    public SavedScheduleResponse getSavedScheduleById(@PathVariable Long id) {
        return savedScheduleService.getSavedScheduleById(id);
    }

    @PutMapping("/{id}/preferred")
    public SavedScheduleResponse markPreferred(@PathVariable Long id) {
        return savedScheduleService.markPreferred(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSavedSchedule(@PathVariable Long id) {
        savedScheduleService.deleteSavedSchedule(id);
    }
}
