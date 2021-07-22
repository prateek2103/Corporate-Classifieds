package com.spring.mfpe.offer.exceptions;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.mfpe.offer.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	ErrorResponse errorResponse;

	// to handle offer not found exception
	@ExceptionHandler(OfferNotFoundException.class)
	public ResponseEntity<ErrorResponse> offerNotFound(OfferNotFoundException ex) {
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND);
		errorResponse.setTimestamp(new Date());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// to handle improper date format ( check getOfferByPostedDate)
	@ExceptionHandler(ImproperDateException.class)
	public ResponseEntity<ErrorResponse> improperDate(ImproperDateException ex) {
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		errorResponse.setTimestamp(new Date());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// to handle employee not found
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> employeeNotFound(EmployeeNotFoundException ex) {
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.NOT_FOUND);
		errorResponse.setTimestamp(new Date());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// to handle offer already engaged
	@ExceptionHandler(OfferAlreadyEngagedException.class)
	public ResponseEntity<ErrorResponse> offerEngaged(OfferAlreadyEngagedException ex) {
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		errorResponse.setTimestamp(new Date());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
