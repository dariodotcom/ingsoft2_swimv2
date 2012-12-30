package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.UsernameAlreadyTakenException;

import javax.ejb.Remote;

/**
 * AuthenticationControllerRemote is an interface that manages the access to the
 * database in case of an authentication request.
 */
@Remote
public interface AuthenticationControllerRemote {

	/**
	 * This method is useful to provide informations about the type of the
	 * authenticated user.
	 * 
	 * @param username
	 *            a String that contains the username of the user who is
	 *            authenticated.
	 * @param password
	 *            a String that contains the password given by the user who is
	 *            authenticated.
	 * @return the UserType (customer or administrator) of the user who is
	 *         authenticated.
	 * @throws AuthenticationFailedException
	 *             the authentication request isn't successful.
	 */
	public UserType authenticateUser(String username, String password)
			throws AuthenticationFailedException;

	/**
	 * This method is useful to add a new registered user to the database.
	 * 
	 * @param username
	 *            a String that contains the username of the new user.
	 * @param password
	 *            a String that contains the password of the new user.
	 * @param email
	 *            a String that contains the e-mail given by the new user.
	 * @param name
	 *            a String that contains the name of the new user.
	 * @param surname
	 *            a String that contains the surname of the new user.
	 * @throws UsernameAlreadyTakenException
	 *             the username chosen by the new user is already in use.
	 * @throws EmailAlreadyTakenException
	 *             the e-mail address given by the user is already in use.
	 */
	// TODO aggiungere eccezione EmailPathIncompatible se la mail inserita non
	// rispetta il path, e modificare poi il javadoc!
	public void createUser(String username, String password, String email,
			String name, String surname) throws UsernameAlreadyTakenException,
			EmailAlreadyTakenException;

	// TODO Metodo public void confirmUserEmailAddress(String username) presente
	// in AuthenticationController.

	// TODO Metodo public String resetUserPassword(String username) presente in
	// AuthenticationController.
}
