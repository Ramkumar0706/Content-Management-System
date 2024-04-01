package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PanelNotFoundException  extends RuntimeException{
private String message;
}
