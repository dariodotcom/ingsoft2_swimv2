package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.MailSender;
import it.polimi.swim.web.pagesupport.Misc;

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

	public static enum PasswordResetSections {
		LANDING("Reset password."), EMAIL_SENT("Email inviata."), CONFIRM(
				"Password reimpostata.");

		String pageTitle;

		private PasswordResetSections(String title) {
			this.pageTitle = title;
		}

		public String getTitle() {
			return pageTitle;
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PasswordResetServlet() {
		super();

		setSectionName("resetpassword");

		/* GET request actions */

		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showLanding(req, resp);
			}
		});

		registerGetActionMapping("confirm", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				confirmRequest(req, resp);
			}
		});

		/* POST request actions */
		registerPostActionMapping("start", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				startPasswordReset(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */
	private void startPasswordReset(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		String username = req.getParameter("username");

		if (Misc.isStringEmpty(username)) {
			showSection(PasswordResetSections.LANDING, ErrorType.EMPTY_FIELDS,
					req, resp);
			return;
		}

		AuthenticationControllerRemote authCtrl = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		String reqId;

		try {
			reqId = authCtrl.createPasswordResetRequest(username);
		} catch (BadRequestException e) {
			showSection(PasswordResetSections.LANDING,
					ErrorType.INVALID_CREDENTIALS, req, resp);
			return;
		}

		UserProfileControllerRemote profileCtrl = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		String email = profileCtrl.getByUsername(username).getEmail();
		String link = req.getContextPath() + "/resetpassword/confirm?key="
				+ reqId;
		MailSender.sendPasswordResetStartEmail(email, link);
		showSection(PasswordResetSections.EMAIL_SENT, null, req, resp);
		return;
	}

	private void showLanding(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		showSection(PasswordResetSections.LANDING, null, req, resp);
	}

	private void confirmRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String reqId = req.getParameter("key");

		if (Misc.isStringEmpty(reqId)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		AuthenticationControllerRemote authCtrl = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		String newPassword, email;

		try {
			email = authCtrl.getPasswordResetEmail(reqId);
			newPassword = authCtrl.resetCustomerPassword(reqId);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		MailSender.sendPasswordResetConfirmEmail(email, newPassword);
		showSection(PasswordResetSections.CONFIRM, null, req, resp);
		return;
	}

	/* Helpers */
	private void showSection(PasswordResetSections section, ErrorType err,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		if (err != null) {
			req.setAttribute(Misc.ERROR_ATTR, err);
		}

		req.getRequestDispatcher(Misc.PASSWORD_RESET_JSP).forward(req, resp);
	}
}
