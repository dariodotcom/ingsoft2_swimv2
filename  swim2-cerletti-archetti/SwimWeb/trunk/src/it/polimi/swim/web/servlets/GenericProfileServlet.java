package it.polimi.swim.web.servlets;

import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenericProfileServlet.
 */
public class GenericProfileServlet extends SwimServlet {

	private static final long serialVersionUID = 1852003714703658764L;

	public static final String CONTEXT_NAME = "user";

	public enum GenericProfileSection {
		PROFILE("Profilo", ""), FEEDBACKS("Feedback", "feedbacks"), FRIENDS(
				"Friends", "friends");

		private String sectionName, sectionIdentifier;

		private GenericProfileSection(String sectionName,
				String sectionIdentifier) {
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
	public GenericProfileServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */

		registerGetActionMapping(
				GenericProfileSection.PROFILE.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(GenericProfileSection.PROFILE, req, resp);
					}
				});

		registerGetActionMapping(
				GenericProfileSection.FEEDBACKS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(GenericProfileSection.FEEDBACKS, req, resp);
					}
				});

		registerGetActionMapping(
				GenericProfileSection.FRIENDS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(GenericProfileSection.FRIENDS, req, resp);
					}
				});

		registerGetActionMapping("request", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showCreateRequestPage(req, resp);
			}
		});

		registerGetActionMapping("requestconfirm", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showRequestConfirm(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("sendworkrequest", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doSendWorkRequest(req, resp);
			}
		});

		registerPostActionMapping("sendfriendship", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doSendFriendshipRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void showSection(GenericProfileSection section, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.USER_JSP).forward(req, resp);
	}
	
	private void doSendWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doSendFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void showCreateRequestPage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("sendworkrequest.jsp").forward(req, resp);
	}

	private void showRequestConfirm(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("sendworkrequest.jsp").forward(req, resp);
	}
}
