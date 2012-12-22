package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersonalPage
 */
public class PersonalPage extends SwimServlet {
	private static final long serialVersionUID = 1L;

	public PersonalPage() {
		super();

		setSectionName("home");
		
		//GET actions
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
		
		//POST actions
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

	// Functionalities
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
