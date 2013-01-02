package it.polimi.swim.business.bean.remote;

import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;

import java.util.List;

import javax.ejb.Remote;

/**
 * AbilityControllerRemote is an interface that manages the access to the
 * database in case of an ability request.
 */
@Remote
public interface AbilityControllerRemote {

	/**
	 * This method is useful for adding an ability request to the database.
	 * 
	 * @param author
	 *            a String that contains the username of the customer that
	 *            requires the addition of the ability to the set.
	 * @param name
	 *            a String that contains the name of the suggested ability.
	 * @param description
	 *            a String that contains a brief description of the suggested
	 *            ability.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database.
	 */
	public void addAbilityRequest(String author, String name, String description)
			throws BadRequestException, InvalidStateException;

	/**
	 * This method is useful for adding a response to an ability request in the
	 * database.
	 * 
	 * @param administratorUsr
	 *            a String that contains the username of the administrator that
	 *            provides the review.
	 * @param requestId
	 *            an Integer that contains the identifier of the associated
	 *            ability request.
	 * @param response
	 *            a Boolean value that is true if the ability request was
	 *            successful, false otherwise.
	 * @param comment
	 *            a String that contains a brief comment given by the
	 *            administrator to justify the response to the ability request.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a request that is incompatible with the status of the
	 *             database
	 */
	public void reviewAbilityRequest(String administratorUsr, int requestId,
			Boolean response, String comment) throws BadRequestException,
			InvalidStateException;

	/**
	 * This method is useful to manage the adding of a new ability to the
	 * predefined set.
	 * 
	 * @param administratorUsr
	 *            a String that contains the username of the administrator that
	 *            provides the adding.
	 * @param name
	 *            a String that contains the name given to the new aility.
	 * @param description
	 *            a String that contains a brief description of the added
	 *            ability.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 * @throws InvalidStateException
	 *             a. request that is incompatible with the status of the
	 *             database
	 */
	public void addNewAbility(String administratorUsr, String name,
			String description) throws BadRequestException,
			InvalidStateException;

	/**
	 * This method is useful to provide a list of the ability already present in
	 * the set.
	 * 
	 * @return a List of the ability already present in the set.
	 */
	public List<?> getAvailableAbilityList();
	
	public List<?> getAvailableAbilityList(String match);

	/**
	 * This method is useful to provide a list of the ability request performed
	 * by users.
	 * 
	 * @return a List of the ability request.
	 */
	public List<?> getAbilityRequestList();
}
