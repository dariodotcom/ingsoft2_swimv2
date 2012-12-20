package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.exceptions.BadRequestException;
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
	 * */
	public int createWorkRequest(String senderUsr, String receiverUsr)
			throws BadRequestException;

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
	 * */
	public void respondToWorkRequest(String responseAuthorUsr,
			Boolean responseDescriptor, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException;

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
	 * */
	public void markRequestAsCompleted(String actorUsr, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException;

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
	 */
	public void sendMessage(String authorUsr, String text, int workRequestId)
			throws UnauthorizedRequestException, BadRequestException;
}