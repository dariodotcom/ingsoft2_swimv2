package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WorkRequestServlet.
 */
public class WorkRequestServlet extends SwimServlet{

	private static final long serialVersionUID = -4086600990410926694L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkRequestServlet(){
		super();
		
		setSectionName("works");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showActiveWorkRequest(req, resp);
			}
		});
		
		registerGetActionMapping("/workrequests", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showPastWorkRequest(req, resp);
			}
		});
		
		/* POST request actions */
		
		
	}
	
	/* Methods to respond to different requests */ 
	
	private void showActiveWorkRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("workrequest.jsp").forward(req, resp);
	}
	
	private void showPastWorkRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("workrequest.jsp").forward(req, resp);
	}
}
