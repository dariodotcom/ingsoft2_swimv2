package it.polimi.swim.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends SwimServlet {

	private static final long serialVersionUID = 2253528507384097557L;

	public AuthenticationServlet() {
		super();

		setSectionName("");

		// GET request actions
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showPage(req, resp);
			}
		});

		// POST request actions
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

		registerPostActionMapping("create", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doCreateUser(req, resp);
			}
		});
	}

	private void showPage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter w = resp.getWriter();
		resp.setContentType("text/html");
		w.println("Swim homepage, LOL");
	}

	private void doLogin(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void doLogout(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void doCreateUser(HttpServletRequest req, HttpServletResponse resp) {
	}

}
