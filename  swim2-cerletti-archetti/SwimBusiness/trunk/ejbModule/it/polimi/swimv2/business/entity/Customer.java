package it.polimi.swimv2.business.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8131612318471168917L;

	@Id
	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "passwordHash", nullable = false)
	private String passwordHash;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "birthdate", nullable = true)
	private String birthDate;

	@Column(name = "location", nullable = true)
	private String location;

	@Column(name = "photourl", nullable = true)
	private String photourl;

	@OneToMany(mappedBy = "sender")
	@JoinColumn(table = "friendship")
	private List<Friendship> sentFriendship;

	@OneToMany(mappedBy = "receiver")
	@JoinColumn(table = "friendship")
	private List<Friendship> receivedFriendship;

	@ManyToMany
	@JoinTable(name = "declaredabilities", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "ability"))
	private Set<Ability> declaredAbilities;

	public Set<Ability> getDeclaredAbilities() {
		return declaredAbilities;
	}

	public void addDeclaredAbilities(Ability declaredAbility) {
		this.declaredAbilities.add(declaredAbility);
	}

	/* Setters and getters */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public List<Friendship> getSentFriendship() {
		return sentFriendship;
	}

	public List<Friendship> getReceivedFriendship() {
		return receivedFriendship;
	}
}
