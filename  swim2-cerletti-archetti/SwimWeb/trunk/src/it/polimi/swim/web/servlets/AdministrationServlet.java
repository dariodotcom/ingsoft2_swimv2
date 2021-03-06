package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.AbilityControllerRemote;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.web.pagesupport.AdminMenu;
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
 * Servlet implementation class AdministrationServlet.
 */
public class AdministrationServlet extends SwimServlet {

	private static final long serialVersionUID = -1318042817980004978L;
	private static final String ABILITY_NAME = "abilityName";
	private static final String ABILITY_DESC = "abilityDescription";
	private static final String ABILITY_REVIEW = "review";
	private static final String REVIEW_ACCEPT = "accept";
	private static final String REVIEW_REQUEST = "request";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdministrationServlet() {
		super();

		setSectionName("admin");

		/* GET request actions */

		registerGetActionMapping(
				AdministrationSection.REQUEST.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(AdministrationSection.REQUEST, req, resp);
					}
				});

		registerGetActionMapping(
				AdministrationSection.MANAGEMENT.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(AdministrationSection.MANAGEMENT, req, resp);
					}
				});

		/* POST request actions */

		registerPostActionMapping("create", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doCreateNewAbility(req, resp);
			}
		});

		registerPostActionMapping("respond", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doRespondToAbilityRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doCreateNewAbility(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {

		HttpSession session = req.getSession();
		String adminUsr = getUsername(session);

		if (!isAdministratorLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String abilityName = req.getParameter(ABILITY_NAME);
		String abilityDesc = req.getParameter(ABILITY_DESC);

		AbilityControllerRemote ability = lookupBean(
				AbilityControllerRemote.class, Misc.BeanNames.ABILITY);

		req.setAttribute(Misc.SELECTED_SECTION_ATTR,
				AdministrationSection.MANAGEMENT);

		try {
			ability.addNewAbility(adminUsr, abilityName, abilityDesc);
		} catch (BadRequestException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.EMPTY_FIELDS);
			req.getRequestDispatcher(Misc.ADMIN_JSP).forward(req, resp);
			return;
		} catch (InvalidStateException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.ABILITY_NAME_TAKEN);
			req.getRequestDispatcher(Misc.ADMIN_JSP).forward(req, resp);
			return;
		}

		req.setAttribute(Misc.NOTIFICATION_ATTR,
				NotificationMessages.ABILITY_ADDED);
		req.getRequestDispatcher(Misc.ADMIN_JSP).forward(req, resp);
		return;
	}

	private void doRespondToAbilityRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		String adminUsr = getUsername(session);

		if (!isAdministratorLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String review = req.getParameter(ABILITY_REVIEW);
		Boolean accept = Boolean.valueOf(req.getParameter(REVIEW_ACCEPT));
		int reqId;

		try {
			reqId = Integer.parseInt(req.getParameter(REVIEW_REQUEST));
		} catch (NumberFormatException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		if (Misc.isStringEmpty(review) || accept == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		AbilityControllerRemote abilityCtrl = lookupBean(
				AbilityControllerRemote.class, Misc.BeanNames.ABILITY);

		try {
			abilityCtrl.reviewAbilityRequest(adminUsr, reqId, accept, review);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			req.setAttribute(Misc.ERROR_ATTR, ErrorType.ABILITY_NAME_TAKEN);
			return;
		}

		NotificationMessages mess = accept ? NotificationMessages.ABILITY_ADDED
				: NotificationMessages.ABILITY_REQ_REFUSED;

		req.setAttribute(Misc.NOTIFICATION_ATTR, mess);
		showSection(AdministrationSection.REQUEST, req, resp);

	}

	private void showSection(AdministrationSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (!isAdministratorLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		/* Put ability requests in req scope */
		AbilityControllerRemote ability = lookupBean(
				AbilityControllerRemote.class, Misc.BeanNames.ABILITY);
		List<?> abilityReqList = ability.getAbilityRequestList();
		req.setAttribute(Misc.ABILITY_LIST, abilityReqList);

		/* Forward */
		req.setAttribute(Misc.SELECTED_TAB_ATTR, AdminMenu.MANAGEMENT);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.ADMIN_JSP).forward(req, resp);
	}

	/* Enumerations */
	
	/**
	 * AdministrationSection is an enumeration useful to provide all the
	 * possible sections accessible from the home page of an administrator.
	 */
	public enum AdministrationSection {
		REQUEST("Richieste", ""), MANAGEMENT("Amministrazione", "manage");

		private String sectionName, sectionIdentifier;

		AdministrationSection(String sectionName, String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the name of this
		 *         AdministrationSection.
		 */
		public String getSectionName() {
			return sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the identifier of this
		 *         AdministrationSection.
		 */
		public String getSectionIdentifier() {
			return sectionIdentifier;
		}
	}
}
