package it.polimi.swimv2.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="administrators")
public class Administrator {

	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="passwordHash")
	private String passwordHash;

	
	/*Getters and setters*/
	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
