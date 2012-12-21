package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerFriendship
 */
public class CustomerFriendship extends SwimServlet {
	private static final long serialVersionUID = 1L;

	private final String sectionName = "friends";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerFriendship() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String identifier = getActionIdentifier(request, sectionName);
		Action a = Action.getByIdentifier(identifier);

		switch (a) {
		case DEFAULT:
			this.showFriendList(request, response);
			break;
		default:
			// Servlet cannot perform required action
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Action a = Action.getByIdentifier(this.getActionIdentifier(request,
				sectionName));

		switch (a) {
		case ACCEPT:
		case DECLINE:
			this.respondToFriendshipRequest(request, response);
			break;
		case REMOVE:
			this.removeFriend(request, response);
			break;
		default:
			// Servlet cannot perform required action.
		}
	}

	// Implementations of methods to respond to different requests
	private void respondToFriendshipRequest(HttpServletRequest req,
			HttpServletResponse res) {
		// Respond to friendship request

		// Show friend list page
		showFriendList(req, res);

	}

	private void removeFriend(HttpServletRequest req, HttpServletResponse res) {
		// Remove friend

		// Show friend list page
		showFriendList(req, res);
	}

	private void showFriendList(HttpServletRequest req, HttpServletResponse res) {

	}

	// Enum to parse request
	private enum Action {

		ACCEPT("accept"), DECLINE("decline"), REMOVE("remove"), DEFAULT("");

		private String identifier;

		Action(String identifier) {
			this.identifier = identifier;
		}

		public static Action getByIdentifier(String identifier) {
			for (Action a : Action.values()) {
				if (a.identifier == identifier) {
					return a;
				}
			}
			return null;
		}
	}
}