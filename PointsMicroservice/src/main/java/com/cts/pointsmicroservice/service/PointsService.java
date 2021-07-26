package com.cts.pointsmicroservice.service;

import com.cts.pointsmicroservice.exception.InvalidUserException;
import com.cts.pointsmicroservice.exception.MicroserviceException;
import com.cts.pointsmicroservice.model.MessageResponse;

public interface PointsService {

	/**Checks the validity of the token.If not valid,it throws InvalidUserException.
	 * If the token is valid,it gets the offer list of that particular employee
	 * through the offer client and returns it.
	 * 
	 * @param token
	 * @param employeeId
	 * @return Points
	 * @throws InvalidUserException
	 */
	public Integer getPoints(String token, int id) throws MicroserviceException;

	/**Checks the validity of the token.If not valid,it throws InvalidUserException.
	 * If the token is valid,it gets the offer list of that particular employee
	 * through the offer client and returns it.
	 * 
	 * if the employee posted an offer and it has 50 likes within 2 days that employee get 10 points
	 * if the employee posted an offer and it has 100 likes within 2 days that employee get 50 points
	 * 
	 * @param token
	 * @param employeeId
	 * @return Points
	 * @throws MicroserviceException 
	 * @throws InvalidUserException
	 */
	public MessageResponse refreshPoints(String token, int id) throws MicroserviceException, InvalidUserException;

}
