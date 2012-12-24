package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserFeedbackServlet.
 */
public class UserFeedbackServlet extends SwimServlet {

	private static final long serialVersionUID = 5352393029820748102L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserFeedbackServlet() {
		super();

		setSectionName("feedbacks");

		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showReceivedFeedbackList(req, resp);
			}
		});
		
		registerGetActionMapping("sent", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showSentFeedbackList(req, resp);
			}
		});

		/* POST request actions */
		
		registerPostActionMapping("reply", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doReplyToReceivedFeedback(req, resp);
			}
		});

	}

	/* Methods to respond to different requests */
	
	private void doReplyToReceivedFeedback(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void showReceivedFeedbackList(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("userfeedbacks.jsp").forward(req, resp);
	}

	private void showSentFeedbackList(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("userfeedbacks.jsp").forward(req, resp);
	}
	
}
