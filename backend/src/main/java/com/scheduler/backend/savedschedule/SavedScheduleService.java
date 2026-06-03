package com.scheduler.backend.savedschedule;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SavedScheduleService {

    private final SavedScheduleRepository savedScheduleRepository;

    public SavedScheduleService(SavedScheduleRepository savedScheduleRepository) {
        this.savedScheduleRepository = savedScheduleRepository;
    }

    public List<SavedScheduleResponse> getAllSavedSchedules() {
        return savedScheduleRepository.findAllByOrderByPreferredDescCreatedAtDesc()
                .stream()
                .map(SavedScheduleResponse::fromEntity)
                .toList();
    }

    @Transactional
    public SavedScheduleResponse createSavedSchedule(SavedScheduleRequest request) {
        SavedSchedule savedSchedule = new SavedSchedule(
                request.getName().trim(),
                request.getScore().getTotal(),
                request.getScore().getPreferencePenalty(),
                request.getScore().getCompactnessScore(),
                request.getScore().getGapScore(),
                request.getScore().getMaxClassesPenalty()
        );

        request.getSections().forEach(sectionRequest -> {
            SavedScheduleSection section = new SavedScheduleSection(
                    sectionRequest.getCourseId(),
                    sectionRequest.getCourseCode().trim(),
                    sectionRequest.getCourseName().trim(),
                    sectionRequest.getSectionId(),
                    sectionRequest.getSectionCode().trim(),
                    trimToNull(sectionRequest.getInstructor()),
                    trimToNull(sectionRequest.getLocation()),
                    sectionRequest.getModality()
            );

            sectionRequest.getMeetings().forEach(meetingRequest -> section.addMeeting(
                    new SavedScheduleMeeting(
                            meetingRequest.getDayOfWeek(),
                            meetingRequest.getStartTime(),
                            meetingRequest.getEndTime()
                    )
            ));

            savedSchedule.addSection(section);
        });

        return SavedScheduleResponse.fromEntity(savedScheduleRepository.save(savedSchedule));
    }

    public SavedScheduleResponse getSavedScheduleById(Long id) {
        SavedSchedule savedSchedule = savedScheduleRepository.findById(id)
                .orElseThrow(() -> new SavedScheduleNotFoundException(id));

        return SavedScheduleResponse.fromEntity(savedSchedule);
    }

    @Transactional
    public SavedScheduleResponse markPreferred(Long id) {
        SavedSchedule selectedSchedule = savedScheduleRepository.findById(id)
                .orElseThrow(() -> new SavedScheduleNotFoundException(id));

        savedScheduleRepository.findAll().forEach(savedSchedule -> {
            savedSchedule.setPreferred(savedSchedule.getId().equals(selectedSchedule.getId()));
        });

        return SavedScheduleResponse.fromEntity(selectedSchedule);
    }

    @Transactional
    public void deleteSavedSchedule(Long id) {
        if (!savedScheduleRepository.existsById(id)) {
            throw new SavedScheduleNotFoundException(id);
        }

        savedScheduleRepository.deleteById(id);
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}
