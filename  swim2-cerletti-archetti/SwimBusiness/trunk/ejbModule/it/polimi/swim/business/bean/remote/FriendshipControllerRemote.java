package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Remote;

/**
 * FriendshipControllerRemote is an interface that manages the database in case
 * of friendship addition.
 */
@Remote
public interface FriendshipControllerRemote {
	/**
	 * This method is useful to add a new friendship request to the database.
	 * 
	 * @param senderUsr
	 *            a String that contains the username of the user that sends the
	 *            friendship request.
	 * @param receiverUsr
	 *            a String that contains the username of the user that receives
	 *            the friendship request.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void addFriendshipRequest(String senderUsr, String receiverUsr)
			throws BadRequestException, InvalidStateException;

	/**
	 * This method is useful to register in the database the response to a
	 * friendship request.
	 * 
	 * @param responseAuthorUsr
	 *            a String that contains the username of the user that receives
	 *            the friendship request.
	 * @param requestId
	 *            an Integer that contains the identifier of the associated
	 *            friendship request.
	 * @param response
	 *            a Boolean value that is true if the request was accepted,
	 *            false otherwise.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 * @throws UnauthorizedRequestException
	 *             a feedback request is not authorized.
	 */
	public void respondToRequest(String responseAuthorUsr, int requestId,
			boolean response) throws BadRequestException,
			InvalidStateException, UnauthorizedRequestException;

	/**
	 * This method is useful to remove a friendship request registered in the
	 * database.
	 * 
	 * @param requestAuthorUsr
	 *            a String that contains the username of the user that has sent
	 *            the friendship request.
	 * @param requestId
	 *            an Integer that is the identfier of the friendship request.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 * @throws UnauthorizedRequestException
	 *             a feedback request is not authorized.
	 */
	public void removeFriendship(String requestAuthorUsr, int requestId)
			throws BadRequestException, InvalidStateException,
			UnauthorizedRequestException;
}
