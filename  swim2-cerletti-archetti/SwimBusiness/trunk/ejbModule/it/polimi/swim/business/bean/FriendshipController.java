package it.polimi.swim.business.bean;

import java.util.Date;

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
				senderUsr);
		Customer receiver = Helpers.getEntityChecked(manager, Customer.class,
				receiverUsr);

		if (sender.getFriends().contains(receiver)) {
			// Users are already friends
			throw new InvalidStateException();
		}

		Friendship f = new Friendship(sender, receiver);
		manager.persist(f);
	}

	public void respondToRequest(String responseAuthorUsr, int requestId,
			boolean accepted) throws BadRequestException,
			InvalidStateException, UnauthorizedRequestException {
		Customer responseAuthor = Helpers.getEntityChecked(manager,
				Customer.class, responseAuthorUsr);
		Friendship friendship = Helpers.getEntityChecked(manager,
				Friendship.class, requestId);

		if (!friendship.getReceiver().equals(responseAuthor)) {
			throw new UnauthorizedRequestException();
		}

		if (friendship.isConfirmed()) {
			throw new InvalidStateException();
		}

		if (accepted == true) {
			friendship.setConfirmed();
			friendship.setConfirmedDate(new Date());
		} else {
			manager.remove(friendship);
		}
	}

	public void removeFriendship(String requestAuthorUsr, int requestId)
			throws BadRequestException, InvalidStateException, UnauthorizedRequestException {
		Friendship friendship = Helpers.getEntityChecked(manager,
				Friendship.class, requestId);
		Customer author = Helpers.getEntityChecked(manager, Customer.class,
				requestAuthorUsr);
		
		if(!(author.equals(friendship.getSender()) || author.equals(friendship.getReceiver()))){
			throw new UnauthorizedRequestException();
		}
		
		manager.remove(friendship);
	}
}
