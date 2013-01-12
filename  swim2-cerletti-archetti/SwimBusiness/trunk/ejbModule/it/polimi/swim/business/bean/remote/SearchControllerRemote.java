package it.polimi.swim.business.bean.remote;

import java.util.Map;

import it.polimi.swim.business.bean.QueryResult;
import it.polimi.swim.business.exceptions.BadRequestException;

import javax.ejb.Remote;

/**
 * SearchControllerRemote is an interface that manages the access to the
 * database in case of a user search request.
 */
@Remote
public interface SearchControllerRemote {

	/**
	 * This method is useful to manage a user research.
	 * 
	 * @param searchTerms
	 *            a Map that contains a set of identifier and the associated
	 *            values with whom we want to perform the research.
	 * @param friendRestrictUsr
	 *            a Boolean value that is true if the research has to be
	 *            performed only in the user friends.
	 * @return the corresponding QueryResult item.
	 * @throws BadRequestException
	 *             a request that does not fit.
	 */
	public QueryResult performQuery(Map<String, String> searchTerms,
			String friendRestrictUsr) throws BadRequestException;
}
