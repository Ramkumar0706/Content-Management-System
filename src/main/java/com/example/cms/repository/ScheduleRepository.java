package com.example.cms.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	LocalDateTime findByDateTimeLessThanEqual(LocalDateTime now);

}
