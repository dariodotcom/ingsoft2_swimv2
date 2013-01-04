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
 * Session Bean implementation class FriendshipController.
 */
@Stateless
public class FriendshipController implements FriendshipControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public FriendshipController() {
	}

	/**
	 * @see FriendshipControllerRemote
	 */
	public void addFriendshipRequest(String senderUsr, String receiverUsr)
			throws BadRequestException, InvalidStateException {
		Customer sender = Helpers.getEntityChecked(manager, Customer.class,
				senderUsr);
		Customer receiver = Helpers.getEntityChecked(manager, Customer.class,
				receiverUsr);

		/* Users are already friends */
		if (!sender.canBeSentFRBy(receiver)) {
			throw new InvalidStateException();
		}

		Friendship f = new Friendship(sender, receiver);
		manager.persist(f);
	}

	/**
	 * @see FriendshipControllerRemote
	 */
	public void respondToRequest(String responseAuthorUsr, int requestId,
			boolean accepted) throws BadRequestException,
			InvalidStateException, UnauthorizedRequestException {

		Friendship friendship = manager.find(Friendship.class, requestId);
		Customer responseAuthor = Helpers.getEntityChecked(manager,
				Customer.class, responseAuthorUsr);

		if (!friendship.getReceiver().equals(responseAuthor)) {
			System.out.println(friendship.getReceiver().getUsername() + " "
					+ responseAuthorUsr);
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

	/**
	 * @see FriendshipControllerRemote
	 */
	public void removeFriendship(String requestAuthorUsr, int requestId)
			throws BadRequestException, InvalidStateException,
			UnauthorizedRequestException {
		Friendship friendship = Helpers.getEntityChecked(manager,
				Friendship.class, requestId);
		Customer author = Helpers.getEntityChecked(manager, Customer.class,
				requestAuthorUsr);

		Boolean isSender = author.equals(friendship.getSender()), isReceiver = author
				.equals(friendship.getReceiver());

		if (friendship.isConfirmed() && !(isSender || isReceiver)) {
			throw new UnauthorizedRequestException();
		}

		if (!friendship.isConfirmed() && !isSender) {
			throw new UnauthorizedRequestException();
		}

		manager.remove(friendship);
	}
}
