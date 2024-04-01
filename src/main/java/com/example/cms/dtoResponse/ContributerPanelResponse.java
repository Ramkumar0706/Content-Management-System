package com.example.cms.dtoResponse;

import java.util.List;

import com.example.cms.usermodel.User;

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
