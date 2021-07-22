package com.spring.mfpe.offer.model;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//model for response entity ( check global exception handler)
@Component
public class ErrorResponse {
	private String message;
	private HttpStatus status;
	private Date timestamp;

	// default constructor
	public ErrorResponse() {
	}

	// getters and setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
