package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailValidationServlet.
 */
public class MailValidationServlet extends SwimServlet {

	private static final long serialVersionUID = 3350469906837984940L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailValidationServlet() {
		super();

		setSectionName("validatemail");

		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showMailValidationPresentation(req, resp);
			}
		});
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException, ServletException {
				showMailValidationLanding(req, resp);
			}
		});
		
		/*POST request actions*/
		
		registerPostActionMapping("startvalidation", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doStartEmailValidation(req, resp);
			}
		});
		
		registerPostActionMapping("validate", new ServletAction() {
			public void runAction(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
				doValidateEmailAddress(req, resp);
			}
		});
		
	}

	/* Methods to respond to different requests */ 
	
	private void doStartEmailValidation(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void doValidateEmailAddress(HttpServletRequest req, HttpServletResponse resp) {
	}
	
	private void showMailValidationPresentation(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("mailvalidation.jsp").forward(req, resp);
	}
	
	private void showMailValidationLanding(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("mailvalidation.jsp").forward(req, resp);
	}
}
