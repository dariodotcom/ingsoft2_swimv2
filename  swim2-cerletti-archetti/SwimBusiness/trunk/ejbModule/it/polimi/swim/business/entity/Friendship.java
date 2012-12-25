package it.polimi.swim.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Friendship is an entity which represents Friendship a relationship of
 * friendship between two users. Through the definition of these "favorites"
 * contacts a user can decide to do research filtered from the list of his
 * friends or between users of the system.
 */
@Entity
@Table(name = "friendship")
public class Friendship {

	/**
	 * Class constructor.
	 */
	public Friendship() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param sender
	 *            a Customer which is the sender of the friendship request.
	 * @param receiver
	 *            a Customer which is the receiver of the friendship request.
	 */
	public Friendship(Customer sender, Customer receiver) {
		this.sender = sender;
		this.receiver = receiver;
		this.confirmed = false;
	}

	@Id
	@GeneratedValue
	@Column(name = "friendship")
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender", nullable = false)
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "receiver", nullable = false)
	private Customer receiver;

	@Column(name = "confirmed", nullable = false)
	private Boolean confirmed;

	@Column(name = "confirmedDate")
	private Date confirmedDate;

	/**
	 * Getter method.
	 * 
	 * @return true if the friendship request has been confirmed by the
	 *         receiver, false otherwise.
	 */
	public Boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Setter method.
	 */
	public void setConfirmed() {
		this.confirmed = true;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Date in which the friendship has been confirmed.
	 */
	public Date getConfirmedDate() {
		return confirmedDate;
	}

	/**
	 * Setter method.
	 * 
	 * @param confirmedDate
	 *            the Date in which the friendship has been confirmed.
	 */
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	/**
	 * Getter method.
	 * 
	 * @return an Integer which identifies the friendship.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has sent the friendship request.
	 */
	public Customer getSender() {
		return sender;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has received the friendship request.
	 */
	public Customer getReceiver() {
		return receiver;
	}
}
