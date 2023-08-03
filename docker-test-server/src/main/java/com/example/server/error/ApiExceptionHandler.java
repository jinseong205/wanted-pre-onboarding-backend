package com.example.server.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.server.dto.ResultDto;

import lombok.extern.slf4j.Slf4j;

// Target all Controllers annotated with @RestController
@ControllerAdvice(annotations = RestController.class)
@RestController
@Slf4j
public class ApiExceptionHandler {

	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ResponseEntity<?> commonExceptionHandler(Exception e) {

		// log.debug("[ERROR]", e);

		String errorMessage;

		if (e instanceof MissingServletRequestPartException) {
			errorMessage = "입력정보가 누락되었습니다.\n" + e.getMessage();
			return new ResponseEntity<>(new ResultDto(errorMessage), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new ResultDto(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
}
