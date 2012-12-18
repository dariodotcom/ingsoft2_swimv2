package it.polimi.swimv2.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friendship")
@IdClass(FriendshipKey.class)
public class Friendship {

	@Id
	@ManyToOne
	@JoinColumn(name = "sender")
	private Customer sender;

	@Id
	@ManyToOne
	@JoinColumn(name = "receiver")
	private Customer receiver;

	@Column(name = "reference", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
	private int reference;

	@Column(name = "confirmed", nullable = false)
	private Boolean confirmed;

	@Column(name = "dateConfirmed", nullable = false)
	private Date dateConfirmed;

	/* Setters and Getters */
	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Customer getReceiver() {
		return receiver;
	}

	public int getReference() {
		return reference;
	}

	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}

}
