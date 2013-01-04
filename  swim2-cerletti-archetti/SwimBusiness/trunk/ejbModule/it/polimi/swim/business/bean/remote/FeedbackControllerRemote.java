package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.exceptions.UnauthorizedRequestException;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import javax.ejb.Remote;

/**
 * FeedbackControllerRemote is an interface that manages the database in case of
 * feedback addition.
 */
@Remote
public interface FeedbackControllerRemote {
	/**
	 * This method manages the adding of a new feedback to the database.
	 * 
	 * @param workRequestId
	 *            an Integer which contains the identifier of the associated
	 *            work request.
	 * @param authorUsr
	 *            a String which contains the username of the user which
	 *            provides the evaluation.
	 * @param mark
	 *            an Integer between 1 and 5 given by the employer to the
	 *            professional.
	 * @param review
	 *            a String that contains a brief comment for the given
	 *            valuation.
	 * @throws UnauthorizedRequestException
	 *             a feedback request is not authorized.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void createFeedback(int workRequestId, String authorUsr, int mark,
			String review) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException;

	/**
	 * This method manages the registration of a reply for a given feedback to
	 * the database.
	 * 
	 * @param workRequestId
	 *            an Integer which contains the identifier of the associated
	 *            work request.
	 * @param authorUsr
	 *            a String which contains the username of the user which
	 *            provides the reply.
	 * @param reply
	 *            a String which contains a brief comment to the achieved
	 *            feedback.
	 * @throws UnauthorizedRequestException
	 *             a feedback reply request is not authorized.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void replyToFeedback(int workRequestId, String authorUsr,
			String reply) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException;
}
