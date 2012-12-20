package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class WorkRequestControl
 */
@Stateless
public class WorkRequestController implements WorkRequestControllerRemote {

    /**
     * Default constructor. 
     */
    public WorkRequestController() {
        // TODO Auto-generated constructor stub
    }

	public int createWorkRequest(String senderUsr, String receiverUsr) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void respondToWorkRequest(String responseAuthorUsr,
			Boolean responseDescriptor, int workRequestId)
			throws UnauthorizedRequestException {
		// TODO Auto-generated method stub
		
	}

	public void markRequestAsCompleted(String actorUsr, int workRequestId)
			throws UnauthorizedRequestException {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(String authorUsr, String text, int workRequestId)
			throws UnauthorizedRequestException {
		// TODO Auto-generated method stub
		
	}

}
