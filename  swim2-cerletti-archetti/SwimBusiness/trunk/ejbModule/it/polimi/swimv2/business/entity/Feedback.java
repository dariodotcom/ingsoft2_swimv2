package it.polimi.swimv2.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="feedbacks")
public class Feedback {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
	private int id;
	
	@Column(name="mark")
	private int mark;
	
	@Column(name="review")
	private String review;
	
	@Column(name="reply")
	private String reply;
	
	@OneToOne(mappedBy="feedback")
	private WorkRequest linkedRequest;

	/*Setters and Getters*/
	public int getId() {
		return id;
	}	
	
	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public WorkRequest getLinkedRequest() {
		return linkedRequest;
	}
}
