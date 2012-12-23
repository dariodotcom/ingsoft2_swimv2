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
				showInformationPage(req, resp);
			}
		});
		
		registerGetActionMapping("editAccount", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				showEditAccount(req, resp);
			}
		});
		
		registerGetActionMapping("editProfile", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				showEditProfile(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("changePassword", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				changePassword(req, resp);
			}
		});
		
		registerPostActionMapping("changeEmail", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				changeEmail(req, resp);
			}
		});
		
		registerPostActionMapping("editProfile", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				editProfile(req, resp);
			}
		});
		
		registerPostActionMapping("addAbility", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				addAbility(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */ 
	
	private void showInformationPage(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void showEditAccount(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void showEditProfile(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void changePassword(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void changeEmail(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void editProfile(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
	private void addAbility(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
	
}
