package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.FeedbackControllerRemote;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class FeedbackController
 */
@Stateless
public class FeedbackController implements FeedbackControllerRemote {

    /**
     * Default constructor. 
     */
    public FeedbackController() {
        // TODO Auto-generated constructor stub
    }

	public void createFeedback(int workRequestId, String authorUsr, int mark,
			String review) throws UnauthorizedRequestException {
		// TODO Auto-generated method stub
		
	}

	public void replyToFeedback(int workRequestId, String authorUsr,
			String reply) {
		// TODO Auto-generated method stub
		
	}

}
