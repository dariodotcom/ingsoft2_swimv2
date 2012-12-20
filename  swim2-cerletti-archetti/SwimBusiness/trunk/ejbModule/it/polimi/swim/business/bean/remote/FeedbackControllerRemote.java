package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.bean.exceptions.InvalidMarkException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Remote;

@Remote
public interface FeedbackControllerRemote {
	public void createFeedback(int workRequestId, String authorUsr, int mark,
			String review) throws UnauthorizedRequestException, InvalidMarkException;

	public void replyToFeedback(int workRequestId, String authorUsr,
			String reply) throws UnauthorizedRequestException;
}
