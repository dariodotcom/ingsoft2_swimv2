package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "passwordrequests")
public class PasswordRequest {
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;
	
	private boolean confirmed;
	
	@OneToOne
	@JoinColumn(name="author")
	private Customer author;

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Customer getAuthor() {
		return author;
	}

	public void setAuthor(Customer author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}
	
}
