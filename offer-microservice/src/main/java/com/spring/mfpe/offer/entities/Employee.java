package com.spring.mfpe.offer.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// name of the employee
	private String name;

	// department of the employee
	private String department;

	// gender
	private String gender;

	// age
	private int age;

	// contact info
	@Column(name="contact_number")
	private Long contactNumber;

	// email id
	private String email;

	// points gained by the employee
	@Column(name="points_gained")
	private int pointsGained;

	// one employee can have many offers
	@OneToMany(mappedBy = "emp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Offer> offers;

	// one employee can be engaged in many offers
	@OneToMany(mappedBy = "engagedEmp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Offer> engagedInOffers;

	// many employees can like many offers
	@ManyToMany(mappedBy = "likedByEmployees", fetch = FetchType.LAZY)
	private Set<Offer> likedOffers = new HashSet<>();

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPointsGained() {
		return pointsGained;
	}

	public void setPointsGained(int pointsGained) {
		this.pointsGained = pointsGained;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Set<Offer> getEngagedInOffers() {
		return engagedInOffers;
	}

	public void setEngagedInOffers(Set<Offer> engagedInOffers) {
		this.engagedInOffers = engagedInOffers;
	}

	public Set<Offer> getLikedOffers() {
		return likedOffers;
	}

	public void setLikedOffers(Set<Offer> likedOffers) {
		this.likedOffers = likedOffers;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", gender=" + gender + ", age="
				+ age + ", contactNumber=" + contactNumber + ", email=" + email + ", pointsGained=" + pointsGained+"]";
	}

}
