package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ability")
public class Ability {
	
	public Ability(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "approvedBy")
	private Administrator approvedBy;

	/* Getters and Setters */
	public Integer getId() {
		return id;
	}

	public Administrator getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Administrator approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setId(Integer id) {
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
}