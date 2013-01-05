package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Ability is an entity that represents a skill that a user registered to the
 * platform can claim to have, or, on the opposite side, a profession that you
 * want to try to establish for working relationship.
 */
@Entity
@Table(name = "ability")
public class Ability {

	@Override
	public String toString() {
		return getName();
	}


	public Ability() {
	}
	
	
	/**
	 * Class constructor.
	 * 
	 * @param name
	 *            a String which contains the name of an ability.
	 * @param description
	 *            a String which contains a brief description of the ability.
	 * @param acceptor
	 *            the Administrator that has inserted the ability to the set.
	 */
	public Ability(String name, String description, Administrator acceptor) {
		this.name = name;
		this.description = description;
		this.approvedBy = acceptor;
	}

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "approvedBy", nullable = false)
	private Administrator approvedBy;

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the ability name.
	 */
	public String getName() {
		return name.toLowerCase();
	}

	/**
	 * Setter method.
	 * 
	 * @param name
	 *            a String that contains the ability name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the ability description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method.
	 * 
	 * @param description
	 *            a String that contains the ability description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Administrator that has approved the ability.
	 */
	public Administrator getApprovedBy() {
		return approvedBy;
	}

	/**
	 * Setter method.
	 * 
	 * @param approvedBy
	 *            the Administrator that has approved the ability.
	 */
	public void setApprovedBy(Administrator approvedBy) {
		this.approvedBy = approvedBy;
	}
}