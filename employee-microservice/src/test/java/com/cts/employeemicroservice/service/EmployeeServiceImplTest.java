package com.cts.employeemicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.employeemicroservice.client.AuthClient;
import com.cts.employeemicroservice.client.OfferClient;
import com.cts.employeemicroservice.exception.InvalidUserException;
import com.cts.employeemicroservice.model.AuthResponse;
import com.cts.employeemicroservice.model.Employee;
import com.cts.employeemicroservice.model.EmployeeOffers;
import com.cts.employeemicroservice.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;
	
	@Mock
	AuthClient authClient;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock
	OfferClient offerClient;
	
	@Test
	public void viewEmpOffersTest(){
		 
		ResponseEntity<AuthResponse> authResponse = new ResponseEntity<>(new AuthResponse(1,"abc",true),HttpStatus.OK);
		List<EmployeeOffers> emp=new ArrayList<EmployeeOffers>();
		emp.add(new EmployeeOffers(1,"abc","Apartment for rent","Apartment Rental",new Date(),new Date(),new Date(),1,2,10));
		ResponseEntity<List<EmployeeOffers>> employeeList=new ResponseEntity<>(emp,HttpStatus.OK);
		when(authClient.getValidity("token")).thenReturn(authResponse);
		when(offerClient.getOffersById("token",1)).thenReturn(employeeList);
		List<EmployeeOffers> resultEmployeeList=employeeServiceImpl.viewEmpOffers("token", 1);
		assertEquals(resultEmployeeList,emp);
	
	}
	
	@Test
	public void viewEmployee(){
		 
		ResponseEntity<AuthResponse> authResponse = new ResponseEntity<>(new AuthResponse(1,"abc",true),HttpStatus.OK);
		Employee employee= new Employee(1,"abc","Full Stack","Gender",23,959983990,"abc@gmail.com",100);
		Optional<Employee> data = Optional.of(employee);
		when(authClient.getValidity("token")).thenReturn(authResponse);
		when(employeeRepository.findById(1)).thenReturn(data);
		Employee resultEmployee=employeeServiceImpl.viewEmployee("token", 1);
		assertEquals(resultEmployee,employee);
	
	}
	
	@Test
	public void viewTopOffersTest(){
		 
		ResponseEntity<AuthResponse> authResponse = new ResponseEntity<>(new AuthResponse(1,"abc",true),HttpStatus.OK);
		List<EmployeeOffers> emp=new ArrayList<EmployeeOffers>();
		emp.add(new EmployeeOffers(1,"abc","Apartment for rent","Apartment Rental",new Date(),new Date(),new Date(),1,2,10));
		ResponseEntity<List<EmployeeOffers>> employeeList=new ResponseEntity<>(emp,HttpStatus.OK);
		when(authClient.getValidity("token")).thenReturn(authResponse);
		when(offerClient.getOfferByTopLikes("token")).thenReturn(employeeList);
		List<EmployeeOffers> resultEmployeeList=employeeServiceImpl.viewTopOffers("token",1);
		assertEquals(resultEmployeeList,emp);
	}
	
	@Test
	public void invalidUserTest()
	{
		ResponseEntity<AuthResponse> auth = new ResponseEntity<>(new AuthResponse(1,"abc",false),HttpStatus.OK);
		when(authClient.getValidity("token")).thenReturn(auth);
		assertThrows(InvalidUserException.class,()->employeeServiceImpl.viewEmpOffers("token", 1));
	}
	
	@Test
	public void invalidUserEmpTest()
	{
		ResponseEntity<AuthResponse> auth = new ResponseEntity<>(new AuthResponse(1,"abc",false),HttpStatus.OK);
		when(authClient.getValidity("token")).thenReturn(auth);
		assertThrows(InvalidUserException.class,()->employeeServiceImpl.viewEmployee("token", 1));
	}
	
	@Test
	public void invalidUserOffersTest()
	{
		ResponseEntity<AuthResponse> auth = new ResponseEntity<>(new AuthResponse(1,"abc",false),HttpStatus.OK);
		when(authClient.getValidity("token")).thenReturn(auth);
		assertThrows(InvalidUserException.class,()->employeeServiceImpl.viewTopOffers("token",1));
	}
	
	@Test
	public void noSuchElementTest()
	{
		ResponseEntity<AuthResponse> auth = new ResponseEntity<>(new AuthResponse(1,"abc",true),HttpStatus.OK);
		when(authClient.getValidity("token")).thenReturn(auth);
		when(employeeRepository.findById(1)).thenReturn(null);
		assertThrows(NoSuchElementException.class,()->employeeServiceImpl.viewEmployee("token",2));
	}
}
