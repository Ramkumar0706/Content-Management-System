package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IllegalAccessRequestException extends RuntimeException {
private String message;
}
