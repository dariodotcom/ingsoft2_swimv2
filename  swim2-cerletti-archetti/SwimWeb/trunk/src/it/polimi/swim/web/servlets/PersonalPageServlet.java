package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersonalPageServlet.
 */
public class PersonalPageServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersonalPageServlet() {
		super();

		setSectionName("home");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				showPersonalPage(req, resp);
			}
		});
		
		registerGetActionMapping("editAccount", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				showAccountEditPage(req, resp);
			}
		});
		
		registerGetActionMapping("editProfile", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				showProfileEditPage(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("editProfile", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doChangeUserInformations(req, resp);
			}
		});
		
		registerPostActionMapping("changePassword", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doChangePassword(req, resp);
			}
		});
		
		registerPostActionMapping("changeEmail", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doChangeEmail(req, resp);
			}
		});
		
		registerPostActionMapping("addAbility", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doAddAbilityToDeclaredSet(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */ 
	
	private void doChangePassword(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void doChangeEmail(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void doChangeUserInformations(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void doAddAbilityToDeclaredSet(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void showPersonalPage(HttpServletRequest req,
			HttpServletResponse resp) {
		req.getRequestDispatcher("home.jsp").forward(req, resp);
	}
	
	private void showAccountEditPage(HttpServletRequest req,
			HttpServletResponse resp) {
		req.getRequestDispatcher("home.jsp").forward(req, resp);
	}
	
	private void showProfileEditPage(HttpServletRequest req,
			HttpServletResponse resp) {
		req.getRequestDispatcher("home.jsp").forward(req, resp);
	}
	
}
