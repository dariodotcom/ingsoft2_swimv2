package it.polimi.swim.business.bean.remote;

import java.util.List;
import java.util.Map;

import it.polimi.swim.business.entity.Feedback;
import it.polimi.swim.business.entity.WorkRequest;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Remote;

/**
 * Controller to manage work requests between users. A work request describes a
 * work interaction between two user, from initial stage to feedback expression.
 * */

@Remote
public interface WorkRequestControllerRemote {
	/**
	 * Creates a new work request between two users.
	 * 
	 * @param senderUsr
	 *            - the identifier of the user sending the request.
	 * @param recevierUsr
	 *            - the identifier of the user receiving the request.
	 * @return the identifier of the created work request.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 * */
	public int createWorkRequest(Map<String, Object> properties)
			throws BadRequestException, InvalidStateException;

	/**
	 * Confirms or cancels a work request. Both the sender and the receiver can
	 * perform this operation.
	 * 
	 * @param responseAuthorUsr
	 *            - the identifier of the response author.
	 * @param responseDescriptor
	 *            - a Boolean describing the user's response.
	 * @param workRequestId
	 *            - the work request identifier.
	 * @throws UnauthorizedRequestException
	 *             when user who performed the action is not involved in the
	 *             request.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 * */
	public void respondToWorkRequest(String responseAuthorUsr,
			Boolean responseDescriptor, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException,
			InvalidStateException;

	/**
	 * Marks a request as complete by either the sender or the receiver
	 * 
	 * @param actorUsr
	 *            - the identifier of the response author.
	 * @param workRequestId
	 *            - the work request identifier.
	 * @throws UnauthorizedRequestException
	 *             when user who performed the action is not involved in the
	 *             request.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 * */
	public void markRequestAsCompleted(String actorUsr, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException,
			InvalidStateException;

	/**
	 * Send a message in the context of a specific work request.
	 * 
	 * @param authorUsr
	 *            - the identifier of the user who sent the message.
	 * @param text
	 *            - the message content.
	 * @param workRequestId
	 *            - the work request identifier.
	 * @throws UnauthorizedRequestException
	 *             when user who performed the action is not involved in the
	 *             request.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void sendMessage(String authorUsr, String text, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException,
			InvalidStateException;

	/**
	 * This method provides a work request associated to a given identifier.
	 * 
	 * @param requestId
	 *            a integer that contains a work request identifier.
	 * @return the WorkRequest associated to the given work request identifier.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public WorkRequest getById(int requestId) throws BadRequestException;

	/**
	 * This method provides a list of messages exchanged during a specific work
	 * request.
	 * 
	 * @param reqId
	 *            a integer that contains a work request identifier.
	 * @return the List of messages associated to the given work request
	 *         identifier.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public List<?> getMessageList(int reqId) throws BadRequestException;

	/**
	 * This method provides the feedback associated to a given work request
	 * given to the professional by the employee.
	 * 
	 * @param reqId
	 *            a integer that contains the work request identifier.
	 * @return the Feedback associated to the given work request identifier.
	 */
	public Feedback getFeedback(int reqId);
}