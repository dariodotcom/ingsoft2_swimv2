package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthenticationServlet.
 */
public class AuthenticationServlet extends SwimServlet {

	private static final long serialVersionUID = 2253528507384097557L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticationServlet() {
		super();

		setSectionName("landing");

		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showPage(req, resp);
			}
		});

		/* POST request actions */
		
		registerPostActionMapping("login", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doLogin(req, resp);
			}
		});

		registerPostActionMapping("logout", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doLogout(req, resp);
			}
		});

		registerPostActionMapping("register", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doCreateUser(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */
	private void doLogin(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void doLogout(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void doCreateUser(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void showPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("landing.jsp").forward(req, resp);
	}
}
