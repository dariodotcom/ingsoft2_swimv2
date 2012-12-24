package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet.
 */
public class SearchServlet extends SwimServlet {

	private static final long serialVersionUID = -3243041247776896867L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet(){
		super();
		
		setSectionName("search");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showSearch(req, resp);
			}
		});

		/* POST request actions */
		
		registerPostActionMapping("login", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doSubmit(req, resp);
			}
		});

	}
	
	/* Methods to respond to different requests */ 
	
	private void doSubmit(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void showSearch(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("search.jsp").forward(req, resp);
	}
}
