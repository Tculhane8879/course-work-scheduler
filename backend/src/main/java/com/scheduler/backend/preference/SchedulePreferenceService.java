package com.scheduler.backend.preference;

import org.springframework.stereotype.Service;

@Service
public class SchedulePreferenceService {

    private final SchedulePreferenceRepository schedulePreferenceRepository;

    public SchedulePreferenceService(SchedulePreferenceRepository schedulePreferenceRepository) {
        this.schedulePreferenceRepository = schedulePreferenceRepository;
    }

    public SchedulePreferenceResponse getPreferences() {
        SchedulePreference preference = schedulePreferenceRepository
                .findById(SchedulePreference.SINGLETON_ID)
                .orElseGet(SchedulePreference::defaults);

        return SchedulePreferenceResponse.fromEntity(preference);
    }

    public SchedulePreferenceResponse updatePreferences(SchedulePreferenceRequest request) {
        SchedulePreference preference = schedulePreferenceRepository
                .findById(SchedulePreference.SINGLETON_ID)
                .orElseGet(SchedulePreference::defaults);

        preference.apply(
                request.isAvoidEarlyMorning(),
                request.isAvoidLateEvening(),
                request.isPreferCompactSchedule(),
                request.isPreferNoFridayClasses(),
                request.getMaxClassesPerDay(),
                request.getMinimumGapMinutes()
        );

        SchedulePreference savedPreference = schedulePreferenceRepository.save(preference);
        return SchedulePreferenceResponse.fromEntity(savedPreference);
    }
}
