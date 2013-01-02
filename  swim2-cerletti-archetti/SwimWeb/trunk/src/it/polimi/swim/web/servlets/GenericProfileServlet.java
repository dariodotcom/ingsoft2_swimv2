package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.FriendshipControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
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
import java.text.ParseException;
import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		/* POST request actions */

		registerPostActionMapping("sendworkrequest", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showCreateWorkRequest(req, resp);
			}
		});

		registerPostActionMapping("sendfriendship", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doSendFriendshipRequest(req, resp);
			}
		});

		registerPostActionMapping("createWorkrequest", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doCreateWorkRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */
	private void showSection(GenericProfileSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String targetUsername = req.getParameter(TARGET_USER_PARAM);

		if (Misc.isStringEmpty(targetUsername)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} else {
			targetUsername = targetUsername.toLowerCase();
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

	private void showCreateWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isUserLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String targetUsername = req.getParameter(TARGET_USER_PARAM);

		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		Customer targetCustomer = profile.getByUsername(targetUsername);
		req.setAttribute(Misc.USER_TO_SHOW, targetCustomer);

		List<?> abilityList;
		try {
			abilityList = profile.getAbilityList(targetUsername);
			req.setAttribute(Misc.ABILITY_LIST, abilityList);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		req.getRequestDispatcher(Misc.CREATE_WORKREQUEST_JSP)
				.forward(req, resp);
		return;
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

	private void doCreateWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		Map<String, Object> workRequestProperties = new HashMap<String, Object>();

		// Retrieve the username of the customer target of the request
		String targetUsername = req.getParameter("target");

		workRequestProperties.put("receiver", targetUsername);
		workRequestProperties.put("sender", getUsername(session));

		// Pust target in request in case we forward to create JSP
		Customer c = lookupBean(UserProfileControllerRemote.class,
				Misc.BeanNames.PROFILE).getByUsername(targetUsername);
		req.setAttribute(Misc.USER_TO_SHOW, c);

		String[] fieldNames = { "abilitySelection", "startDate", "startTime",
				"endDate", "endTime", "location", "description" };

		String[] mandatoryFieldsNames = { "abilitySelection", "startDate",
				"startTime", "location", "description" };

		// Put receiver customer in request scope in case we forward the to
		// create jsp again

		// Initially parse request and retrieve all field values
		for (String name : fieldNames) {
			String value = (String) req.getParameter(name);
			if (Arrays.asList(mandatoryFieldsNames).contains(name)
					&& Misc.isStringEmpty(value)) {
				req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
				req.getRequestDispatcher(Misc.CREATE_WORKREQUEST_JSP).forward(
						req, resp);
			}

			workRequestProperties.put(name, value);
		}

		// Correctly parse date
		try {
			String startDate = (String) workRequestProperties.get("startDate");
			workRequestProperties.remove("startDate");
			String startTime = (String) workRequestProperties.get("startTime");
			workRequestProperties.remove("startTime");

			workRequestProperties.put("start", parseDate(startDate, startTime));

			String endDate = (String) workRequestProperties.get("startDate");
			workRequestProperties.remove("startDate");
			String endTime = (String) workRequestProperties.get("startTime");
			workRequestProperties.remove("startTime");

			if (!Misc.isStringEmpty(endDate) && !Misc.isStringEmpty(endTime)) {
				workRequestProperties.put("start",
						parseDate(startDate, startTime));
			}
		} catch (ParseException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.BAD_DATE);
			req.getRequestDispatcher(Misc.CREATE_WORKREQUEST_JSP).forward(req,
					resp);
		}

		WorkRequestControllerRemote workRequestCtrl = lookupBean(
				WorkRequestControllerRemote.class, Misc.BeanNames.WORKREQUEST);

		int id;

		try {
			id = workRequestCtrl.createWorkRequest(workRequestProperties);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			req.setAttribute(Misc.ERROR_ATTR,
					ErrorType.INVALID_ABILITY_SELECTION);
			req.getRequestDispatcher(Misc.CREATE_WORKREQUEST_JSP).forward(req,
					resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/works/view?id=" + id);
	}

	private Date parseDate(String date, String hour) throws ParseException {
		return Misc.DATE_TIME_FORMAT.parse(date + " " + hour);
	}
}
