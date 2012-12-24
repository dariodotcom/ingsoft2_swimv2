package it.polimi.swim.web.servlets;

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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenericProfileServlet(){
		super();

		setSectionName("genericprofile");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showUserInformations(req, resp);
			}
		});
		
		registerGetActionMapping("feedbacks", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showUserFeedbacks(req, resp);
			}
		});
		
		registerGetActionMapping("friends", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showUserFriends(req, resp);
			}
		});
		
		registerGetActionMapping("request", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showCreateRequestPage(req, resp);
			}
		});
		
		registerGetActionMapping("requestconfirm", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showRequestConfirm(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("sendworkrequest", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doSendWorkRequest(req, resp);
			}
		});
		
		registerPostActionMapping("sendfriendship", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doSendFriendshipRequest(req, resp);
			}
		});
	}
	
	/* Methods to respond to different requests */ 
	
	private void doSendWorkRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doSendFriendshipRequest(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void showUserInformations(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("genericuserprofile.jsp").forward(req, resp);
	}
	
	private void showUserFeedbacks(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("genericuserprofile.jsp").forward(req, resp);
	}
	
	private void showUserFriends(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("genericuserprofile.jsp").forward(req, resp);
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
