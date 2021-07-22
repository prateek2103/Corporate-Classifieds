package com.spring.mfpe.offer.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.mfpe.offer.entities.Employee;
import com.spring.mfpe.offer.entities.Offer;
import com.spring.mfpe.offer.exceptions.EmployeeNotFoundException;
import com.spring.mfpe.offer.exceptions.ImproperDateException;
import com.spring.mfpe.offer.exceptions.OfferAlreadyEngagedException;
import com.spring.mfpe.offer.exceptions.OfferNotFoundException;
import com.spring.mfpe.offer.model.SuccessResponse;
import com.spring.mfpe.offer.repositories.EmployeeRepository;
import com.spring.mfpe.offer.repositories.OfferRepository;

@Service
public class OfferService {

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SuccessResponse successResponse;

	/**
	 * service that returns offer details for a specific offerId
	 * 
	 * @param offerId
	 * @return
	 * @throws OfferNotFoundException
	 */
	public Offer getOfferDetails(int offerId) throws OfferNotFoundException {
		Optional<Offer> offer = offerRepository.findById(offerId);
		if (!offer.isPresent())
			throw new OfferNotFoundException("No offer found");

		return offer.get();
	}

	/**
	 * service to filter offers by category
	 * 
	 * @param category
	 * @return
	 * @throws OfferNotFoundException
	 */
	public List<Offer> getOfferByCategory(String category) throws OfferNotFoundException {
		List<Offer> offers = offerRepository.findByCategory(category);
		if (offers.size() == 0) {
			throw new OfferNotFoundException("no offers found");
		}
		return offers;
	}

	/**
	 * returns top 3 liked offers
	 * 
	 * @return
	 * @throws OfferNotFoundException
	 */
	public List<Offer> getOfferByTopLikes() throws OfferNotFoundException {
		List<Offer> offers = offerRepository.findAll(PageRequest.of(0, 3, Sort.by("likes").descending()));

		if (offers.size() == 0) {
			throw new OfferNotFoundException("no offers found");
		}

		return offers;
	}

	/**
	 * filter offers by postedDate
	 * 
	 * @param date
	 * @return
	 * @throws OfferNotFoundException
	 * @throws ImproperDateException
	 */
	public List<Offer> getOfferByPostedDate(String date) throws OfferNotFoundException, ImproperDateException {
		LocalDate currentDate = null;

		// if the user passes improper date
		try {
			currentDate = LocalDate.parse(date);
		} catch (Exception e) {
			throw new ImproperDateException("enter a valid date");
		}

		int month = currentDate.getMonthValue();
		int year = currentDate.getYear();
		int day = currentDate.getDayOfMonth();

		List<Offer> offers = offerRepository.getByPostedDate(month, year, day);

		if (offers.size() == 0)
			throw new OfferNotFoundException("no offers found");

		return offers;
	}

	/**
	 * service to initiate an engagement between the employee and the buyer
	 * 
	 * @param offerId
	 * @param employeeId
	 * @return
	 * @throws OfferNotFoundException
	 * @throws OfferAlreadyEngagedException
	 * @throws EmployeeNotFoundException
	 */
	public SuccessResponse engageOffer(int offerId, int employeeId)
			throws OfferNotFoundException, OfferAlreadyEngagedException, EmployeeNotFoundException {

		// check if the employeeId matches with the logged user

		// check if the offer is present or not
		Optional<Offer> offer = offerRepository.findById(offerId);
		if (!offer.isPresent()) {
			throw new OfferNotFoundException("offer not found");
		}

		// if already closed
		if (offer.get().getClosedDate() != null) {
			successResponse.setMessage("offer is already closed");
			successResponse.setStatus(HttpStatus.BAD_REQUEST);
			successResponse.setTimestamp(new Date());
			return successResponse;
		}

		// if offer is present then check the initial stage of the offer
		else if (offer.get().getEngagedDate() != null) {
			throw new OfferAlreadyEngagedException("offer cannot be engaged");
		}

		// if the offer is available
		else {
			Optional<Employee> employee = employeeRepository.findById(employeeId);

			// if employee is not present
			if (!employee.isPresent()) {
				throw new EmployeeNotFoundException("employee not found");
			}
			
			// check if offer belongs to the same employee
			if (offer.get().getEmp().getId() == employeeId) {
				successResponse.setMessage("Employee cannot be engaged with his own offer");
				successResponse.setStatus(HttpStatus.BAD_REQUEST);
				successResponse.setTimestamp(new Date());
				return successResponse;
			}

			// else set the engaged employee id and the engagedDate
			Offer offer_real = offer.get();
			offer_real.setEngagedEmp(employee.get());
			offer_real.setEngagedDate(new Date());
			offerRepository.save(offer_real);

			// set the response
			successResponse.setMessage("engaged in the offer successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			successResponse.setTimestamp(new Date());

			return successResponse;
		}
	}

	/**
	 * service to update an existing offer
	 * @param offerDetails
	 * @return
	 * @throws OfferNotFoundException
	 */
	public SuccessResponse editOffer(Offer offerDetails) throws OfferNotFoundException {
		
		//get the offer from the repository
		Optional<Offer> offer = offerRepository.findById(offerDetails.getId());
		
		//if the offer is not found
		if(!offer.isPresent()) {
			throw new OfferNotFoundException("no offer found");
		}
		
		//update the new details
		Offer offerReal = offer.get();
		offerReal.setCategory(offerDetails.getCategory());
		offerReal.setDescription(offerDetails.getDescription());
		offerReal.setName(offerDetails.getName());
		
		//save the details to offer repository
		offerRepository.save(offerReal);
		
		//prepare the response
		successResponse.setMessage("offer updated successfully");
		successResponse.setStatus(HttpStatus.OK);
		successResponse.setTimestamp(new Date());
		
		return successResponse;
	}

	/**
	 * service to add a new offer
	 * @param offer
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	public SuccessResponse addOffer(Offer offer) throws EmployeeNotFoundException {
		//get the employee details from the auth token
		int empId = 10;  //demo
		
		Optional<Employee> emp = employeeRepository.findById(empId);
		
		if(!emp.isPresent()) {
			throw new EmployeeNotFoundException("employee not found");
		}
		
		offer.setEmp(emp.get());
		offer.setOpenDate(new Date());
		
		offerRepository.save(offer);
		successResponse.setMessage("successfully added offer");
		successResponse.setStatus(HttpStatus.CREATED);
		successResponse.setTimestamp(new Date());
		
		return successResponse;
	}
}
