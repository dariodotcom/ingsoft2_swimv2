package it.polimi.swim.web.servlets;

import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerFriendshipServlet.
 */
public class CustomerFriendshipServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	public static final String CONTEXT_NAME = "friends";

	public enum CustomerFriendshipSection {
		FRIENDS("Amici", ""), FRIENDSHIP_REQUESTS("Richieste di amicizia",
				"requests");

		private String sectionName, sectionIdentifier;

		private CustomerFriendshipSection(String sectionName,
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
	public CustomerFriendshipServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */

		registerGetActionMapping(
				CustomerFriendshipSection.FRIENDS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(CustomerFriendshipSection.FRIENDS, req,
								resp);
					}
				});

		registerGetActionMapping(
				CustomerFriendshipSection.FRIENDSHIP_REQUESTS
						.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(
								CustomerFriendshipSection.FRIENDSHIP_REQUESTS,
								req, resp);
					}
				});

		/* POST request actions */

		ServletAction respond = new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				respondToFriendshipRequest(req, resp);
			}
		};

		registerPostActionMapping("/accept", respond);
		registerPostActionMapping("/decline", respond);

		registerPostActionMapping("/remove", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				removeFriend(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void respondToFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		// Respond to friendship request

		// Show friend list page

	}

	private void removeFriend(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Remove friend

		// Show friend list page

	}

	private void showSection(CustomerFriendshipSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.FRIENDS);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.FRIENDS_JSP).forward(req, resp);
		return;
	}
}