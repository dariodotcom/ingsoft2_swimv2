package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PasswordResetServlet.
 */
public class PasswordResetServlet extends SwimServlet {

	private static final long serialVersionUID = -1677560603959997510L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PasswordResetServlet(){
		super();

		setSectionName("resetpassword");
		
		/* GET request actions */
		
		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showPasswordResetRequest(req, resp);
			}
		});
		
		registerGetActionMapping("/pswreqfirstemailsent", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showPasswordRequestFirstEmailSent(req, resp);
			}
		});
		
		registerGetActionMapping("/pswreqsecondemailsent", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showPasswordRequestSecondEmailSent(req, resp);
			}
		});
		
		/* POST request actions */
		
		registerPostActionMapping("reset", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doResetPassword(req, resp);
			}
		});
	}
	
	/* Methods to respond to different requests */ 
	
	private void doResetPassword(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void showPasswordResetRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("passwordreset.jsp").forward(req, resp);
	}
	
	private void showPasswordRequestFirstEmailSent(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("passwordreset.jsp").forward(req, resp);
	}
	
	private void showPasswordRequestSecondEmailSent(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("passwordreset.jsp").forward(req, resp);
	}
}
