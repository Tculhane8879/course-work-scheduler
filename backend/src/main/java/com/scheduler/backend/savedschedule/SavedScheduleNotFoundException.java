package com.scheduler.backend.savedschedule;

public class SavedScheduleNotFoundException extends RuntimeException {

    public SavedScheduleNotFoundException(Long id) {
        super("Saved schedule not found with id: " + id);
    }
}
