package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdministrationServlet.
 */
public class AdministrationServlet extends SwimServlet {
	
	private static final long serialVersionUID = -1318042817980004978L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdministrationServlet(){
		super();

		setSectionName("administration");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showRequestPage(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("create", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doCreateNewAbility(req, resp);
			}
		});
		
		registerPostActionMapping("accept", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doAcceptAbilityRequest(req, resp);
			}
		});
		
		registerPostActionMapping("decline", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doDeclineAbilityRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */ 
	
	private void doCreateNewAbility(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doAcceptAbilityRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doDeclineAbilityRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void showRequestPage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("suggestability.jsp").forward(req, resp);
	}
}
