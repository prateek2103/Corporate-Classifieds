package com.cts.employeemicroservice.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.employeemicroservice.client.AuthClient;
import com.cts.employeemicroservice.client.OfferClient;
import com.cts.employeemicroservice.exception.InvalidUserException;
import com.cts.employeemicroservice.exception.MicroserviceException;
import com.cts.employeemicroservice.model.AuthResponse;
import com.cts.employeemicroservice.model.Employee;
import com.cts.employeemicroservice.model.EmployeeOffers;
import com.cts.employeemicroservice.model.MessageResponse;
import com.cts.employeemicroservice.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	AuthClient authClient;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	OfferClient offerClient;
	
	@Autowired
	MessageResponse messageResponse;

	@Override
	public List<EmployeeOffers> viewEmpOffers(String token, int id) throws MicroserviceException, InvalidUserException {
		log.info("Inside view employee offers");

		AuthResponse authResponse;
		List<EmployeeOffers> empOffers;

		// validate the user
		try {
			authResponse = authClient.getValidity(token).getBody();
		} catch (Exception e) {
			throw new MicroserviceException(e.getMessage());
		}

		// if token is valid
		if (authResponse.isValid()) {
			// verify the user id with the token id
			if (authResponse.getEmpid() != id) {
				throw new InvalidUserException("invalid token for the user");
			}
			try {
				empOffers = offerClient.getOffersById(token, id);
			} catch (Exception e) {
				throw new MicroserviceException(e.getMessage());
			}
			return empOffers;
		} else {
			log.error("Token invalid");
			throw new InvalidUserException("Invalid User");
		}
	}

	// view employee details by employee id
	@Override
	public Employee viewEmployee(String token, int id) throws MicroserviceException, InvalidUserException {
		log.info("Inside view employee");
		AuthResponse authResponse;

		// authenticate the user
		try {
			authResponse = authClient.getValidity(token).getBody();
		} catch (Exception e) {
			throw new MicroserviceException(e.getMessage());
		}

		// validate the token
		if (authResponse.isValid()) {

			if (authResponse.getEmpid() != id) {
				throw new InvalidUserException("token is invalid for the given user");
			}

			// retrieve the employee details
			Employee employee = employeeRepository.findById(id).orElse(null);

			if (employee == null) {
				log.error("Invalid employee id");
				throw new NoSuchElementException();
			}
			return employee;
		} else {
			log.error("Token invalid");
			throw new InvalidUserException("Invalid User");
		}
	}

	// view top 3 offers of the employee
	@Override
	public List<EmployeeOffers> viewTopOffers(String token, int employeeId) throws MicroserviceException,InvalidUserException{
		log.info("Inside view top offers");
		AuthResponse authResponse ;
		//validate the user
		try {
			authResponse = authClient.getValidity(token).getBody();
		} catch (Exception e) {
			throw new MicroserviceException(e.getMessage());
		}
		
		//if the token is valid
		if (authResponse.isValid()) {
			List<EmployeeOffers> empOffers;
			try {
				empOffers = offerClient.getOffersById(token, employeeId);
			}catch(Exception e) {
				throw new MicroserviceException(e.getMessage());
			}
			
			//retrieve the top 3 offers
			List<EmployeeOffers> empList = empOffers.stream()
					.sorted(Comparator.comparing(EmployeeOffers::getLikes).reversed()).limit(3)
					.collect(Collectors.toList());
			
			return empList;
		} 
		else {
			log.error("Token invalid");
			throw new InvalidUserException("Invalid User");
		}
	}

	@Override
	public MessageResponse savePoints(String token, int points) throws MicroserviceException,InvalidUserException {
		log.info("Inside save points");
		AuthResponse authResponse ;
		//validate the user
		try {
			authResponse = authClient.getValidity(token).getBody();
		} catch (Exception e) {
			throw new MicroserviceException(e.getMessage());
		}
		
		//if the token is valid
		if (authResponse.isValid()) {
			Employee emp = employeeRepository.findById(authResponse.getEmpid()).orElse(null);
			emp.setPointsGained(points);
			employeeRepository.save(emp);
			messageResponse.setMessage("points updated successfully");
			messageResponse.setStatus(HttpStatus.OK);
			messageResponse.setTimeStamp(new Date());
			return messageResponse;
		}
		else {
			throw new InvalidUserException("invalid user");
		}
	}
}