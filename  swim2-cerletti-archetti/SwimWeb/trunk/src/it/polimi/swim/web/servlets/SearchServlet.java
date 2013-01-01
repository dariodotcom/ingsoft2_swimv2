package it.polimi.swim.web.servlets;

import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.MenuDescriptor;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.UnloggedMenu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		registerGetActionMapping("results", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showSearchResultPage(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("/perform", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doPerformSearch(req, resp);
			}
		});

	}

	/* Methods to respond to different requests */

	private void doPerformSearch(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void showSearchPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		MenuDescriptor selectedTab = (isCustomerLoggedIn(req.getSession()) ? CustomerMenu.SEARCH
				: UnloggedMenu.SEARCH);

		req.setAttribute(Misc.SELECTED_TAB_ATTR, selectedTab);
		req.getRequestDispatcher(Misc.SEARCH_JSP).forward(req, resp);
	}

	private void showSearchResultPage(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher(Misc.SEARCH_JSP).forward(req, resp);
	}
}
