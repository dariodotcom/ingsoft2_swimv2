package it.polimi.swim.business.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * User is an entity that represents a user of the system, which may be a
 * customer or an Administrator.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

	/**
	 * Class constructor.
	 */
	protected User() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param username
	 *            a String which contains the username chosen by the user.
	 * @param password
	 *            a String which contains the hash value of the password set by
	 *            the user.
	 */
	protected User(String username, String password) {
		this.username = username.toLowerCase();
		this.passwordHash = hash(password);
	}

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "passwordHash", nullable = false)
	private String passwordHash;

	/* Relationship */

	@OneToMany
	List<Ability> declaredAbilities;

	/**
	 * Getter method.
	 * 
	 * @return the String which contains the username chosen by the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter method.
	 * 
	 * @param password
	 *            the String which contains the hash value of the password set
	 *            by the user.
	 */
	public void setPassword(String password) {
		this.passwordHash = hash(password);
	}

	/* Helper */

	/**
	 * This method is useful to verify if the hash value of a given password
	 * corresponds or not with the one registered in the database.
	 * 
	 * @param password
	 *            a String that contains the password to verify.
	 * @return true if the given password hash corresponds with the one present
	 *         in the system database, false otherwise.
	 */
	public boolean matchPassword(String password) {
		return hash(password).equals(passwordHash);
	}

	private String hash(String input) {
		try {
			byte[] inputBytes = input.getBytes();
			byte[] hash = MessageDigest.getInstance("SHA-1").digest(inputBytes);
			return new BigInteger(1, hash).toString(16);
		} catch (NoSuchAlgorithmException nse) {
			nse.printStackTrace();
			return null;
		}
	}
}
