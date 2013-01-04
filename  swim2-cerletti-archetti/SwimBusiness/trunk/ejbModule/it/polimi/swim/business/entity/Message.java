package it.polimi.swim.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Message is an entity which represents a message that is exchanged between
 * employer and professional, or vice versa, to discuss a job offer.
 */
@Entity
@Table(name = "message", uniqueConstraints = @UniqueConstraint(columnNames = {
		"sender", "receiver", "sentdate" }))
public class Message {

	/**
	 * Class constructor.
	 */
	public Message() {
		this.sentDate = new Date();
	}

	/**
	 * Class constructor.
	 * 
	 * @param request
	 * @param sender
	 * @param text
	 */
	public Message(WorkRequest request, Customer sender, String text) {
		super();
		this.message = text;
		this.sender = sender;
		this.linkedRequest = request;
		this.sentDate = new Date();
	}

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender", nullable = false)
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "receiver")
	private Customer receiver;

	@ManyToOne
	@JoinColumn(name = "linkedrequest", nullable = false)
	private WorkRequest linkedRequest;

	@Column(name = "sentdate", nullable = false)
	private Date sentDate;

	@Column(name = "text", nullable = false)
	private String message;

	/**
	 * Getter method.
	 * 
	 * @return an Integer which identifies a message.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has sent the message.
	 */
	public Customer getSender() {
		return sender;
	}

	/**
	 * Setter method.
	 * 
	 * @param sender
	 *            the Customer which has sent the message.
	 */
	public void setSender(Customer sender) {
		this.sender = sender;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Customer which has received the message.
	 */
	public Customer getReceiver() {
		return receiver;
	}

	/**
	 * Setter method.
	 * 
	 * @param receiver
	 *            the Customer which has received the message.
	 */
	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Date in which the message has been sent.
	 */
	public Date getSentDate() {
		return sentDate;
	}

	/**
	 * Getter method.
	 * 
	 * @return the String which contains the sent message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Getter method.
	 * 
	 * @return the WorkRequest associated to the sent message.
	 */
	public WorkRequest getLinkedRequest() {
		return linkedRequest;
	}

	/**
	 * Setter method.
	 * 
	 * @param message
	 *            the String which contains the sent message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}