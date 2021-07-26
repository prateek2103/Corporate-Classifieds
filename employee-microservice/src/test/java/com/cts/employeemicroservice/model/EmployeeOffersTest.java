package com.cts.employeemicroservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class EmployeeOffersTest {

	EmployeeOffers offers=new EmployeeOffers();
	EmployeeOffers empOffers=new EmployeeOffers(1,"abc","Apartment for rent","Apartment Rental",new Date(),new Date(),new Date(),1,2,10);
	
	@Test
	void testOfferId()
	{
		offers.setId(1);
		assertEquals(offers.getId(),1);
	}
	
	@Test
	void testOfferName()
	{
		offers.setName("abc");
		assertEquals(offers.getName(),"abc");
	}
	
	@Test
	void testOfferDescription()
	{
		offers.setDescription("Apartment for rent");
		assertEquals(offers.getDescription(),"Apartment for rent");
	}
	
	@Test
	void testOfferEmpId()
	{
		offers.setEmpId(1);
		assertEquals(offers.getEmpId(),"1");
	}
	
	@Test
	void testOfferEngagedEmpId()
	{
		offers.setEngagedEmpId(2);
		assertEquals(offers.getEngagedEmpId(),"2");
	}
	
	@Test
	void testOpenedDate()
	{
		offers.setOpenDate(new Date());
		assertEquals(offers.getOpenDate(),new Date());
	}
	
	@Test
	void testEngagedDate()
	{
		offers.setEngagedDate(new Date());
		assertEquals(offers.getOpenDate(),new Date());
	}
	
	@Test
	void testClosedDate()
	{
		offers.setClosedDate(new Date());
		assertEquals(offers.getClosedDate(),new Date());
	}
	
	@Test
	void testLikes()
	{
		offers.setLikes(10);
		assertEquals(offers.getLikes(),10);
	}
	
	@Test
	void testOfferCategory()
	{
		offers.setCategory("Apartment Rental");
		assertEquals(offers.getCategory(),"Apartment Rental");
	}
	
	@Test
	void testToString() {
		String string = empOffers.toString();
		assertEquals(empOffers.toString(), string);
	}
}
