package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.exceptions.UnauthorizedRequestException;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import javax.ejb.Remote;

@Remote
public interface FeedbackControllerRemote {
	public void createFeedback(int workRequestId, String authorUsr, int mark,
			String review) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException;

	public void replyToFeedback(int workRequestId, String authorUsr,
			String reply) throws UnauthorizedRequestException,
			BadRequestException, InvalidStateException;
}
