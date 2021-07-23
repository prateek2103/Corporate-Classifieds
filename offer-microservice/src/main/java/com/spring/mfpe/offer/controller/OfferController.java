package com.spring.mfpe.offer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mfpe.offer.entities.Offer;
import com.spring.mfpe.offer.exceptions.EmployeeNotFoundException;
import com.spring.mfpe.offer.exceptions.ImproperDateException;
import com.spring.mfpe.offer.exceptions.OfferAlreadyEngagedException;
import com.spring.mfpe.offer.exceptions.OfferNotFoundException;
import com.spring.mfpe.offer.model.SuccessResponse;
import com.spring.mfpe.offer.services.OfferService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	OfferService offerService;
	
	/**
	 * 
	 * @param offerId
	 * @return
	 * @throws OfferNotFoundException
	 * returns offer details for a specific offer id
	 */
	@GetMapping("/getOfferDetails/{offerId}")
	public Offer getOfferDetails(@PathVariable("offerId") int offerId) throws OfferNotFoundException {
		
		//authenticate the user
		
		log.debug("inside getOfferDetails method of offer microservice");
		//after authentication return the offer details
		Offer offer = offerService.getOfferDetails(offerId);
		
		return offer;
	}
	
	/**
	 * returns offer filtered by category
	 * @param category
	 * @return
	 * @throws OfferNotFoundException 
	 */
	@GetMapping("/getOfferByCategory/{category}")
	public List<Offer> getOfferByCategory(@PathVariable("category") String category) throws OfferNotFoundException{
		log.debug("inside getOfferByCategory method of offer microservice");
		List<Offer> offers = offerService.getOfferByCategory(category);		
		return offers;
	}
	
	/**
	 * returns top 3 offers filtered by likes
	 * @return
	 * @throws OfferNotFoundException
	 */
	@GetMapping("/getOfferByTopLikes")
	public List<Offer> getOfferByTopLikes() throws OfferNotFoundException{
		log.debug("inside getOFferByTopLikes method of offer microservice");
		List<Offer> offers = offerService.getOfferByTopLikes();
		
		return offers;
	}
	
	/**
	 * return offers filtered by posted date
	 * @param postedDate
	 * @return
	 * @throws OfferNotFoundException
	 * @throws ImproperDateException 
	 */
	@GetMapping("/getOfferByPostedDate/{date}")
	public List<Offer> getOfferByPostedDate(@PathVariable("date") String postedDate) throws OfferNotFoundException, ImproperDateException{
		log.debug("inside getOfferByPostedDate method of offer microservice");
		List<Offer> offers = offerService.getOfferByPostedDate(postedDate);
		
		return offers;
	}
	
	/**
	 * engage a buyer with the offer
	 * @param offerId
	 * @param employeeId
	 * @return
	 * @throws OfferNotFoundException
	 * @throws OfferAlreadyEngagedException
	 * @throws EmployeeNotFoundException
	 */
	@PostMapping("/engageOffer")
	public SuccessResponse engageOffer(@RequestParam(name="offerId")int offerId, @RequestParam(name="employeeId") int employeeId) throws OfferNotFoundException, OfferAlreadyEngagedException, EmployeeNotFoundException {
		log.debug("inside engage offer method of offer microservice");
		SuccessResponse status = offerService.engageOffer(offerId,employeeId);
		
		return status;
	}
	
	/**
	 * update the existing offer
	 * @param offer
	 * @return
	 * @throws OfferNotFoundException
	 */
	@PostMapping("/editOffer")
	public SuccessResponse editOffer(@RequestBody Offer offer) throws OfferNotFoundException {
		log.debug("inside editOffer method of offer microservice");
		SuccessResponse status = offerService.editOffer(offer);
		
		return status;
	}
	
	/**
	 * add a new offer
	 * @param offer
	 * @return
	 * @throws EmployeeNotFoundException 
	 */
	@PostMapping("/addOffer")
	public SuccessResponse addOffer(@RequestBody Offer offer) throws EmployeeNotFoundException {
		log.debug("inside addOffer method of offer microservice");
		SuccessResponse status = offerService.addOffer(offer);
		
		return status;
	}
	
	/**
	 * returns the list of offers for a particular employee id
	 * @param emp_id
	 * @return
	 * @throws OfferNotFoundException 
	 */
	@GetMapping("/getOffers/{emp_id}")
	public List<Offer> getOffersById(@PathVariable("emp_id") int emp_id) throws OfferNotFoundException{
		log.debug("inside getOffersById method of offer microservice");
		List<Offer> offers = offerService.getOffersById(emp_id);
		
		return offers;
	}
}
