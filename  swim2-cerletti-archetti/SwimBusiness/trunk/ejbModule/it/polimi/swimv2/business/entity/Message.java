package it.polimi.swimv2.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "message", uniqueConstraints = @UniqueConstraint(columnNames = {
		"sender", "recevier", "sentdate" }))
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender")
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "receiver")
	private Customer receiver;

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

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}