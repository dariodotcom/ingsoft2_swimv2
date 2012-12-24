package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserFriendServlet.
 */
public class UserFriendServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserFriendServlet() {
		super();

		setSectionName("friends");

		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showFriendList(req, resp);
			}
		});

		registerGetActionMapping("/requests", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showFriendRequestList(req, resp);
			}
		});

		/* POST request actions */
		
		registerPostActionMapping("reply", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doAcceptFriendRequest(req, resp);
			}
		});
		
		registerPostActionMapping("reply", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doDeclineFriendRequest(req, resp);
			}
		});
		
		registerPostActionMapping("reply", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doRemoveFriend(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */
	
	private void doAcceptFriendRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doDeclineFriendRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doRemoveFriend(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void showFriendList(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("friends.jsp").forward(req, resp);
	}
	
	private void showFriendRequestList(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("friends.jsp").forward(req, resp);
	}
}