package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * PasswordRequest is an entity which represents a request made by the user to
 * reset the password.
 */
@Entity
@Table(name = "passwordrequests")
public class PasswordRequest {
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	private boolean confirmed;

	@OneToOne
	@JoinColumn(name = "author")
	private Customer author;

	/**
	 * Getter method.
	 * 
	 * @return true if the password reset request has been confirmed by the
	 *         system, false otherwise.
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Setter method.
	 * 
	 * @param confirmed
	 *            a Boolean value that is true if the password reset request has
	 *            been confirmed by the system, false otherwise.
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has sent the password reset request.
	 */
	public Customer getAuthor() {
		return author;
	}

	/**
	 * Setter method.
	 * 
	 * @param author
	 *            the Customer which has sent the password reset request.
	 */
	public void setAuthor(Customer author) {
		this.author = author;
	}

	/**
	 * Getter method.
	 * 
	 * @return an Integer which identifies the password reset request.
	 */
	public int getId() {
		return id;
	}

}
