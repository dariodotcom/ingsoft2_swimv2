package it.polimi.swim.business.entity;

import it.polimi.swim.business.bean.Helpers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "emailvalidationrequest")
public class EmailValidationRequest {

	@Id
	@Column(name = "reqKey")
	private String key;

	@OneToOne
	@JoinColumn(name = "author", unique = true)
	private Customer author;

	/**
	 * Class constructor.
	 */
	public EmailValidationRequest() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param author
	 *            a Customer that is the author of the email validation request.
	 */
	public EmailValidationRequest(Customer author) {
		this.key = Helpers.generateRandomString(32);
		this.author = author;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the key of the email validation request.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Getter method.
	 * 
	 * @return a Customer that is the author of the email validation request.
	 */
	public Customer getAuthor() {
		return author;
	}
}