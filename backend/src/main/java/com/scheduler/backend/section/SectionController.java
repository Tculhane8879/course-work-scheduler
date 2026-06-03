package com.scheduler.backend.section;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/api/courses/{courseId}/sections")
    public List<SectionResponse> getSectionsForCourse(@PathVariable Long courseId) {
        return sectionService.getSectionsForCourse(courseId);
    }

    @PostMapping("/api/courses/{courseId}/sections")
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponse createSection(
            @PathVariable Long courseId,
            @Valid @RequestBody SectionRequest request
    ) {
        return sectionService.createSection(courseId, request);
    }

    @GetMapping("/api/sections/{sectionId}")
    public SectionResponse getSectionById(@PathVariable Long sectionId) {
        return sectionService.getSectionById(sectionId);
    }

    @PutMapping("/api/sections/{sectionId}")
    public SectionResponse updateSection(
            @PathVariable Long sectionId,
            @Valid @RequestBody SectionRequest request
    ) {
        return sectionService.updateSection(sectionId, request);
    }

    @DeleteMapping("/api/sections/{sectionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSection(@PathVariable Long sectionId) {
        sectionService.deleteSection(sectionId);
    }
}
