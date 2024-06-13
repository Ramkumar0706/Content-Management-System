package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

public interface ContributerRepository  extends JpaRepository<ContributionPanel, Integer>{
	
	
	
	boolean existsByPanelIdAndContributers(int panelId, User user);
}
