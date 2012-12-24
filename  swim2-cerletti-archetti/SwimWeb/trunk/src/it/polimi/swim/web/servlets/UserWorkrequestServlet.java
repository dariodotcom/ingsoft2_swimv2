package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserWorkrequestServlet.
 */
public class UserWorkrequestServlet extends SwimServlet{

	private static final long serialVersionUID = -4086600990410926694L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserWorkrequestServlet(){
		super();
		
		setSectionName("works");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showWorkRequest(req, resp);
			}
		});
		
		registerGetActionMapping("/active", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showActiveRequestList(req, resp);
			}
		});
		
		registerGetActionMapping("/archived", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showArchivedRequestList(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("accept", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doAcceptWorkRequest(req, resp);
			}
		});
		
		registerPostActionMapping("decline", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doDeclineWorkRequest(req, resp);
			}
		});
		
		registerPostActionMapping("completed", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doMarkRequestAsCompleted(req, resp);
			}
		});
		
		registerPostActionMapping("sendmsg", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doSendMessage(req, resp);
			}
		});
		
		registerPostActionMapping("insertfeedback", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doInsertFeedback(req, resp);
			}
		});
		
		registerPostActionMapping("replyfeedback", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doReplyToFeedback(req, resp);
			}
		});
	}
	
	/* Methods to respond to different requests */ 
	
	private void doAcceptWorkRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doDeclineWorkRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doMarkRequestAsCompleted(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doSendMessage(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doInsertFeedback(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doReplyToFeedback(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void showWorkRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("workrequest.jsp").forward(req, resp);
	}
	
	private void showActiveRequestList(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("workrequest.jsp").forward(req, resp);
	}
	
	private void showArchivedRequestList(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("workrequest.jsp").forward(req, resp);
	}
}
