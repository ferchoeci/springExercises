package com.tigoune.rest.webservices.resfulwebservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) 
public class EmptyUsersException extends RuntimeException {

	public EmptyUsersException(String message) {
		super(message);
	}

	
}
