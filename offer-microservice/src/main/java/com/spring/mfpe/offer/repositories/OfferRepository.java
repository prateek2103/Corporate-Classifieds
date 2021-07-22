package com.spring.mfpe.offer.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.mfpe.offer.entities.Offer;

public interface OfferRepository extends CrudRepository<Offer, Integer> {
	//filter by category
	public List<Offer> findByCategory(String category);

	//sort by likes and return top 3
	public List<Offer> findAll(Pageable pageable);
	
	//filter by posted date
	@Query("from Offer where month(open_date)=?1 and year(open_date)=?2 and day(open_date)=?3")
	public List<Offer> getByPostedDate(int month , int year, int day);
	
}
