package com.cts.employeemicroservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.employeemicroservice.model.EmployeeOffers;

//connect to the offer microservice
@FeignClient(url = "${offer.feign.client}", name = "${offer.feign.name}")
public interface OfferClient {

	@GetMapping("/getOffers/{emp_id}")
	public List<EmployeeOffers> getOffersById(@RequestHeader(name = "Authorization", required = true) String token, @PathVariable("emp_id") int id);
}