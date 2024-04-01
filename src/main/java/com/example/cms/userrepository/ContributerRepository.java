package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.usermodel.ContributionPanel;

public interface ContributerRepository  extends JpaRepository<ContributionPanel, Integer>{

}
