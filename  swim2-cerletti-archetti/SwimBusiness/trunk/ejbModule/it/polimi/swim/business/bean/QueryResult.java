package it.polimi.swim.business.bean;

import java.io.Serializable;
import java.util.List;

/**
 * QueryResult is a class useful to manage the data given by a query from a user
 * to the system.
 */
public class QueryResult implements Serializable {

	private static final long serialVersionUID = 2659751500711505285L;

	private List<?> resultList;
	private long queryDuration;

	/**
	 * Class constructor.
	 * 
	 * @param resultList
	 *            a List of object that are the values returned by a query.
	 * @param queryDuration
	 *            a Long that indicates he time that the system has taken to
	 *            provide the results of the query.
	 */
	public QueryResult(List<?> resultList, long queryDuration) {
		super();
		this.resultList = resultList;
		this.queryDuration = queryDuration;
	}

	/**
	 * Getter method.
	 * 
	 * @return a List of object that are the values returned by a query.
	 */
	public List<?> getResultList() {
		return resultList;
	}

	/**
	 * Getter method.
	 * 
	 * @return a Long that indicates he time that the system has taken to
	 *         provide the results of the query.
	 */
	public long getQueryDuration() {
		return queryDuration;
	}
}
