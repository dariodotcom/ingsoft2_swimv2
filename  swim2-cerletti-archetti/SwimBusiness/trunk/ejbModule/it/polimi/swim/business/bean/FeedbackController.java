package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.FeedbackControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Feedback;
import it.polimi.swim.business.entity.WorkRequest;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class FeedbackController.
 */
@Stateless
public class FeedbackController implements FeedbackControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * @see FeedbackControllerRemote
	 */
	public void createFeedback(int workRequestId, String authorUsr, int mark,
			String review) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException {

		WorkRequest targetRequest = getWorkRequest(workRequestId);
		Customer feedbackAuthor = getCustomer(authorUsr);

		/* Check request */
		if (!isMarkValid(mark) || !isStringValid(review)) {
			throw new BadRequestException();
		}

		/* Check that feedback's author is the sender of the work request */
		if (!targetRequest.getSender().equals(feedbackAuthor)) {
			throw new UnauthorizedRequestException();
		}

		/*
		 * Check that target work request is in correct state, which is
		 * completed and with no feedback
		 */
		Boolean notCompleted = !targetRequest.isCompleted();
		Boolean hasFeedback = targetRequest.getFeedback() != null;

		if (notCompleted || hasFeedback) {
			throw new InvalidStateException();
		}

		/* Create feedback */
		Feedback f = new Feedback(mark, review, targetRequest);
		manager.persist(f);
	}

	/**
	 * @see FeedbackControllerRemote
	 */
	public void replyToFeedback(int workRequestId, String authorUsr,
			String reply) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException {

		WorkRequest targetRequest = getWorkRequest(workRequestId);
		Customer feedbackAuthor = getCustomer(authorUsr);

		/* Check request */
		if (!isStringValid(reply)) {
			throw new BadRequestException();
		}

		/* Check state */
		Boolean rightCustomer = targetRequest.getReceiver().equals(
				feedbackAuthor);
		Boolean hasFeedback = targetRequest.getFeedback() != null;

		if (!rightCustomer || !hasFeedback) {
			throw new InvalidStateException();
		}

		Feedback f = targetRequest.getFeedback();
		f.setReply(reply);
	}

	/* Helpers */

	private WorkRequest getWorkRequest(int id) throws BadRequestException {
		WorkRequest request = manager.find(WorkRequest.class, id);
		if (request == null) {
			throw new BadRequestException();
		} else {
			return request;
		}
	}

	private Customer getCustomer(String username) throws BadRequestException {
		Customer customer = manager.find(Customer.class, username);
		if (customer == null) {
			throw new BadRequestException();
		} else {
			return customer;
		}
	}

	private boolean isMarkValid(int mark) {
		return mark > 0 && mark <= 5;
	}

	private boolean isStringValid(String input) {
		return input != null && input.length() > 0;
	}
}