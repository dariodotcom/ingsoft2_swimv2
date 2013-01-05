package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UserNotFoundException;
import it.polimi.swim.business.exceptions.UsernameAlreadyTakenException;

import javax.ejb.Remote;

/**
 * AuthenticationControllerRemote is an interface that manages the access to the
 * database in case of an authentication request.
 */
@Remote
public interface AuthenticationControllerRemote {

	/**
	 * This method provides informations about the type of the authenticated
	 * user (customer or administrator).
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
	 * This method manages the adding of a new registered user to the database.
	 * 
	 * @param username
	 *            a String that contains the username of the new user.
	 * @param password
	 *            a String that contains the password of the new user.
	 * @param email
	 *            a String that contains the e-mail of the new user.
	 * @param name
	 *            a String that contains the name of the new user.
	 * @param surname
	 *            a String that contains the surname of the new user.
	 * @throws UsernameAlreadyTakenException
	 *             the username chosen by the new user is already in use.
	 * @throws EmailAlreadyTakenException
	 *             the e-mail address given by the user is already in use.
	 */
	// TODO for the future: Add an exception EmailPathIncompatible if the
	// inserted email path doesn't match with a standard one. N.B. After that
	// remember to modify JavaDoc!
	public void createUser(String username, String password, String email,
			String name, String surname) throws UsernameAlreadyTakenException,
			EmailAlreadyTakenException;

	/**
	 * This method manages the adding of a new administrator to the database.
	 * 
	 * @param username
	 *            a String that contains the username of the new administrator.
	 * @param password
	 *            a String that contains the password of the new administrator.
	 * @throws UsernameAlreadyTakenException
	 *             the username chosen by the administrator is already in use.
	 */
	public void createAdministrator(String username, String password)
			throws UsernameAlreadyTakenException;

	/**
	 * This method retrieves the key of an email validation request made by a
	 * given user if there are no errors.
	 * 
	 * @param authorUsr
	 *            a String that contains the username of the Customer that made
	 *            the request.
	 * @return a String that contains the key value of the email validation
	 *         request.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public String createEmailValidationRequest(String authorUsr)
			throws BadRequestException, InvalidStateException;

	/**
	 * This method manages the confirmation of a user email address.
	 * 
	 * @param emailValidationKey
	 *            the mail validation request key.
	 * @throws UserNotFoundException
	 *             the user has not been found.
	 */
	public void validateCustomerEmail(String emailValidationKey)
			throws BadRequestException, InvalidStateException;

	/**
	 * This method retrieves the key of a password reset request made by a given
	 * user if there are no errors.
	 * 
	 * @param authorUsr
	 *            a String that contains the username of the Customer that made
	 *            the request.
	 * @return a String that contains the key value of the password reset
	 *         request.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public String createPasswordResetRequest(String authorUsr)
			throws BadRequestException;

	/**
	 * This method manages the reset password request made by a user.
	 * 
	 * @param reqId
	 *            a String that is the key of a Password Reset request
	 * @return a String which contains the user new password.
	 * @throws UserNotFoundException
	 *             the user has not been found.
	 */
	public String resetCustomerPassword(String reqId)
			throws BadRequestException;

	/**
	 * This method retrieves the email associated to a user that made a password
	 * reset request.
	 * 
	 * @param reqId
	 *            a String that contains the identifier of the password reset
	 *            request.
	 * @return the email of the author of the password reset request.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public String getPasswordResetEmail(String reqId)
			throws BadRequestException;
}
