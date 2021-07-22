package com.spring.mfpe.offer.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Offer")
public class Offer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	//name of the offer
	private String name;
	
	//description of the offer
	private String description;
	
	//category of the offer
	private String category;

	// opening/creation date of the offer
	@Column(name="open_date")
	private Date openDate;
	
	// engaged date ( when a buyer shows interest)
	@Column(name="engaged_date")
	private Date engagedDate;
	
	//closing date(when the buyer buys)
	@Column(name="closed_date")
	private Date closedDate;
	
	//employee who created the Offer
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private Employee emp;
	
	//employee which showed interest in the offer
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="engaged_emp_id")
	private Employee engagedEmp;
	
	//many employees can like many offers
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinTable(name="liked_by", 
		joinColumns = {
				@JoinColumn(name="offer_id",referencedColumnName="id")
		},
		inverseJoinColumns = {
				@JoinColumn(name="emp_id",referencedColumnName="id")
		})
	private Set<Employee> likedByEmployees = new HashSet<>();
	
	//no of likes on an offer
	private int likes;

	//getters and setters
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getEngagedDate() {
		return engagedDate;
	}

	public void setEngagedDate(Date engagedDate) {
		this.engagedDate = engagedDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Employee getEngagedEmp() {
		return engagedEmp;
	}

	public void setEngagedEmp(Employee engagedEmp) {
		this.engagedEmp = engagedEmp;
	}

	public Set<Employee> getLikedByEmployees() {
		return likedByEmployees;
	}

	public void setLikedByEmployees(Set<Employee> likedByEmployees) {
		this.likedByEmployees = likedByEmployees;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", openDate=" + openDate + ", engagedDate=" + engagedDate + ", closedDate=" + closedDate + ", likes="
				+ likes + "]";
	}
	
	
}
