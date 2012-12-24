package it.polimi.swim.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FeedbackServlet.
 */
public class FeedbackServlet extends SwimServlet {

	private static final long serialVersionUID = 5352393029820748102L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedbackServlet() {
		super();

		setSectionName("feedbacks");

		/* GET request actions */
		
		/* Received feedbacks */
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showReceivedFeedbackList(req, resp);
			}
		});

		/* Sent feedbacks */
		registerGetActionMapping("/sent", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showSentFeedbackList(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("/respond", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				respondToReceivedFeedback(req, resp);
			}
		});

	}

	/* Methods to respond to different requests */
	
	private void respondToReceivedFeedback(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		// Show received feedback list
		showReceivedFeedbackList(req, resp);

	}

	private void showReceivedFeedbackList(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter w = resp.getWriter();
		resp.setContentType("text/html");
		w.println("view received feedbacks");
	}

	private void showSentFeedbackList(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter w = resp.getWriter();
		resp.setContentType("text/html");
		w.println("view sent feedbacks");
	}
}