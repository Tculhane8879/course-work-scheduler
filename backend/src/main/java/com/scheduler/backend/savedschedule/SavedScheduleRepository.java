package com.scheduler.backend.savedschedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedScheduleRepository extends JpaRepository<SavedSchedule, Long> {

    List<SavedSchedule> findAllByOrderByPreferredDescCreatedAtDesc();
}
