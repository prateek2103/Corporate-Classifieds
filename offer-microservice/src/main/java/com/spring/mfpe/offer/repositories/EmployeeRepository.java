package com.spring.mfpe.offer.repositories;

import org.springframework.data.repository.CrudRepository;

import com.spring.mfpe.offer.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
