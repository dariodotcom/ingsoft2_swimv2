package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Friendship;

import java.util.List;

import javax.ejb.Remote;

/**
 * UserProfileControllerRemote is an interface that manages the users database.
 * 
 */
@Remote
public interface UserProfileControllerRemote {
	public Customer getByUsername(String username);
	
	/**
	 * 
	 * @return
	 */
	public List<Customer> getFriendList();

	/**
	 * Getter method useful to provide the list of friendship requests involving
	 * a user.
	 * 
	 * @return a List of Friendship that are present in a user friendship
	 *         request list.
	 */
	public List<Friendship> getFriendshipRequest();

	/**
	 * Getter method useful to provide the list of friendship requests involving
	 * a given user.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know received frienship requests about.
	 * @return the List of the user received friendship requests.
	 */
	public List<?> getFriendshipRequest(String username);

	/**
	 * Getter method useful to provide the list of feedbacks sent by an user.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know the sent feedbacks.
	 * @return the List of the user sent feedbacks.
	 */
	public List<?> getSentFeedbacks(String username);

	/**
	 * Getter method useful to provide the list of feedback received by an user.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know the sent feedbacks.
	 * @return the List of the user received feedbacks.
	 */
	public List<?> getReceivedFeedacks(String username);
}
