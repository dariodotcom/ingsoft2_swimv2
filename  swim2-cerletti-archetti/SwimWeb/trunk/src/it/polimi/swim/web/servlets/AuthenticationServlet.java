package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.UsernameAlreadyTakenException;
import it.polimi.swim.web.pagesupport.ErrorType;
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
 * Servlet implementation class AuthenticationServlet.
 */
public class AuthenticationServlet extends SwimServlet {

	public static final String LOGGED_ATTRIBUTE = "loggedIn";
	public static final String LOGGED_USERNAME = "loggedUsername";
	public static final String LOGGED_USERTYPE = "loggedUserType";

	private static final long serialVersionUID = 2253528507384097557L;

	public static enum RegistrationFields {
		USERNAME("username", "Username", "text"), PASSWORD("password",
				"Password", "password"), REPEATPASSWORD("repeatpassword",
				"Ripeti password", "password"), EMAIL("email", "Email", "text"), NAME(
				"name", "Nome", "text"), SURNAME("surname", "Cognome", "text");

		private String type;
		private String name;
		private String label;

		private RegistrationFields(String name, String label, String type) {
			this.name = name;
			this.label = label;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public String getLabel() {
			return label;
		}

		public String getType() {
			return type;
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticationServlet() {
		super();

		setSectionName("");

		/* GET request actions */

		ServletAction showPage = new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showPage(req, resp);
			}
		};

		ServletAction showAbout = new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showAbout(req, resp);
			}
		};

		registerGetActionMapping("", showPage);

		registerGetActionMapping("landing", showPage);

		registerGetActionMapping("logout", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doLogout(req, resp);
			}
		});

		registerGetActionMapping("about", showAbout);

		/* POST request actions */

		registerPostActionMapping("login", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doLogin(req, resp);
			}
		});

		registerPostActionMapping("register", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doCreateUser(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doLogin(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		/* Retrieve the bean used for authentication */
		AuthenticationControllerRemote auth = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		/* Check user is not already logged in */
		if (isUserLoggedIn(session)) {
			// If so, send him to his homepage
			redirectToRightHome(req, resp);
		} else {
			String username = (String) req.getParameter("username")
					.toLowerCase();
			String password = (String) req.getParameter("password");

			if (Misc.isStringEmpty(password) || Misc.isStringEmpty(username)) {
				retry("login", ErrorType.EMPTY_FIELDS, req, resp);
				return;
			}

			UserType loggedUserType;

			/* Check user login details are valid */
			try {
				loggedUserType = auth.authenticateUser(username, password);
			} catch (AuthenticationFailedException e) {
				retry("login", ErrorType.INVALID_CREDENTIALS, req, resp);
				return;
			}

			// Check that customer email is validated
			if (loggedUserType.equals(UserType.CUSTOMER)) {

				UserProfileControllerRemote profileCtrl = lookupBean(
						UserProfileControllerRemote.class,
						Misc.BeanNames.PROFILE);

				Customer c = profileCtrl.getByUsername(username);

				if (!c.isEmailConfirmed()) {
					// User has to validate its email address
					session.setAttribute(Misc.LOGGED_USERNAME, username);
					resp.sendRedirect(req.getContextPath() + "/validatemail/");
					return;
				}
			}

			// Log in user
			session.setAttribute(LOGGED_ATTRIBUTE, true);
			session.setAttribute(LOGGED_USERNAME, username);
			session.setAttribute(LOGGED_USERTYPE, loggedUserType);
			redirectToRightHome(req, resp);
		}
	}

	private void doLogout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		/* Check that user is logged in */
		if (!isUserLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		/* Remove parameters from session */
		session.removeAttribute(LOGGED_ATTRIBUTE);
		session.removeAttribute(LOGGED_USERNAME);
		session.removeAttribute(LOGGED_USERTYPE);

		/* Forward to landing */
		resp.sendRedirect(req.getContextPath() + "/landing");
	}

	private void doCreateUser(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();
		Map<String, String> values = new HashMap<String, String>();

		for (RegistrationFields field : RegistrationFields.values()) {
			String fieldName = field.getName();
			String value = req.getParameter(fieldName);

			if (Misc.isStringEmpty(value)) {
				retry("registration", ErrorType.EMPTY_FIELDS, req, resp);
				return;
			}

			if (fieldName.equals("username")) {
				value = value.toLowerCase();
			}

			values.put(fieldName, value);
		}

		/* Check repeated password */
		String pwd = values.get(RegistrationFields.PASSWORD.getName());
		String rePwd = values.get(RegistrationFields.REPEATPASSWORD.getName());

		if (pwd.length() < Misc.MIN_PASSWORD_LENGTH) {
			retry("registration", ErrorType.INVALID_PASSWORD, req, resp);
			return;
		}

		if (!pwd.equals(rePwd)) {
			retry("registration", ErrorType.UNMATCHING_PASSWORDS, req, resp);
			return;
		}

		/* Check email */
//		String email = values.get(RegistrationFields.EMAIL.getName());
//		if (!Misc.isEmailValid(email)) {
//			retry("registration", ErrorType.BAD_EMAIL, req, resp);
//			return;
//		}

		/* Retrieve bean used for registration */
		AuthenticationControllerRemote auth = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		try {
			auth.createUser(values.get("username"), values.get("password"),
					values.get("email"), values.get("name"),
					values.get("surname"));
		} catch (UsernameAlreadyTakenException e) {
			retry("registration", ErrorType.USERNAME_NOT_AVAILABLE, req, resp);
			return;
		} catch (EmailAlreadyTakenException e) {
			retry("registration", ErrorType.EMAIL_NOT_AVAILABLE, req, resp);
			return;
		}

		session.setAttribute(Misc.LOGGED_USERNAME, values.get("username"));
		session.setAttribute(Misc.MAIL_VALIDATION_PENDING, true);

		resp.sendRedirect(req.getContextPath() + "/validatemail/");
	}

	private void showPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (isUserLoggedIn(req.getSession())) {
			redirectToRightHome(req, resp);
			return;
		}

		req.getRequestDispatcher(Misc.LANDING_JSP).forward(req, resp);
	}

	private void showAbout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setAttribute(Misc.SELECTED_TAB_ATTR, UnloggedMenu.ABOUT);
		req.getRequestDispatcher(Misc.ABOUT_JSP).forward(req, resp);
	}

	private void retry(String identifier, ErrorType err,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("retry", identifier);
		req.setAttribute(Misc.ERROR_ATTR, err);
		req.getRequestDispatcher(Misc.LANDING_JSP).forward(req, resp);
	}
}
