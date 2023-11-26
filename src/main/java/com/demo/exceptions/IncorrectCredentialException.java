package com.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IncorrectCredentialException extends Exception{
	
	public IncorrectCredentialException(String message) {
		super(message);
	}

}
