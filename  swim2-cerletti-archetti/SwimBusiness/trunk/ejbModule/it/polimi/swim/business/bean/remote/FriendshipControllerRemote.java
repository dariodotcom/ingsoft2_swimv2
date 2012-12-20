package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Remote;

@Remote
public interface FriendshipControllerRemote {
	public void addFriendshipRequest(String senderUsr, String receiverUsr)
			throws BadRequestException, InvalidStateException;

	public void respondToRequest(String responseAuthorUsr, int requestId,
			boolean response) throws BadRequestException,
			InvalidStateException, UnauthorizedRequestException;

	public void removeFriendship(String requestAuthorUsr, int requestId)
			throws BadRequestException, InvalidStateException, UnauthorizedRequestException;
}
