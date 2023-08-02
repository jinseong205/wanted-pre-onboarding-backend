package com.example.server.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultDto {
	private String message;

	public ResultDto(String message) {
		this.message = message;
	}
	
	
}
