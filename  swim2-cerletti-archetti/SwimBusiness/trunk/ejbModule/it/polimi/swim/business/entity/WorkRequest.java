package it.polimi.swim.business.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workrequests")
public class WorkRequest {

	public WorkRequest() {
	}

	public WorkRequest(Customer sender, Customer receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender", nullable = false)
	private Customer sender;

	@ManyToOne
	@JoinColumn(name = "receiver", nullable = false)
	private Customer receiver;

	@Column(name = "senderconfirmed")
	private Boolean senderConfirmed;

	@Column(name = "receiverconfirmed")
	private Boolean receiverConfirmed;

	@Column(name = "sendercompleted")
	private Boolean senderCompleted;

	@Column(name = "receivercompleted")
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

	@OneToMany(mappedBy = "linkedRequest")
	private Set<Message> relatedMessages;

	@OneToOne(mappedBy="linkedRequest")
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

	public void setSenderCompleted() {
		this.senderCompleted = true;
	}

	public Boolean getReceiverCompleted() {
		return receiverCompleted;
	}

	public void setReceiverCompleted() {
		this.receiverCompleted = true;
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

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public int getId() {
		return id;
	}

	public boolean isCompleted() {
		return receiverCompleted && senderCompleted;
	}

	public boolean isConfirmed() {
		return receiverConfirmed && senderConfirmed;
	}
}