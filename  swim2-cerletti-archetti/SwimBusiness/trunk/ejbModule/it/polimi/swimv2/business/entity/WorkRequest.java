package it.polimi.swimv2.business.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workrequests")
public class WorkRequest {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender", nullable = false)
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "recevier", nullable = false)
	private Customer receiver;

	@Column(name = "senderconfirmed", nullable = false)
	private Boolean senderConfirmed;

	@Column(name = "receiverconfirmed", nullable = false)
	private Boolean receiverConfirmed;

	@Column(name = "sendercompleted", nullable = false)
	private Boolean senderCompleted;

	@Column(name = "receivercompleted", nullable = true)
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

	@OneToMany
	@JoinTable(name = "work_request_message", joinColumns = @JoinColumn(name = "workrequest"), inverseJoinColumns = @JoinColumn(name = "message"))
	private Set<Message> relatedMessages;

	@OneToOne
	@JoinColumn(name = "feedback")
	private Feedback feedback;

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

	public Boolean getSenderConfirmed() {
		return senderConfirmed;
	}

	public void setSenderConfirmed(Boolean senderConfirmed) {
		this.senderConfirmed = senderConfirmed;
	}

	public Boolean getReceiverConfirmed() {
		return receiverConfirmed;
	}

	public void setReceiverConfirmed(Boolean receiverConfirmed) {
		this.receiverConfirmed = receiverConfirmed;
	}

	public Boolean getSenderCompleted() {
		return senderCompleted;
	}

	public void setSenderCompleted(Boolean senderCompleted) {
		this.senderCompleted = senderCompleted;
	}

	public Boolean getReceiverCompleted() {
		return receiverCompleted;
	}

	public void setReceiverCompleted(Boolean receiverCompleted) {
		this.receiverCompleted = receiverCompleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Ability getRequiredAbility() {
		return requiredAbility;
	}

	public void setRequiredAbility(Ability requiredAbility) {
		this.requiredAbility = requiredAbility;
	}

	public Set<Message> getRelatedMessages() {
		return relatedMessages;
	}

	public void addRelatedMessages(Message message) {
		this.relatedMessages.add(message);
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public int getId() {
		return id;
	}
}
