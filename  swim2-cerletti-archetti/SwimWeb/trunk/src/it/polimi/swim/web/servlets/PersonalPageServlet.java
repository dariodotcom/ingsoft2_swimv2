package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;

import javax.naming.NamingException;
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

	public enum PersonalPageSection {
		HOME("Il tuo profilo", ""), EDIT_PROFILE("Modifica profilo",
				"editProfile"), EDIT_ACCOUNT("Modifica account", "editAccount");

		private String sectionName, sectionIdentifier;

		private PersonalPageSection(String sectionName, String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		public String getSectionName() {
			return sectionName;
		}

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

		/* POST request actions */

		registerPostActionMapping("editProfile", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doChangeUserInformations(req, resp);
			}
		});

		registerPostActionMapping("changePassword", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doChangePassword(req, resp);
			}
		});

		registerPostActionMapping("changeEmail", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doChangeEmail(req, resp);
			}
		});

		registerPostActionMapping("addAbility", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doAddAbilityToDeclaredSet(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doChangePassword(HttpServletRequest req,
			HttpServletResponse resp) {

	}

	private void doChangeEmail(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void doChangeUserInformations(HttpServletRequest req,
			HttpServletResponse resp) {

	}

	private void doAddAbilityToDeclaredSet(HttpServletRequest req,
			HttpServletResponse resp) {

	}

	private void showSection(PersonalPageSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Check that user is logged in
		HttpSession session = req.getSession();
		Object loggedIn = session
				.getAttribute(AuthenticationServlet.LOGGED_ATTRIBUTE);

		if (loggedIn == null || !(Boolean) loggedIn) {
			// User is not logged in
			resp.sendRedirect(req.getContextPath() + "/landing");
			return;
		}

		// Retrieve username
		String username = String.valueOf(session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME));

		// lookup bean to retrieve user informations
		UserProfileControllerRemote profile;
		try {
			profile = lookupBean(UserProfileControllerRemote.class,
					"UserProfileController/remote");
		} catch (NamingException e) {
			e.printStackTrace(); // TODO
			return;
		}

		Customer c = profile.getByUsername(username);
		
		System.out.println("surname: " + c.getSurname());
		
		//Put user informations in request
		req.setAttribute(USER_ATTR, c);
		
		//Set section to show
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.HOME);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.HOME_JSP).forward(req, resp);
		return;
	}
}