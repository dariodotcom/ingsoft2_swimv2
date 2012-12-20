package it.polimi.swim.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friendship")
public class Friendship {

	public Friendship(){}
	
	public Friendship(Customer sender, Customer receiver){
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

	@Column(name="confirmedDate")
	private Date confirmedDate;

	/*Setters and Getters*/
	public Boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed() {
		this.confirmed = true;
	}

	public Date getConfirmedDate() {
		return confirmedDate;
	}

	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	public int getId() {
		return id;
	}

	public Customer getSender() {
		return sender;
	}

	public Customer getReceiver() {
		return receiver;
	}	
}
