package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ability_requests")
public class AbilityRequest {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(name = "abilityName")
	private String abilityName;

	@Column(name = "abilityDescription")
	private String abilityDescription;

	@Column(name = "approved")
	private Boolean approved;

	@ManyToOne
	@JoinColumn(name = "requestauthor")
	private Customer requestAuthor;

	/* Getters and Setters */
	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public String getAbilityDescription() {
		return abilityDescription;
	}

	public void setAbilityDescription(String abilityDescription) {
		this.abilityDescription = abilityDescription;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Customer getRequestAuthor() {
		return requestAuthor;
	}

	public void setRequestAuthor(Customer requestAuthor) {
		this.requestAuthor = requestAuthor;
	}

	public int getId() {
		return id;
	}
}
