package it.polimi.swim.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerFriendship
 */
public class CustomerFriendship extends SwimServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerFriendship() {
		super();

		setSectionName("friends");

		// GET Actions
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showFriendList(req, resp);
			}
		});

		registerGetActionMapping("requests", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showFriendshipRequests(req, resp);
			}
		});

		// POST Actions
		ServletAction respond = new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				respondToFriendshipRequest(req, resp);
			}
		};

		registerPostActionMapping("accept", respond);
		registerPostActionMapping("decline", respond);
		
		registerPostActionMapping("remove", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				removeFriend(req, resp);
			}
		});
	}

	// Implementations of methods to respond to different requests
	private void respondToFriendshipRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		// Respond to friendship request

		// Show friend list page
		showFriendList(req, resp);

	}

	private void removeFriend(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Remove friend

		// Show friend list page
		showFriendList(req, resp);
	}

	private void showFriendList(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter w = resp.getWriter();
		resp.setContentType("text/html");
		w.println("view friends");
	}

	private void showFriendshipRequests(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter w = resp.getWriter();
		resp.setContentType("text/html");
		w.println("view friendship requests");
	}
}