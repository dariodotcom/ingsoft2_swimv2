package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
import it.polimi.swim.business.exceptions.AuthenticationFailedException;
import it.polimi.swim.business.exceptions.EmailAlreadyTakenException;
import it.polimi.swim.business.exceptions.UsernameAlreadyTakenException;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
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
	public static final String USERTYPE_ADMIN = "loggedUserType";
	public static final String USERTYPE_CUSTOMER = "loggedUserType";

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

		AuthenticationControllerRemote auth;

		// Retrieve the bean used for authentication
		try {
			auth = lookupBean(AuthenticationControllerRemote.class,
					"AuthenticationController/remote");
		} catch (NamingException e) {
			// TODO
			return;
		}

		// Check user is not already logged in
		if (!isUserLoggedIn(session)) {
			String username = (String) req.getParameter("username");
			String password = (String) req.getParameter("password");

			if (username == null || password == null) {
				sendError(req, resp, ErrorType.BAD_REQUEST);
				return;
			}

			// Check user login details are valid

			try {
				UserType loggedUserType = auth.authenticateUser(username,
						password);

				// Log in user
				session.setAttribute(LOGGED_ATTRIBUTE, true);
				session.setAttribute(LOGGED_USERNAME, username);
				session.setAttribute(LOGGED_USERTYPE, loggedUserType);

			} catch (AuthenticationFailedException e) {
				session.setAttribute("retry", "login");
				resp.sendRedirect(req.getContextPath() + "/retry");
				return;
			}
		}

		// Forward request to /home servlet
		resp.sendRedirect(req.getContextPath() + "/home/");
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

		for (String fieldName : fields) {
			String value = req.getParameter(fieldName);

			if (value != null) {
				values.put(fieldName, value);
			} else {
				req.getSession().setAttribute("retry", "registration");
				resp.sendRedirect(req.getContextPath() + "/retry");
				return;
			}
		}

		// Retrieve bean used for registration
		AuthenticationControllerRemote auth;

		try {
			auth = lookupBean(AuthenticationControllerRemote.class,
					"AuthenticationController/remote");
		} catch (NamingException e) {
			// TODO
			return;
		}

		try {
			auth.createUser(values.get("username"), values.get("password"),
					values.get("email"), values.get("name"),
					values.get("surname"));
		} catch (UsernameAlreadyTakenException e) {
			// TODO Auto-generated catch block
			return;
		} catch (EmailAlreadyTakenException e) {
			// TODO Auto-generated catch block
			return;
		}

		// Redirect user to home for the moment //TODO
		resp.sendRedirect(req.getContextPath() + "/landing");
	}

	private void showPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher(Misc.LANDING_JSP).forward(req, resp);
	}
	
	private void showAbout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher(Misc.ABOUT_JSP).forward(req, resp);
	}

}
