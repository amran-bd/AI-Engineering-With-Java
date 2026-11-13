package com.sdd.demo.exception;

public class CustomerEmailAlreadyExistsException extends RuntimeException {

	public CustomerEmailAlreadyExistsException(String message) {
		super(message);
	}
}
