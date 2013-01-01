package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.AbilityControllerRemote;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;

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

	public enum AdministrationSection {
		REQUEST("Richieste", ""), MANAGEMENT("Gestione", "manage");

		private String sectionName, sectionIdentifier;

		private AdministrationSection(String sectionName,
				String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		public String getSectionName() {
			return sectionName;
		}

		public String getSectionIdentifier() {
			return sectionIdentifier;
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdministrationServlet() {
		super();

		setSectionName("administration");

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
					HttpServletResponse resp) throws IOException {
				doCreateNewAbility(req, resp);
			}
		});

		registerPostActionMapping("accept", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doAcceptAbilityRequest(req, resp);
			}
		});

		registerPostActionMapping("decline", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				doDeclineAbilityRequest(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doCreateNewAbility(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doAcceptAbilityRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doDeclineAbilityRequest(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void showSection(AdministrationSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (!isAdministratorLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		// Put ability requests in req scope.
		AbilityControllerRemote ability = lookupBean(
				AbilityControllerRemote.class, Misc.BeanNames.ABILITY);
		List<?> abilityReqList = ability.getAbilityRequestList();
		req.setAttribute(Misc.ABILITY_LIST, abilityReqList);
		
		// Forward
		req.getRequestDispatcher(Misc.ADMIN_JSP).forward(req, resp);
	}
}
