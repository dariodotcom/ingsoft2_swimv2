package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.QueryResult;
import it.polimi.swim.business.bean.remote.SearchControllerRemote;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.MenuDescriptor;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.UnloggedMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchServlet.
 */
public class SearchServlet extends SwimServlet {

	private static final long serialVersionUID = -3243041247776896867L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();

		setSectionName("search");

		/* GET request actions */

		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showSearchPage(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("perform", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws ServletException,
					IOException {
				doPerformSearch(req, resp);
			}
		});

	}

	/* Methods to respond to different requests */
	
	private void doPerformSearch(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		Map<String, String> searchTerms = new HashMap<String, String>();

		for (SearchField field : SearchField.values()) {
			String name = field.getName();
			String value = req.getParameter(name);
			Boolean isEmpty = Misc.isStringEmpty(value);

			if (isEmpty && field.isMandatory()) {
				req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
				req.getRequestDispatcher(Misc.SEARCH_JSP).forward(req, resp);
				return;
			} else if (!isEmpty) {
				searchTerms.put(name, value);
			}
		}

		SearchControllerRemote searchCtrl = lookupBean(
				SearchControllerRemote.class, Misc.BeanNames.SEARCH);

		QueryResult result;

		String username = null;
		HttpSession session = req.getSession();

		if (Boolean.valueOf(req.getParameter("restrict"))) {
			if (!isCustomerLoggedIn(session)) {
				sendError(req, resp, ErrorType.BAD_REQUEST);
				return;
			}
			username = getUsername(session);
		}

		try {
			result = searchCtrl.performQuery(searchTerms, username);
		} catch (BadRequestException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.BAD_ABILITY_NAME);
			req.getRequestDispatcher(Misc.SEARCH_JSP).forward(req, resp);
			return;
		}

		req.setAttribute(Misc.SEARCH_RESULTS, result);
		showSearchPage(req, resp);
	}

	private void showSearchPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		MenuDescriptor selectedTab = (isCustomerLoggedIn(req.getSession()) ? CustomerMenu.SEARCH
				: UnloggedMenu.SEARCH);

		req.setAttribute(Misc.SELECTED_TAB_ATTR, selectedTab);
		req.getRequestDispatcher(Misc.SEARCH_JSP).forward(req, resp);
	}

	private enum SearchField {

		NAME("name", false), SURNAME("surname", false), LOCATION("location",
				false), ABILITY("ability", true);

		String name;
		Boolean mandatory;

		private SearchField(String name, Boolean mandatory) {
			this.name = name;
			this.mandatory = mandatory;
		}

		public String getName() {
			return name;
		}

		public Boolean isMandatory() {
			return mandatory;
		}

	}
}
