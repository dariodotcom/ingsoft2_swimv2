package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.AbilityControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;
import it.polimi.swim.web.pagesupport.NotificationMessages;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AbilityRequestServlet.
 */
public class AbilityRequestServlet extends SwimServlet {

	private static final long serialVersionUID = 6907811996106198670L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AbilityRequestServlet() {
		super();

		setSectionName("abilityrequest");

		/* GET request actions */

		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showRequestPage(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("suggest", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doAddAbilityRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doAddAbilityRequest(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String loggedUsername = getUsername(session);
		String name = req.getParameter("name");
		String description = req.getParameter("description");

		if (Misc.isStringEmpty(name) || Misc.isStringEmpty(description)) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
			showRequestPage(req, resp);
		}

		AbilityControllerRemote abilityCtrl = lookupBean(
				AbilityControllerRemote.class, Misc.BeanNames.ABILITY);

		try {
			abilityCtrl.addAbilityRequest(loggedUsername, name, description);
		} catch (BadRequestException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
			showRequestPage(req, resp);
			return;
		} catch (InvalidStateException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.ABILITY_NAME_TAKEN);
			showRequestPage(req, resp);
			return;
		}

		req.setAttribute(Misc.NOTIFICATION_ATTR, NotificationMessages.REQUEST_SENT);
		showRequestPage(req, resp);
	}

	private void showRequestPage(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String username = getUsername(session);

		UserProfileControllerRemote profileCtrl = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		List<?> abilityReqList = profileCtrl.getSentAbilityRequest(username);

		req.setAttribute(Misc.ABILITY_LIST, abilityReqList);
		req.getRequestDispatcher(Misc.ABILITY_REQUEST_JSP).forward(req, resp);
	}
}
