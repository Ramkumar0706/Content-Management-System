package com.example.cms.response.dto;

import java.util.List;

import com.example.cms.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ContributerPanelResponse {
	private User owner;
	private List<User> users;
}
