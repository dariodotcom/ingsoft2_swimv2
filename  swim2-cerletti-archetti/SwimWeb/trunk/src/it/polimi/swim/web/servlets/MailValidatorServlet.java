package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailValidatorServlet.
 */
public class MailValidatorServlet extends SwimServlet {

	private static final long serialVersionUID = 3350469906837984940L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailValidatorServlet() {
		super();

		setSectionName("validatemail");

		/* GET request actions */
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showValidationPage(req, resp);
			}
		});
		
		/*POST request actions*/
	}

	/* Methods to respond to different requests */ 
	
	private void showValidationPage(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
}
