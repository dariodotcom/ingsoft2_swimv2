package it.polimi.swim.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Feedback is an entity which represents an assessment of the services provided
 * by the professional and is provided by the employer at the end of a working
 * relationship.
 */
@Entity
@Table(name = "feedbacks")
public class Feedback {

	/**
	 * Class constructor.
	 */
	public Feedback() {
	}

	/**
	 * Class constructor.
	 * 
	 * @param mark
	 *            an Integer which represents a value between 1 and 5 achieved
	 *            by a professional.
	 * @param review
	 *            a String which contains a brief review given by the customer
	 *            which has sent the workrequest.
	 * @param w
	 *            the WorkRequest associated to the feedback.
	 */
	public Feedback(int mark, String review, WorkRequest w) {
		this.mark = mark;
		this.review = review;
		this.linkedRequest = w;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "mark", nullable = false)
	private int mark;

	@Column(name = "review", nullable = false)
	private String review;

	@Column(name = "reply")
	private String reply;

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private WorkRequest linkedRequest;

	/**
	 * Getter method.
	 * 
	 * @return an Integer which identifies the feedback and consequently the
	 *         associated work request.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method.
	 * 
	 * @return an Integer which is the achieved mark.
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * Setter method.
	 * 
	 * @param mark
	 *            an Integer which is the achieved mark.
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains a brief review about the customer
	 *         performance.
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Setter method.
	 * 
	 * @param review
	 *            a String which contains a brief review about the customer
	 *            performance.
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String which contains a brief reply to the given feedback.
	 */
	public String getReply() {
		return reply;
	}

	/**
	 * Setter method.
	 * 
	 * @param reply
	 *            a String which contains a brief reply to the given feedback.
	 */
	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * Getter method.
	 * 
	 * @return the WorkRequest associated to the feedback.
	 */
	public WorkRequest getLinkedRequest() {
		return linkedRequest;
	}
}