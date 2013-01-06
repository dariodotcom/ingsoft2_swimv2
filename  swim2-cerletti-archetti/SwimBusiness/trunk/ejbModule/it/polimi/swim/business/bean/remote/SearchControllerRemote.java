package it.polimi.swim.business.bean.remote;

import java.util.Map;

import it.polimi.swim.business.bean.QueryResult;
import it.polimi.swim.business.exceptions.BadRequestException;

import javax.ejb.Remote;

@Remote
public interface SearchControllerRemote {

	public QueryResult performQuery(Map<String, String> searchTerms,
			String friendRestrictUsr) throws BadRequestException;
}
