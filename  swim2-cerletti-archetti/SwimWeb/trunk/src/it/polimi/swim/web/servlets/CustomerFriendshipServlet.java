package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.FriendshipControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CustomerFriendshipServlet.
 */
public class CustomerFriendshipServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	public static final String CONTEXT_NAME = "friends";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerFriendshipServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */
		registerGetActionMapping(
				CustomerFriendshipSection.FRIENDS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(CustomerFriendshipSection.FRIENDS, req,
								resp);
					}
				});

		registerGetActionMapping(
				CustomerFriendshipSection.FRIENDSHIP_REQUESTS
						.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(
								CustomerFriendshipSection.FRIENDSHIP_REQUESTS,
								req, resp);
					}
				});

		/* POST request actions */
		registerPostActionMapping("accept", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				respondToFriendshipRequest(req, resp, true);
			}
		});

		registerPostActionMapping("decline", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				respondToFriendshipRequest(req, resp, false);
			}
		});

		registerPostActionMapping("remove", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				removeFriend(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void respondToFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp, Boolean response) throws IOException,
			ServletException {
		HttpSession session = req.getSession();
		int friendshipId;

		try {
			friendshipId = Integer.parseInt(req.getParameter("f"));
		} catch (NumberFormatException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (NullPointerException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		// Check login
		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		// Get logged user
		String loggedUsername = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);

		// Lookup bean
		FriendshipControllerRemote friendship = lookupBean(
				FriendshipControllerRemote.class, Misc.BeanNames.FRIENDSHIP);

		// Respond to friendship request
		try {
			friendship.respondToRequest(loggedUsername, friendshipId, response);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.INVALID_REQUEST);
			return;
		} catch (UnauthorizedRequestException e) {
			sendError(req, resp, ErrorType.UNAUTHORIZED_REQUEST);
			return;
		}

		// Show friend list page
		showSection(CustomerFriendshipSection.FRIENDSHIP_REQUESTS, req, resp);
	}

	private void removeFriend(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		HttpSession session = req.getSession();

		// Retrieve logged user
		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String selfUsername = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);

		// Retrieve friendship id
		int friendshipId;

		try {
			friendshipId = Integer.parseInt(req.getParameter("f"));
		} catch (Exception e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		// Lookup bean
		FriendshipControllerRemote friendship = lookupBean(
				FriendshipControllerRemote.class, Misc.BeanNames.FRIENDSHIP);

		// Remove friendship
		try {
			friendship.removeFriendship(selfUsername, friendshipId);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.INVALID_REQUEST);
			return;
		} catch (UnauthorizedRequestException e) {
			sendError(req, resp, ErrorType.UNAUTHORIZED_REQUEST);
			return;
		}

		// Show friend list page
		resp.sendRedirect(req.getContextPath() + "/friends/");
		return;
	}

	private void showSection(CustomerFriendshipSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}

		// Retrieve username
		String loggedUsername = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);

		// Retrieve bean
		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		List<?> friendList = null;

		switch (section) {
		case FRIENDS:
			friendList = profile.getConfirmedFriendshipList(loggedUsername);
			break;
		case FRIENDSHIP_REQUESTS:
			friendList = profile.getFriendshipRequest(loggedUsername);
			break;
		}

		req.setAttribute(Misc.FRIENDLIST_ATTR, friendList);

		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.FRIENDS);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.FRIENDS_JSP).forward(req, resp);
		return;
	}

	/* Enumerations */

	public enum CustomerFriendshipSection {
		FRIENDS("Amici", ""), FRIENDSHIP_REQUESTS("Richieste di amicizia",
				"requests");

		private String sectionName, sectionIdentifier;

		private CustomerFriendshipSection(String sectionName,
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
}