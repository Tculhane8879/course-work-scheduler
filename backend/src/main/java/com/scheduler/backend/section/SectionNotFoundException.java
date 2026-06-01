package com.scheduler.backend.section;

public class SectionNotFoundException extends RuntimeException {

    public SectionNotFoundException(Long id) {
        super("Section not found with id: " + id);
    }
}
