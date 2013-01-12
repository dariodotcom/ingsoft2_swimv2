package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.helpers.SerializableImage;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * UserProfileControllerRemote is an interface that manages the users database.
 * 
 */
@Remote
public interface UserProfileControllerRemote {

	/**
	 * Getter method that provides the user profile associated to a given
	 * username.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            retrieve the profile.
	 * @return the Customer associated to the given username.
	 */
	public Customer getByUsername(String username);

	/**
	 * Getter method that provides the list of friendships involving a given
	 * user.
	 * 
	 * @return the List of the user friendships.
	 */
	public List<?> getConfirmedFriendshipList(String username);

	/**
	 * Getter method that provides the list of friendship requests, not yet
	 * confirmed, involving a given user.
	 * 
	 * @return the List of Friendship not yet confirmed that are present in a
	 *         user friendship request list.
	 */
	public List<?> getFriendshipRequest(String username);

	/**
	 * Getter method that provides a list of feedbacks provided by a given user.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know the sent feedback.
	 * @return a List of feedbacks provided by the given user.
	 */
	public List<?> getSentFeedbacks(String username);

	/**
	 * Getter method that provides a list of abilities declared by a given user.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know the declared abilities.
	 * @return a List of abilities declared by a user.
	 */
	public List<?> getAbilityList(String username);

	/**
	 * Getter method useful to provide the list of feedback received by an user.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know the received feedbacks.
	 * @return the List of the user received feedbacks.
	 */
	public List<?> getReceivedFeedbacks(String username);

	/**
	 * Getter method that provides a list of work requests received by a user
	 * that are still in course.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know a list of the active received work requests.
	 * @return a List of active work requests received by a user.
	 */
	public List<?> getReceivedActiveWorkRequest(String username);

	/**
	 * Getter method that provides a list of work requests sent by a user that
	 * are still in course.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know a list of the active sent work requests.
	 * @return a List of active work requests sent by a user.
	 */
	public List<?> getSentActiveWorkRequest(String username);

	/**
	 * Getter method that provides a list of work requests received by a user
	 * that are concluded.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know a list of the concluded received work requests.
	 * @return a List of concluded work requests received by a user.
	 */
	public List<?> getReceivedArchivedWorkRequest(String username);

	/**
	 * Getter method that provides a list of work requests sent by a user that
	 * are concluded.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know a list of the concluded sent work requests.
	 * @return a List of concluded work requests sent by a user.
	 */
	public List<?> getSentArchivedWorkRequest(String username);

	/**
	 * Getter method that provides the list of ability request sent by an user
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            know a list of the sent ability requests
	 * @return a List of sent ability reuests
	 */
	public List<?> getSentAbilityRequest(String username);

	/**
	 * This method manages the update of a set (one or more) user details, i.e.
	 * name, surname, birth date, location.
	 * 
	 * @param username
	 *            a String that contains the username of the user we want to
	 *            update the details.
	 * @param values
	 *            a Map that contains a set of the parameters with the
	 *            respective new values to update.
	 */
	public void updateCustomerDetails(String username,
			Map<String, Object> values);

	/**
	 * This method manages the adding of a user ability declaration.
	 * 
	 * @param username
	 *            a String that contains the username of the user that wants to
	 *            add an ability to the declared ones.
	 * @param abilityName
	 *            a String that contains the name of the ability the user want
	 *            to declare.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void addAbility(String username, String abilityName)
			throws BadRequestException, InvalidStateException;

	/**
	 * This method manages the removal of a user ability declaration.
	 * 
	 * @param username
	 *            a String that contains the username of the user that wants to
	 *            remove an ability to the declared ones.
	 * @param abilityName
	 *            a String that contains the name of the ability the user want
	 *            to remove.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void removeAbility(String username, String abilityName)
			throws BadRequestException, InvalidStateException;

	/**
	 * This method manages the changing of a user password.
	 * 
	 * @param username
	 *            a String that contains the username of the user that wants to
	 *            change his password.
	 * @param password
	 *            a String that contains the user new password.
	 */
	public void changePassword(String username, String password);

	/**
	 * This method manages the changing of a user email address.
	 * 
	 * @param username
	 *            a String that contains the username of the user that wants to
	 *            change his email address.
	 * @param email
	 *            a String that contains the user new email.
	 * @throws EmailAlreadyTakenException
	 *             the email is already in use.
	 */
	public void changeEmail(String username, String email)
			throws EmailAlreadyTakenException;

	/**
	 * This method provides informations whether is possible to send a
	 * friendship from a user to another one.
	 * 
	 * @param u1
	 *            a String that contains the username of the first user.
	 * @param u2
	 *            a String that contains the username of the second user.
	 * @return true if u1 and u2 are distinct users and they are not friends
	 *         yet, false otherwise.
	 */
	public Boolean canSendFriendshipRequest(String u1, String u2);

	/**
	 * This method provides informations whether two users are already friends
	 * or not.
	 * 
	 * @param username1
	 *            a String that contains the username of the first user.
	 * @param username2
	 *            a Strinf that contains the username of the second user.
	 * @return true if there is a confirmed friendship relationship between the
	 *         two users, false otherwise.
	 */
	public Boolean areFriends(String username1, String username2);

	/**
	 * This method manages the change of a user profile picture.
	 * 
	 * @param username
	 *            a String that contains the username of the user who wants to
	 *            change his picture.
	 * @param photo
	 *            a SerializableImage which contains the new picture.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public void changeCustomerPhoto(String username, SerializableImage photo)
			throws BadRequestException;
}
