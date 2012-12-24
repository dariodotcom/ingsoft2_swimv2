package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AbilityRequestServlet.
 */
public class AbilityRequestServlet extends SwimServlet {
	
	private static final long serialVersionUID = 6907811996106198670L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AbilityRequestServlet(){
		super();

		setSectionName("abilityrequest");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showRequestPage(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("abilityrequest", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doAddAbilityRequest(req, resp);
			}
		});
	}
	
	/* Methods to respond to different requests */ 

	private void doAddAbilityRequest(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void showRequestPage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("suggestability.jsp").forward(req, resp);
	}
}
