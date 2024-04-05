package com.example.cms.userrepository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.usermodel.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	LocalDateTime findByDateTimeLessThanEqual(LocalDateTime now);

}
