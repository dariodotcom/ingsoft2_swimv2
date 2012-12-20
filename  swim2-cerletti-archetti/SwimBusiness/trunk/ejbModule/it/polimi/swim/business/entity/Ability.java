package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ability")
public class Ability {

	public Ability(String name, String description, Administrator acceptor) {
		this.name = name;
		this.description = description;
		this.approvedBy = acceptor;
	}

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "description", nullable=false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "approvedBy", nullable=false)
	private Administrator approvedBy;

	/* Getters and Setters */
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

	public Administrator getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Administrator approvedBy) {
		this.approvedBy = approvedBy;
	}
}