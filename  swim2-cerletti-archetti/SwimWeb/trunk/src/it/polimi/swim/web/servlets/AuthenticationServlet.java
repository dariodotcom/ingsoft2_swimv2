package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
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

		registerGetActionMapping("retry", showPage);

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
					HttpServletResponse resp) throws IOException {
				doCreateUser(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */
	private void doLogin(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		// Retrieve the bean used for authentication
		AuthenticationControllerRemote auth = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		// Check user is not already logged in
		if (!isUserLoggedIn(session)) {
			String username = (String) req.getParameter("username")
					.toLowerCase();
			String password = (String) req.getParameter("password");

			if (Misc.isStringEmpty(password) || Misc.isStringEmpty(username)) {
				session.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
				session.setAttribute("retry", "login");
				resp.sendRedirect(req.getContextPath() + "/retry");
				return;
			}

			UserType loggedUserType;

			// Check user login details are valid
			try {
				loggedUserType = auth.authenticateUser(username, password);

				// Log in user
				session.setAttribute(LOGGED_ATTRIBUTE, true);
				session.setAttribute(LOGGED_USERNAME, username);
				session.setAttribute(LOGGED_USERTYPE, loggedUserType);

			} catch (AuthenticationFailedException e) {
				session.setAttribute("retry", "login");
				session.setAttribute(Misc.ERROR_ATTR,
						ErrorType.INVALID_CREDENTIALS);
				resp.sendRedirect(req.getContextPath() + "/retry");
				return;
			}
		}

		// Forward request to /home servlet
		redirectToRightHome(req, resp);
	}

	private void doLogout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		// Check that user is logged in
		if (!isUserLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
		}

		// Remove parameters from session
		session.removeAttribute(LOGGED_ATTRIBUTE);
		session.removeAttribute(LOGGED_USERNAME);
		session.removeAttribute(LOGGED_USERTYPE);

		// Forward to landing
		resp.sendRedirect(req.getContextPath() + "/landing");
	}

	private void doCreateUser(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String[] fields = { "username", "password", "email", "name", "surname" };
		Map<String, String> values = new HashMap<String, String>();
		HttpSession session = req.getSession();

		for (String fieldName : fields) {
			String value = req.getParameter(fieldName);

			if (Misc.isStringEmpty(value)) {
				session.setAttribute("retry", "registration");
				session.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
				resp.sendRedirect(req.getContextPath() + "/retry");
				return;
			}

			if (fieldName.equals("username")) {
				value = value.toLowerCase();
			}

			values.put(fieldName, value);
		}

		// Retrieve bean used for registration
		AuthenticationControllerRemote auth = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		try {
			auth.createUser(values.get("username"), values.get("password"),
					values.get("email"), values.get("name"),
					values.get("surname"));
		} catch (UsernameAlreadyTakenException e) {
			session.setAttribute("retry", "registration");
			session.setAttribute(Misc.ERROR_ATTR,
					ErrorType.USERNAME_NOT_AVAILABLE);
			resp.sendRedirect(req.getContextPath() + "/retry");
			return;
		} catch (EmailAlreadyTakenException e) {
			session.setAttribute("retry", "registration");
			session.setAttribute(Misc.ERROR_ATTR, ErrorType.EMAIL_NOT_AVAILABLE);
			resp.sendRedirect(req.getContextPath() + "/retry");
			return;
		}

		// Redirect user to home for the moment //TODO
		resp.sendRedirect(req.getContextPath() + "/landing");
	}

	private void showPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (isCustomerLoggedIn(req.getSession())) {
			resp.sendRedirect(req.getContextPath() + "/home/");
			return;
		}

		req.getRequestDispatcher(Misc.LANDING_JSP).forward(req, resp);
	}

	private void showAbout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setAttribute(Misc.SELECTED_TAB_ATTR, UnloggedMenu.ABOUT);
		req.getRequestDispatcher(Misc.ABOUT_JSP).forward(req, resp);
	}

}
