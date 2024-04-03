package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.usermodel.Publish;

public interface PublishRepository extends JpaRepository<Publish, Integer> {

}
