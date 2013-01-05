package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.AuthenticationControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.MailSender;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.NotificationMessages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MailValidationServlet.
 */
public class MailValidationServlet extends SwimServlet {

	private static final long serialVersionUID = 3350469906837984940L;

	public static final String KEY_PARAM = "key";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailValidationServlet() {
		super();

		setSectionName("validatemail");

		/* GET request actions */

		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showMailValidationPresentation(req, resp);
			}
		});

		registerGetActionMapping("validate", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doValidateEmailAddress(req, resp);
			}
		});

	}

	/* Methods to respond to different requests */

	private void doValidateEmailAddress(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {

		String key = req.getParameter("key");

		AuthenticationControllerRemote authCtrl = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		try {
			authCtrl.validateCustomerEmail(key);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.INVALID_REQUEST);
			return;
		}

		req.setAttribute(Misc.NOTIFICATION_ATTR,
				NotificationMessages.EMAIL_VALIDATED);
		req.getRequestDispatcher(Misc.LANDING_JSP).forward(req, resp);
	}

	private void showMailValidationPresentation(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (isUserLoggedIn(session)) {
			redirectToRightHome(req, resp);
		}

		String username = getUsername(session);

		if (username == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		AuthenticationControllerRemote authCtrl = lookupBean(
				AuthenticationControllerRemote.class,
				Misc.BeanNames.AUTHENTICATION);

		String key;

		try {
			key = authCtrl.createEmailValidationRequest(username);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.VALIDATION_NOT_REQUIRED);
			return;
		}

		UserProfileControllerRemote profileCtrl = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		String targetEmail = profileCtrl.getByUsername(username).getEmail();
		String link = req.getContextPath() + "/validatemail/validate?key="
				+ key;

		MailSender.sendValidationEmail(targetEmail, link);

		req.setAttribute(Misc.CUSTOMER_EMAIL, targetEmail);
		req.getRequestDispatcher(Misc.MAILVALIDATION_JSP).forward(req, resp);
	}
}