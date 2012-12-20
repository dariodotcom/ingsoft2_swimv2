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

@Entity
@Table(name = "message", uniqueConstraints = @UniqueConstraint(columnNames = {
		"sender", "receiver", "sentdate" }))
public class Message {
	
	public Message() {
		this.sentDate = new Date();
	}

	public Message(WorkRequest request, Customer sender, String text){
		super();
		this.message = text;
		this.sender = sender;
		this.linkedRequest = request;
	}
	
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender", nullable=false)
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "receiver")
	private Customer receiver;

	@ManyToOne
	@JoinColumn(name = "linkedrequest", nullable=false)
	private WorkRequest linkedRequest;
	
	@Column(name = "sentdate", nullable = false)
	private Date sentDate;

	@Column(name = "text", nullable = false)
	private String message;

	/* Getters and Setters */
	public int getId() {
		return id;
	}

	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Customer getReceiver() {
		return receiver;
	}

	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public String getMessage() {
		return message;
	}

	public WorkRequest getLinkedRequest() {
		return linkedRequest;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}