package it.polimi.swim.business.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	protected User(){}
	
	protected User(String username, String password){
		this.username = username;
		this.passwordHash = hash(password);
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
	
	public boolean matchPassword(String password){
		return hash(password).equals(passwordHash);
	}
	
	public void setPassword(String password){
		this.passwordHash = hash(password);
	}
	
	/*Helper*/
	private String hash(String input){
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(input.getBytes());
			return new String(messageDigest.digest());
		}catch(NoSuchAlgorithmException nse){
			nse.printStackTrace();
			return null;
		}
	}
}
