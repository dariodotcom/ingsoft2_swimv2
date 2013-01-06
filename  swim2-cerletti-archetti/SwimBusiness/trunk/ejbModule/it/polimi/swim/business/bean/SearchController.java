package it.polimi.swim.business.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.polimi.swim.business.bean.remote.SearchControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.BadRequestException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class SearchController
 */
@Stateless
public class SearchController implements SearchControllerRemote {

	@PersistenceContext(unitName = "swim")
	EntityManager manager;

	/**
	 * Default constructor.
	 */
	public SearchController() {
		// TODO Auto-generated constructor stub
	}

	public QueryResult performQuery(Map<String, String> searchTerms,
			String friendRestrictUsr) throws BadRequestException {
		List<?> resultList;

		// Create query
		Query q = buildQuery(searchTerms);

		// Perform query
		Date queryStart = new Date();
		resultList = q.getResultList();
		Date queryEnd = new Date();

		long duration = queryEnd.getTime() - queryStart.getTime();

		if (friendRestrictUsr != null) {
			Customer c = Helpers.getEntityChecked(manager, Customer.class,
					friendRestrictUsr);
			resultList.retainAll(c.getFriends());
		}

		return new QueryResult(resultList, duration);
	}

	private Query buildQuery(Map<String, String> searchTerms)
			throws BadRequestException {

		Map<String, String> clauses = new HashMap<String, String>();

		if (!searchTerms.keySet().contains("ability")) {
			throw new BadRequestException();
		}

		clauses.put("name", "c.name=:name");
		clauses.put("surname", "c.surname=:surname");
		clauses.put("location", "c.location=:location");
		clauses.put("ability", "a.name=:ability");

		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT c FROM Customer c");
		queryString.append(", IN (c.declaredAbilities) a");

		queryString.append(" WHERE ");

		Iterator<String> i = searchTerms.keySet().iterator();

		while (i.hasNext()) {
			String termName = i.next();
			queryString.append(clauses.get(termName));
			if (i.hasNext()) {
				queryString.append(" AND ");
			}
		}

		Query q = manager.createQuery(queryString.toString());

		// Set parameters
		for (String key : searchTerms.keySet()) {
			q.setParameter(key, searchTerms.get(key));
		}

		return q;
	}
}