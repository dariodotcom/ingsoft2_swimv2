package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.AbilityControllerRemote;
import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Ability;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.NotificationMessages;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Arrays;
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
 * Servlet implementation class PersonalPageServlet.
 */
public class PersonalPageServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	public static final String CONTEXT_NAME = "home";
	public static final String USER_ATTR = "user";

	/**
	 * PersonalPageSection is an enumeration useful to provide all the possible
	 * sections accessible from the home page of a logged user.
	 */
	public enum PersonalPageSection {
		HOME("Il tuo profilo", ""), EDIT_PROFILE("Modifica profilo",
				"editProfile"), EDIT_ACCOUNT("Modifica account", "editAccount");

		private String sectionName, sectionIdentifier;

		private PersonalPageSection(String sectionName, String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the name of this PersonalPageSection.
		 */
		public String getSectionName() {
			return sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the identifier of this
		 *         PersonalPageSection.
		 */
		public String getSectionIdentifier() {
			return sectionIdentifier;
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersonalPageServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */

		registerGetActionMapping(
				PersonalPageSection.HOME.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(PersonalPageSection.HOME, req, resp);
					}
				});

		registerGetActionMapping(
				PersonalPageSection.EDIT_ACCOUNT.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
					}
				});

		registerGetActionMapping(
				PersonalPageSection.EDIT_PROFILE.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
					}
				});

		registerGetActionMapping("abilityList", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				getAbilityList(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("editProfile", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doChangeUserInformations(req, resp);
			}
		});

		registerPostActionMapping("changePassword", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doChangePassword(req, resp);
			}
		});

		registerPostActionMapping("changeEmail", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doChangeEmail(req, resp);
			}
		});

		registerPostActionMapping("addAbility", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				manageCustomerAbility(req, resp, true);
			}
		});

		registerPostActionMapping("removeAbility", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				manageCustomerAbility(req, resp, false);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doChangePassword(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {

		String currentPassword = req.getParameter("currentpassword");
		String newPassword = req.getParameter("password");

		if (currentPassword == null || newPassword == null) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
			showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
			return;
		}

		HttpSession session = req.getSession();
		String username = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);

		/* Check password length */
		if (newPassword.length() < Misc.MIN_PASSWORD_LENGTH) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.INVALID_PASSWORD);
			showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
			return;
		}

		/* Authenticate request */
		if (!authenticateRequest(currentPassword, req)) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.INCORRECT_PASSWORD);
			showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
			return;
		}

		/* Now request is authenticated. Update password! */
		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		profile.changePassword(username, newPassword);

		req.setAttribute(Misc.NOTIFICATION_ATTR,
				NotificationMessages.PASSWORD_CHANGED);
		showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
	}

	private void doChangeEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String currentPassword = req.getParameter("currentpassword");
		HttpSession session = req.getSession();
		String newEmail = req.getParameter("newemail");

		/* Check email */

		/* Authenticate request */
		if (!authenticateRequest(currentPassword, req)) {
			/* Send error */
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.INCORRECT_PASSWORD);
			showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
			return;
		}

		String username = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);

		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		try {
			profile.changeEmail(username, newEmail);
		} catch (EmailAlreadyTakenException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMAIL_NOT_AVAILABLE);
			showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
			return;
		}

		req.setAttribute(Misc.NOTIFICATION_ATTR,
				NotificationMessages.EMAIL_CHANGED);
		showSection(PersonalPageSection.EDIT_ACCOUNT, req, resp);
	}

	private void doChangeUserInformations(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String username = getUsername(session);

		String[] fields = { "name", "surname", "birthdate", "location" };
		String[] nonNullableFields = { "name", "surname" };
		Map<String, Object> values = new HashMap<String, Object>();

		for (String field : fields) {
			String value = req.getParameter(field);

			if (Misc.isStringEmpty(value)
					&& Arrays.asList(nonNullableFields).contains(field)) {
				req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
				showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
				return;
			}

			/* For the field birthdate, convert it into a date. */
			if (field.equals("birthdate")) {
				try {
					Date d = Misc.DATE_FORMAT.parse(value);
					values.put(field, d);
				} catch (ParseException e) {
					req.setAttribute(Misc.ERROR_ATTR, ErrorType.BAD_DATE);
					showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
					return;
				}
			} else {
				values.put(field, value);
			}
		}

		/* Lookup bean to retrieve user informations */
		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);
		profile.updateCustomerDetails(username, values);

		req.setAttribute(Misc.NOTIFICATION_ATTR,
				NotificationMessages.DETAILS_CHANGED);
		showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
	}

	private void manageCustomerAbility(HttpServletRequest req,
			HttpServletResponse resp, Boolean shouldAdd) throws IOException,
			ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String username = getUsername(session);

		String abilityName = req.getParameter(Misc.ABILITY_NAME);

		if (Misc.isStringEmpty(abilityName)) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
			showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
			return;
		}

		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		try {
			if (shouldAdd) {
				profile.addAbility(username, abilityName);
			} else {
				profile.removeAbility(username, abilityName);
			}
		} catch (BadRequestException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.BAD_ABILITY_NAME);
			showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
			return;
		} catch (InvalidStateException e) {
			ErrorType err = shouldAdd ? ErrorType.ALREADY_DECLARED_ABILITY
					: ErrorType.ABILITY_NOT_DECLARED;
			req.setAttribute(Misc.ERROR_ATTR, err);
			showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
			return;
		}

		NotificationMessages mess = shouldAdd ? NotificationMessages.ABILITY_ADDED
				: NotificationMessages.ABILITY_REMOVED;

		req.setAttribute(Misc.NOTIFICATION_ATTR, mess);
		showSection(PersonalPageSection.EDIT_PROFILE, req, resp);
	}

	private void showSection(PersonalPageSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/* Check that user is logged in */
		HttpSession session = req.getSession();
		Object loggedIn = session
				.getAttribute(AuthenticationServlet.LOGGED_ATTRIBUTE);

		if (loggedIn == null || !(Boolean) loggedIn) {
			/* User is not logged in */
			resp.sendRedirect(req.getContextPath() + "/landing");
			return;
		}

		/* Retrieve username */
		String selfUsername = String.valueOf(session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME));

		/* Lookup bean to retrieve user informations */
		UserProfileControllerRemote profile = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		Customer c = profile.getByUsername(selfUsername);

		/* Put user informations in request */
		req.setAttribute(Misc.USER_TO_SHOW, c);

		/* Put user ability list in request */
		List<?> abilityList;
		try {
			abilityList = profile.getAbilityList(selfUsername);
			req.setAttribute(Misc.ABILITY_LIST, abilityList);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		/* Set section to show */
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.HOME);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.HOME_JSP).forward(req, resp);
		return;
	}

	private Boolean authenticateRequest(String password, HttpServletRequest req) {
		AuthenticationControllerRemote auth = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);
		HttpSession session = req.getSession();
		String username = (String) getUsername(session);

		try {
			auth.authenticateUser(username, password);
			return true;
		} catch (AuthenticationFailedException e) {
			return false;
		}
	}

	private void getAbilityList(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");

		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			return;
		}

		PrintWriter respWriter = resp.getWriter();

		/* Retrieve ability list */
		String match = req.getParameter("term");

		AbilityControllerRemote ability = lookupBean(
				AbilityControllerRemote.class, Misc.BeanNames.ABILITY);

		List<?> abilityList;
		if (Misc.isStringEmpty(match)) {
			abilityList = ability.getAvailableAbilityList();
		} else {
			abilityList = ability.getAvailableAbilityList(match);
		}

		int size = abilityList.size();

		respWriter.print("[");

		for (int i = 0; i < size; i++) {
			Object obj = abilityList.get(i);
			Ability a = (Ability) obj;
			respWriter.printf("\"%s\"%s", a.getName(),
					(i < size - 1 ? "," : ""));
		}

		respWriter.print("]");
	}
}