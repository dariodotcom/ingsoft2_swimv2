package it.polimi.swim.business.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Customer is an entity which represents a registered user on the platform
 * SWIMv2, whether a professional or a hypothetical employer.
 */
@Entity
@Table(name = "customer")
public class Customer extends User {

	/**
	 * Class contructor.
	 */
	public Customer() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param username
	 *            a String which contains the username of the customer.
	 * @param passwordHash
	 *            a String which contains the hash value of the password set by
	 *            the customer.
	 * @param email
	 *            a String which contains the e-mail address set by the
	 *            customer.
	 * @param name
	 *            a String which contains the name of the customer.
	 * @param surname
	 *            a String which contains the surname of the customer.
	 */
	public Customer(String username, String passwordHash, String email,
			String name, String surname) {
		super(username, passwordHash);

		this.email = email;
		this.name = name;
		this.surname = surname;
		this.emailConfirmed = false;
	}

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "birthdate")
	private Date birthDate;

	@Column(name = "location")
	private String location;

	@Lob
	@Column(name = "photourl", nullable = true)
	private byte[] customerPhoto;

	@Lob
	@Column(name = "thumbnail", nullable = true)
	private byte[] customerThumbnail;

	@Column(name = "emailactive", nullable = false)
	private Boolean emailConfirmed;

	/* Relationship */

	@OneToMany(mappedBy = "sender")
	private List<Friendship> sentFriendshipReq;

	@OneToMany(mappedBy = "receiver")
	private List<Friendship> receivedFriendshipReq;

	@ManyToMany
	List<Ability> declaredAbilities;

	/**
	 * Getter method.
	 * 
	 * @return a String which contains the customer e-mail address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method.
	 * 
	 * @param email
	 *            a String which contains the customer e-mail address.
	 */
	public void setEmail(String email) {
		this.email = email;
		this.emailConfirmed = false;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains the customer name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method.
	 * 
	 * @param name
	 *            a String which contains the customer name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains the customer surname.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Setter method.
	 * 
	 * @param surname
	 *            a String which contains the customer surname.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Getter method.
	 * 
	 * @return a Date which contains the customer birthdate.
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter method.
	 * 
	 * @param birthDate
	 *            a Date which contains the customer birthdate.
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains the customer residence location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter method.
	 * 
	 * @param location
	 *            a String which contains the customer residence location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Getter method.
	 * 
	 * @return an array of byte that represent a user profile image.
	 */
	public byte[] getCustomerPhoto() {
		return customerPhoto;
	}

	/**
	 * Setter method.
	 * 
	 * @param customerPhoto
	 *            an array of byte that represent a user profile image.
	 */
	public void setCustomerPhoto(byte[] customerPhoto) {
		this.customerPhoto = customerPhoto;
	}

	/**
	 * Getter method.
	 * 
	 * @return a set of byte that represent the thumbnail associated to a user
	 *         profile image.
	 */
	public byte[] getCustomerThumbnail() {
		return customerThumbnail;
	}

	/**
	 * Setter method.
	 * 
	 * @param customerThumbnail
	 *            a set of byte that represent the thumbnail associated to a
	 *            user profile image.
	 */
	public void setCustomerThumbnail(byte[] customerThumbnail) {
		this.customerThumbnail = customerThumbnail;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the customer e-mail has been confirmed, false otherwise.
	 */
	public boolean isEmailConfirmed() {
		return emailConfirmed;
	}

	/**
	 * Sets user email as confirmed.
	 * */
	public void setEmailConfirmed() {
		this.emailConfirmed = true;
	}

	/**
	 * Getter method.
	 * 
	 * @return a List of Customer which contains the customer friend list.
	 */
	public List<Customer> getFriends() {
		List<Customer> friends = new ArrayList<Customer>();

		for (Friendship f : sentFriendshipReq) {
			if (f.isConfirmed()) {
				friends.add(f.getReceiver());
			}
		}

		for (Friendship f : receivedFriendshipReq) {
			if (f.isConfirmed()) {
				friends.add(f.getSender());
			}
		}

		return friends;
	}

	/**
	 * Getter method.
	 * 
	 * @return the List of Friendship requests that the customer has sent.
	 */
	public List<Friendship> getSentFriendship() {
		return sentFriendshipReq;
	}

	/**
	 * Getter method.
	 * 
	 * @return the List of Friendship requests that the customer has received.
	 */
	public List<Friendship> getReceivedFriendship() {
		return receivedFriendshipReq;
	}

	/**
	 * Method to find out whether this Customer can be friend with another one.
	 * Two customers can be friend only if there isn't a friendship request
	 * between them yet.
	 * 
	 * @param otherCustomer
	 *            - The other Customer.
	 * @return true if friendship is possible.
	 */
	public Boolean canBeSentFRBy(Customer otherCustomer) {
		if (this.equals(otherCustomer)) {
			return false;
		}

		for (Friendship f : sentFriendshipReq) {
			if (f.getReceiver().equals(otherCustomer)) {
				return false;
			}
		}

		for (Friendship f : receivedFriendshipReq) {
			if (f.getSender().equals(otherCustomer)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Method that provides the list of abilities declared by this Customer.
	 * 
	 * @return the List of Ability declared by this Customer.
	 */
	public List<Ability> getAbilityList() {
		return declaredAbilities;
	}

	/**
	 * Method that adds a given ability to the set of abilities declared by this
	 * Customer.
	 * 
	 * @param a
	 *            the Ability to add.
	 */
	public void addAbility(Ability a) {
		this.declaredAbilities.add(a);
	}

	/**
	 * Method that removes a given ability to the set of abilities declared by
	 * this Customer.
	 * 
	 * @param a
	 *            the Ability to remove.
	 */
	public void removeAbility(Ability a) {
		this.declaredAbilities.remove(a);
	}
}