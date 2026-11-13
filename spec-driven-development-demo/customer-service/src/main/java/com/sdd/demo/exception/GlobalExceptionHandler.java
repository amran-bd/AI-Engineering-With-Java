package com.sdd.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	ResponseEntity<ErrorResponse> handleNotFound(CustomerNotFoundException exception, HttpServletRequest request) {
		return error(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", exception.getMessage(), request);
	}

	@ExceptionHandler(CustomerEmailAlreadyExistsException.class)
	ResponseEntity<ErrorResponse> handleDuplicateEmail(CustomerEmailAlreadyExistsException exception,
			HttpServletRequest request) {
		return error(HttpStatus.CONFLICT, "CUSTOMER_EMAIL_ALREADY_EXISTS", exception.getMessage(), request);
	}

	@ExceptionHandler(InvalidCustomerStatusException.class)
	ResponseEntity<ErrorResponse> handleInvalidStatus(InvalidCustomerStatusException exception,
			HttpServletRequest request) {
		return error(HttpStatus.BAD_REQUEST, "INVALID_CUSTOMER_STATUS", exception.getMessage(), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		String message = exception.getBindingResult().getFieldErrors().stream().map(this::formatFieldError)
				.collect(Collectors.joining("; "));
		return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message, request);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException exception,
			HttpServletRequest request) {
		String message = "Request body contains invalid or missing values";
		String details = exception.getMostSpecificCause().getMessage();
		if (details != null && details.contains("CustomerStatus")) {
			message = "Status must be ACTIVE or INACTIVE";
			return error(HttpStatus.BAD_REQUEST, "INVALID_CUSTOMER_STATUS", message, request);
		}
		return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	ResponseEntity<ErrorResponse> handleConstraint(DataIntegrityViolationException exception,
			HttpServletRequest request) {
		return error(HttpStatus.CONFLICT, "CUSTOMER_EMAIL_ALREADY_EXISTS", "Customer email already exists", request);
	}

	private String formatFieldError(FieldError fieldError) {
		return fieldError.getField() + " " + fieldError.getDefaultMessage();
	}

	private ResponseEntity<ErrorResponse> error(HttpStatus status, String code, String message,
			HttpServletRequest request) {
		ErrorResponse body = new ErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), code, message,
				request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}
}
