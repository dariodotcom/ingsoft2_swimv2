package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.MenuDescriptor;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.UnloggedMenu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GenericProfileServlet.
 */
public class GenericProfileServlet extends SwimServlet {

	private static final long serialVersionUID = 1852003714703658764L;

	public static final String CONTEXT_NAME = "user";

	private static final String TARGET_USER_PARAM = "u";

	public enum GenericProfileSection {
		PROFILE("Profilo", ""), FEEDBACKS("Feedback", "feedbacks"), FRIENDS(
				"Amici", "friends");

		private String sectionName, sectionIdentifier;

		private GenericProfileSection(String sectionName,
				String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		public String getSectionName() {
			return sectionName;
		}

		public String getSectionIdentifier() {
			return sectionIdentifier;
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenericProfileServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */

		registerGetActionMapping(
				GenericProfileSection.PROFILE.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(GenericProfileSection.PROFILE, req, resp);
					}
				});

		registerGetActionMapping(
				GenericProfileSection.FEEDBACKS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(GenericProfileSection.FEEDBACKS, req, resp);
					}
				});

		registerGetActionMapping(
				GenericProfileSection.FRIENDS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(GenericProfileSection.FRIENDS, req, resp);
					}
				});

		registerGetActionMapping("request", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showCreateRequestPage(req, resp);
			}
		});

		registerGetActionMapping("requestconfirm", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showRequestConfirm(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("sendworkrequest", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doSendWorkRequest(req, resp);
			}
		});

		registerPostActionMapping("sendfriendship", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doSendFriendshipRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void showSection(GenericProfileSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = req.getParameter(TARGET_USER_PARAM);

		if (Misc.isStringEmpty(username)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		// Check user isn't asking to view its own profile
		HttpSession session = req.getSession();
		String loggedUsername = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);

		if (username.equals(loggedUsername)) {
			resp.sendRedirect(req.getContextPath() + "/home/");
			return;
		}

		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		Customer c = profile.getByUsername(username);

		if (c == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		req.setAttribute(Misc.USER_TO_SHOW, c);
		MenuDescriptor selectedTab = (isUserLoggedIn(req.getSession()) ? CustomerMenu.SEARCH
				: UnloggedMenu.SEARCH);

		req.setAttribute(Misc.SELECTED_TAB_ATTR, selectedTab);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.USER_JSP).forward(req, resp);
	}

	private void doSendWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doSendFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void showCreateRequestPage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("sendworkrequest.jsp").forward(req, resp);
	}

	private void showRequestConfirm(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("sendworkrequest.jsp").forward(req, resp);
	}
}
