package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AbilityRequest is an entity which represents the request to the administrator
 * by a user to add a skill to the set of professions predefined by the system.
 */
@Entity
@Table(name = "ability_requests")
public class AbilityRequest {

	/**
	 * Class constructor.
	 */
	public AbilityRequest() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param author
	 *            the Customer that has sent the request.
	 * @param name
	 *            a String that contains the name of the suggested ability.
	 * @param description
	 *            a String that contains a brief description of the suggested
	 *            ability.
	 */
	public AbilityRequest(Customer author, String name, String description) {
		this.abilityName = name;
		this.abilityDescription = description;
		this.requestAuthor = author;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "abilityName")
	private String abilityName;

	@Column(name = "abilityDescription")
	private String abilityDescription;

	@Column(name = "approved")
	private Boolean approved;

	@Column(name = "review")
	private String review;

	@ManyToOne
	@JoinColumn(name = "requestauthor")
	private Customer requestAuthor;

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the name given to the suggested ability.
	 */
	public String getAbilityName() {
		return abilityName;
	}

	/**
	 * Setter method.
	 * 
	 * @param abilityName
	 *            a String that contains the name given to the suggested
	 *            ability.
	 */
	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the description given to the suggested
	 *         ability.
	 */
	public String getAbilityDescription() {
		return abilityDescription;
	}

	/**
	 * Setter method.
	 * 
	 * @param abilityDescription
	 *            a String that contains the description given to the suggested
	 *            ability.
	 */
	public void setAbilityDescription(String abilityDescription) {
		this.abilityDescription = abilityDescription;
	}

	/**
	 * Getter method.
	 * 
	 * @return a Boolean that is true if the ability request has been approved,
	 *         false otherwise.
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * Setter method.
	 * 
	 * @param approved
	 *            a Boolean that is true if the ability request has been
	 *            approved, false otherwise.
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the review given by the administrator for
	 *         the ability request.
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Setter method.
	 * 
	 * @param review
	 *            a String that contains the review given by the administrator
	 *            for the ability request.
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer that has sent the request.
	 */
	public Customer getRequestAuthor() {
		return requestAuthor;
	}

	/**
	 * Setter method.
	 * 
	 * @param requestAuthor
	 *            the Customer that has sent the request.
	 */
	public void setRequestAuthor(Customer requestAuthor) {
		this.requestAuthor = requestAuthor;
	}

	/**
	 * Getter method.
	 * 
	 * @return an Integer that represents the identifier of the request.
	 */
	public int getId() {
		return id;
	}
}
