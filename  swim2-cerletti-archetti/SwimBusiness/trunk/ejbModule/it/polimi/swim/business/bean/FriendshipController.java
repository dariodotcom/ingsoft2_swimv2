package it.polimi.swim.business.bean;

import it.polimi.swim.business.bean.remote.FriendshipControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Friendship;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class FriendshipController
 */
@Stateless
public class FriendshipController implements FriendshipControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public FriendshipController() {
		// TODO Auto-generated constructor stub
	}

	public void addFriendshipRequest(String senderUsr, String receiverUsr)
			throws BadRequestException, InvalidStateException {
		Customer sender = Helpers.getEntityChecked(manager, Customer.class,
				senderUsr), receiver = Helpers.getEntityChecked(manager,
				Customer.class, receiverUsr);

	}

	public void respondToRequest(String responseAuthorUsr, int requestId,
			Boolean response) throws BadRequestException, InvalidStateException, UnauthorizedRequestException {
		Customer author = Helpers.getEntityChecked(manager, Customer.class,
				responseAuthorUsr);
		Friendship friendship = Helpers.getEntityChecked(manager,
				Friendship.class, requestId);
		
		if(response == null){
			throw new BadRequestException();
		}
		
		if(!friendship.getReceiver().equals(author)){
			throw new UnauthorizedRequestException();
		}
		
		if(friendship.getConfirmed() != null){
		}
		

	}

	public void removeFriendship(String requestAuthorUsr, int requestId)
			throws BadRequestException, InvalidStateException {
		// TODO Auto-generated method stub

	}

}
