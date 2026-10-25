package org.example.JobApplicationTracker.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.example.JobApplicationTracker.dto.ExceptionResponseDto;
import org.example.JobApplicationTracker.dto.ValidationExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(
			ResourceNotFoundException ex, HttpServletRequest request) {
		
		ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
				LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(), 
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI()
		);
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(exceptionResponse);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionResponseDto> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex, HttpServletRequest request) {
		
		Map<String, String> fieldErrors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors()
		                    .forEach(error ->
		                    fieldErrors.put(error.getField(), error.getDefaultMessage()));
		
		ValidationExceptionResponseDto exceptionResponse = new ValidationExceptionResponseDto(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(), 
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"Validation Failed",
				request.getRequestURI(),
				fieldErrors
		);
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(exceptionResponse);
	}

}
