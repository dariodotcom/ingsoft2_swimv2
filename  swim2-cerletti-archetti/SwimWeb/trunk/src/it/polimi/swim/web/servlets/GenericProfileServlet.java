package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.FriendshipControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.MenuDescriptor;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.Misc.FriendshipStatus;
import it.polimi.swim.web.pagesupport.NotificationMessages;
import it.polimi.swim.web.pagesupport.UnloggedMenu;

import java.io.IOException;
import java.util.List;

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
					HttpServletResponse resp) throws IOException,
					ServletException {
				doSendFriendshipRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void showSection(GenericProfileSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String targetUsername = req.getParameter(TARGET_USER_PARAM)
				.toLowerCase();

		if (Misc.isStringEmpty(targetUsername)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		// Check user isn't asking to view its own profile
		HttpSession session = req.getSession();
		String loggedUsername = getUsername(session);

		if (targetUsername.equals(loggedUsername)) {
			resp.sendRedirect(req.getContextPath() + "/home/");
			return;
		}

		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		Customer targetCustomer = profile.getByUsername(targetUsername);

		if (targetCustomer == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		// Check friendship status between users
		FriendshipStatus status;

		if (Misc.isStringEmpty(loggedUsername)) {
			status = FriendshipStatus.FRIENDSHIP_UNAVAILABLE;
		} else {
			if (profile
					.canSendFriendshipRequest(loggedUsername, targetUsername)) {
				status = FriendshipStatus.NOT_FRIENDS;
			} else if (profile.areFriends(loggedUsername, targetUsername)) {
				status = FriendshipStatus.ALREADY_FRIENDS;
			} else {
				status = FriendshipStatus.CONFIRMATION_AWAITED;
			}
		}

		req.setAttribute(Misc.FRIENDSHIP_STATUS, status);

		// Friend list
		List<?> friendList = profile.getConfirmedFriendshipList(targetUsername);
		req.setAttribute(Misc.FRIENDLIST_ATTR, friendList);

		req.setAttribute(Misc.USER_TO_SHOW, targetCustomer);
		MenuDescriptor selectedTab = (isCustomerLoggedIn(req.getSession()) ? CustomerMenu.SEARCH
				: UnloggedMenu.SEARCH);

		req.setAttribute(Misc.SELECTED_TAB_ATTR, selectedTab);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.USER_JSP).forward(req, resp);
	}

	private void doSendWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doSendFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();

		// Check logged user
		if (!isCustomerLoggedIn(session)) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.LOGIN_REQUIRED);
			req.getRequestDispatcher(Misc.ERROR_JSP).forward(req, resp);
			return;
		}

		String loggedUsername = getUsername(session);

		// Retrieve target username from request
		String targetUsername = req.getParameter(TARGET_USER_PARAM);

		if (Misc.isStringEmpty(targetUsername)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		// Create frienship
		FriendshipControllerRemote frController = lookupBean(
				FriendshipControllerRemote.class, Misc.BeanNames.FRIENDSHIP);

		try {
			frController.addFriendshipRequest(loggedUsername, targetUsername);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.INVALID_REQUEST);
			return;
		}

		req.setAttribute(Misc.NOTIFICATION_ATTR,
				NotificationMessages.REQUEST_SENT);
		showSection(GenericProfileSection.PROFILE, req, resp);
	}

	private void showCreateRequestPage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("sendworkrequest.jsp").forward(req, resp);
	}

	private void showRequestConfirm(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("sendworkrequest.jsp").forward(req, resp);
	}

	// Helper

}
