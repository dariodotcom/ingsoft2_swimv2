package it.polimi.swim.business.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	
	protected User(String username, String passwordHash){
		this.username = username;
		this.passwordHash = passwordHash;
	}
	
	@Id
	@Column(name="username")
	private String username;

	@Column(name="passwordHash", nullable=false)
	private String passwordHash;
	
	/*Relationship*/
	@OneToMany
	List<Ability> declaredAbilities;
	
	/*Getters and Setters*/
	public String getUsername(){
		return username;
	}
	
	public String getPasswordHash(){
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash){
		this.passwordHash = passwordHash;
	}
}
