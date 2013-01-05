package it.polimi.swim.business.entity;

import it.polimi.swim.business.bean.Helpers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * PasswordRequest is an entity which represents a request made by the user to
 * reset the password.
 */
@Entity
@Table(name = "passwordresetrequests")
public class PasswordResetRequest {

	public PasswordResetRequest() {
	}

	public PasswordResetRequest(Customer author) {
		this.author = author;
		this.key = Helpers.generateRandomString(32);
	}

	@Id
	@Column(name = "reqKey")
	private String key;

	@OneToOne
	@JoinColumn(name = "author")
	private Customer author;

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has sent the password reset request.
	 */
	public Customer getAuthor() {
		return author;
	}

	/**
	 * Getter method.
	 * 
	 * @return an Integer which identifies the password reset request.
	 */
	public String getId() {
		return key;
	}
}
