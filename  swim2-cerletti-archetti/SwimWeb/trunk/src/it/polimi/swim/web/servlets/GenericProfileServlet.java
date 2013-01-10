package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.FriendshipControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.entity.Feedback;
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

	/**
	 * GenericProfileSection is an enumeration useful to provide all the
	 * possible sections accessible from profile of an another user.
	 */
	public enum GenericProfileSection {
		PROFILE("Profilo", ""), FEEDBACKS("Feedback", "feedbacks"), FRIENDS(
				"Amici", "friends");

		private String sectionName, sectionIdentifier;

		private GenericProfileSection(String sectionName,
				String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the name of this
		 *         GenericProfileSection.
		 */
		public String getSectionName() {
			return sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the identifier of this
		 *         GenericProfileSection.
		 */
		public String getSectionIdentifier() {
			return sectionIdentifier;
		}
	}

	/**
	 * createWRField is an enumeration useful to manage compilation of all the
	 * fields present in a work request form accessible from the profile of a
	 * user we want to send a work request.
	 */
	public enum createWRField {
		SELECTED_ABILITY("Professionalit&agrave; richiesta", "selectedAbility",
				true), START_DATE("Data inizio (gg/mm/aa)", "startDate", true), START_HOUR(
				"Ora inizio (hh:mm)", "startHour", true), END_DATE(
				"Data fine (gg/mm/aa)", "endDate", false), END_HOUR(
				"Ora fine(hh:mm)", "endHour", false), LOCATION("Luogo",
				"location", true), DESCRIPTION("Descrizione", "description",
				true);

		private String labelText, name;
		private Boolean mandatory;

		private createWRField(String labelText, String name, Boolean mandatory) {
			this.labelText = labelText;
			this.name = name;
			this.mandatory = mandatory;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the name of this field to compile.
		 */
		public String getLabelText() {
			return labelText;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the value inserted for this field.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter method.
		 * 
		 * @return true if the compilation of this field is mandatory, false
		 *         otherwise.
		 */
		public Boolean isMandatory() {
			return mandatory;
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

		registerGetActionMapping("sendworkrequest", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showCreateWorkRequest(req, resp);
			}
		});

		/* POST request actions */
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

		/* Check user isn't asking to view its own profile */
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

		/* Check friendship status between users */
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

		/* Ability List */
		List<?> abilityList = profile.getAbilityList(targetUsername);
		req.setAttribute(Misc.ABILITY_LIST, abilityList);

		/* Feedback list */
		List<?> feedbackList = profile.getReceivedFeedbacks(targetUsername);
		req.setAttribute(Misc.FEEDBACK_LIST, feedbackList);

		/* Friend list */
		List<?> friendList = profile.getConfirmedFriendshipList(targetUsername);
		req.setAttribute(Misc.FRIENDLIST_ATTR, friendList);

		/* Compute feedback average */
		Integer average = null;

		if (feedbackList.size() > 0) {
			int sum = 0;
			for (Object o : feedbackList) {
				Feedback f = (Feedback) o;
				sum += f.getMark();
			}
			average = sum / feedbackList.size();
		}

		req.setAttribute(Misc.MARK_VALUE, average);

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

		List<?> abilityList = profile.getAbilityList(targetUsername);

		if (abilityList.size() == 0) {
			sendError(req, resp, ErrorType.USER_HAS_NO_ABILITIES);
			return;
		}

		req.setAttribute(Misc.ABILITY_LIST, abilityList);
		req.getRequestDispatcher(Misc.CREATE_WORKREQUEST_JSP)
				.forward(req, resp);
		return;
	}

	private void doSendFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();

		/* Check logged user */
		if (!isCustomerLoggedIn(session)) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.LOGIN_REQUIRED);
			req.getRequestDispatcher(Misc.ERROR_JSP).forward(req, resp);
			return;
		}

		String loggedUsername = getUsername(session);

		/* Retrieve target username from request */
		String targetUsername = req.getParameter(TARGET_USER_PARAM);

		if (Misc.isStringEmpty(targetUsername)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		/* Create frienship */
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

		/* Retrieve the username of the customer target of the request */
		String targetUsername = req.getParameter(TARGET_USER_PARAM);

		workRequestProperties.put("receiver", targetUsername);
		workRequestProperties.put("sender", getUsername(session));

		/* Initially parse request and retrieve all field values */
		for (createWRField field : createWRField.values()) {
			String name = field.getName();
			String value = (String) req.getParameter(name);
			if (field.isMandatory() && Misc.isStringEmpty(value)) {
				req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
				showCreateWorkRequest(req, resp);
				return;
			}

			workRequestProperties.put(name, value);
		}

		/* Correctly parse date */
		try {
			String startDate = (String) workRequestProperties.get("startDate");
			workRequestProperties.remove("startDate");
			String startTime = (String) workRequestProperties.get("startHour");
			workRequestProperties.remove("startHour");

			workRequestProperties.put("start", parseDate(startDate, startTime));

			String endDate = (String) workRequestProperties.get("startDate");
			workRequestProperties.remove("startDate");
			String endTime = (String) workRequestProperties.get("endHour");
			workRequestProperties.remove("endHour");

			if (!Misc.isStringEmpty(endDate) && !Misc.isStringEmpty(endTime)) {
				workRequestProperties.put("start",
						parseDate(startDate, startTime));
			}
		} catch (ParseException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.BAD_DATE);
			showCreateWorkRequest(req, resp);
			return;
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
			showCreateWorkRequest(req, resp);
			return;
		}

		CustomerWorkrequestServlet.redirectToRequestView(req, resp, id);
	}

	private Date parseDate(String date, String hour) throws ParseException {
		return Misc.DATE_TIME_FORMAT.parse(date + " " + hour);
	}
}
