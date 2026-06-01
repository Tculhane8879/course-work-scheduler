package com.scheduler.backend.availability;

public class AvailabilityBlockNotFoundException extends RuntimeException {

    public AvailabilityBlockNotFoundException(Long id) {
        super("Availability block not found with id: " + id);
    }
}
