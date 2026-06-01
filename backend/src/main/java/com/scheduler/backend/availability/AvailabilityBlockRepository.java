package com.scheduler.backend.availability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityBlockRepository extends JpaRepository<AvailabilityBlock, Long> {

    List<AvailabilityBlock> findAllByOrderByDayOfWeekAscStartTimeAsc();
}
