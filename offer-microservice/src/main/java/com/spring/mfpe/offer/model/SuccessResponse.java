package com.spring.mfpe.offer.model;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SuccessResponse {
	private String message;
	private HttpStatus status;
	private Date timestamp;

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
