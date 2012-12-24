package it.polimi.swim.web.servlets;

import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersonalPageServlet.
 */
public class PersonalPageServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	public static final String CONTEXT_NAME = "home";
	
	public enum PersonalPageSection {
		HOME("Il tuo profilo", ""), EDIT_PROFILE("Modifica profilo", "editProfile"), EDIT_ACCOUNT(
				"Modifica account", "editAccount");

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
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.HOME);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.HOME_JSP).forward(req, resp);
		return;
	}
}