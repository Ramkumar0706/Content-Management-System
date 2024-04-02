package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.usermodel.ContributionPanel;
import com.example.cms.usermodel.User;

public interface ContributerRepository  extends JpaRepository<ContributionPanel, Integer>{
	boolean existsByPanelIdAndContributors(int panelId, User user);
}
