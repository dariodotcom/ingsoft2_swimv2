package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback {

	public Feedback() {
	}

	public Feedback(int mark, String review, WorkRequest w) {
		this.mark = mark;
		this.review = review;
		this.linkedRequest = w;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "mark", nullable=false)
	private int mark;

	@Column(name = "review", nullable=false)
	private String review;

	@Column(name = "reply")
	private String reply;

	@OneToOne(mappedBy = "feedback")
	@JoinColumn(nullable = false, unique = true)
	private WorkRequest linkedRequest;

	/* Setters and Getters */
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