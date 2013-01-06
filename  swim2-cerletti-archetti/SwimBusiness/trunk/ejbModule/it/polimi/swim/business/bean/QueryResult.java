package it.polimi.swim.business.bean;

import java.io.Serializable;
import java.util.List;

public class QueryResult implements Serializable{

	private static final long serialVersionUID = 2659751500711505285L;
	
	private List<?> resultList;
	private long queryDuration;

	public QueryResult(List<?> resultList, long queryDuration) {
		super();
		this.resultList = resultList;
		this.queryDuration = queryDuration;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public long getQueryDuration() {
		return queryDuration;
	}
}
