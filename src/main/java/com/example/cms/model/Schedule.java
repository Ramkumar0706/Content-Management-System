package com.example.cms.model;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int scheduleId;
	
	
	private LocalDateTime dateTime;

}
