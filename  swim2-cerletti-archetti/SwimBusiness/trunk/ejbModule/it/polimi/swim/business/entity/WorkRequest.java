package it.polimi.swim.business.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * WorkRequest is an entity which represents an employment relationship between
 * two users.
 */
@Entity
@Table(name = "workrequests")
public class WorkRequest {

	/**
	 * Class constructor.
	 */
	public WorkRequest() {
		this.receiverCompleted = false;
		this.senderCompleted = false;
	}

	/**
	 * Class constructor.
	 * 
	 * @param sender
	 *            the Customer which has sent the work request.
	 * @param receiver
	 *            the Customer which has received the work request.
	 */
	public WorkRequest(Customer sender, Customer receiver) {
		super();
		this.sender = sender;
		this.receiver = receiver;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender", nullable = false)
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "receiver", nullable = false)
	private Customer receiver;

	@Column(name = "senderconfirmed")
	private Boolean senderConfirmed;

	@Column(name = "receiverconfirmed")
	private Boolean receiverConfirmed;

	@Column(name = "sendercompleted")
	private Boolean senderCompleted;

	@Column(name = "receivercompleted")
	private Boolean receiverCompleted;

	@Column(name = "description")
	private String description;

	@Column(name = "location")
	private String location;

	@Column(name = "startdate")
	private Date startDate;

	@Column(name = "enddate")
	private Date endDate;

	@ManyToOne
	@JoinColumn(name = "requiredAbility")
	private Ability requiredAbility;

	@OneToMany(mappedBy = "linkedRequest")
	private List<Message> relatedMessages;

	@OneToOne(mappedBy = "linkedRequest")
	@JoinColumn(name = "feedback")
	private Feedback feedback;

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has sent the work request.
	 */
	public Customer getSender() {
		return sender;
	}

	/**
	 * Setter method.
	 * 
	 * @param sender
	 *            the Customer which has sent the work request.
	 */
	public void setSender(Customer sender) {
		this.sender = sender;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has received the work request.
	 */
	public Customer getReceiver() {
		return receiver;
	}

	/**
	 * Setter method.
	 * 
	 * @param receiver
	 *            the Customer which has received the work request.
	 */
	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the user who has sent the work request has confirmed it,
	 *         false otherwise.
	 */
	public Boolean isConfirmedBySender() {
		return senderConfirmed;
	}

	/**
	 * Setter method.
	 * 
	 * @param senderConfirmed
	 *            true if the user who has sent the work request has confirmed
	 *            it, false otherwise.
	 */
	public void setSenderConfirmed(Boolean senderConfirmed) {
		this.senderConfirmed = senderConfirmed;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the user who has received the work request has confirmed
	 *         it, false otherwise.
	 */
	public Boolean isConfirmedByReceiver() {
		return receiverConfirmed;
	}

	/**
	 * Setter method.
	 * 
	 * @param receiverConfirmed
	 *            true if the user who has received the work request has
	 *            confirmed it, false otherwise.
	 */
	public void setReceiverConfirmed(Boolean receiverConfirmed) {
		this.receiverConfirmed = receiverConfirmed;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the user who has sent the work request has confirmed its
	 *         finish, false otherwise.
	 */
	public Boolean getSenderCompleted() {
		if (senderCompleted == null) {
			return false;
		}

		return senderCompleted;
	}

	/**
	 * Setter method.
	 */
	public void setSenderCompleted() {
		this.senderCompleted = true;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the user who has received the work request has confirmed
	 *         its finish, false otherwise.
	 */
	public Boolean getReceiverCompleted() {
		if (receiverCompleted == null) {
			return false;
		}

		return receiverCompleted;
	}

	/**
	 * Setter method.
	 */
	public void setReceiverCompleted() {
		this.receiverCompleted = true;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains a brief description about what is the
	 *         work request.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method.
	 * 
	 * @param description
	 *            a String which contains a brief description about what is the
	 *            work request.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains the location of the work request.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter method.
	 * 
	 * @param location
	 *            a String which contains the location of the work request.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Date in which the work request starts.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setter method.
	 * 
	 * @param startDate
	 *            the Date in which the work request starts.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Date in which the work request ends.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Setter method.
	 * 
	 * @param endDate
	 *            the Date in which the work request starts.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Ability required by the receiver for the work request.
	 */
	public Ability getRequiredAbility() {
		return requiredAbility;
	}

	/**
	 * Setter method.
	 * 
	 * @param requiredAbility
	 *            the Ability required by the receiver for the work request.
	 */
	public void setRequiredAbility(Ability requiredAbility) {
		this.requiredAbility = requiredAbility;
	}

	/**
	 * Getter method.
	 * 
	 * @return a Set of Message related to the work request.
	 */
	public List<Message> getRelatedMessages() {
		return relatedMessages;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Feedback associated to the work request.
	 */
	public Feedback getFeedback() {
		return feedback;
	}

	/**
	 * Setter method.
	 * 
	 * @param feedback
	 *            the Feedback associated to the work request.
	 */
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Integer value which identifies the work request.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the work request is set completed by both sender and
	 *         receiver, false otherwise.
	 */
	public Boolean isCompleted() {
		return getSenderCompleted() && getReceiverCompleted();
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the work request is set confirmed by both sender and
	 *         receiver, false otherwise.
	 */
	public Boolean isConfirmed() {
		if (receiverConfirmed == null || senderConfirmed == null) {
			return false;
		}
		return receiverConfirmed && senderConfirmed;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if either the sender or the receiver has declined the
	 *         request, false otherwise.
	 */
	public Boolean isDeclined() {
		if (receiverCompleted == null || senderCompleted == null) {
			return false;
		}

		return receiverConfirmed == false || senderConfirmed == false;
	}
}