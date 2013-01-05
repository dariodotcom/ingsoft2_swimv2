package it.polimi.swim.business.entity;

import it.polimi.swim.business.bean.Helpers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "emailvalidationrequest")
public class EmailValidationRequest {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name="reqKey", unique=true)
	private String reqKey;
	
	@OneToOne
	@JoinColumn(name = "author", unique = true)
	private Customer author;

	// Constructors
	public EmailValidationRequest() {

	}

	public EmailValidationRequest(Customer author) {
		this.reqKey = Helpers.generateRandomString(10);
		this.author = author;
	}

	// Getters
	public int getId(){
		return id;
	}
	
	public String getKey() {
		return reqKey;
	}

	public Customer getAuthor() {
		return author;
	}
}