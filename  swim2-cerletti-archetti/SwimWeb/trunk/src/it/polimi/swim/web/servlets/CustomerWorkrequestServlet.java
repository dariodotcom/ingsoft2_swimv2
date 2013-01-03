package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
import it.polimi.swim.business.entity.WorkRequest;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;
import it.polimi.swim.web.pagesupport.CustomerMenu;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserWorkrequestServlet.
 */
public class CustomerWorkrequestServlet extends SwimServlet {

	private static final long serialVersionUID = -4086600990410926694L;

	public static final String CONTEXT_NAME = "works";

	public enum CustomerWorkRequestSection {
		ACTIVE_REQUESTS("Richieste attive", ""), ARCHIVED_REQUESTS(
				"Richieste archiviate", "archived");

		private String sectionName, sectionIdentifier;

		private CustomerWorkRequestSection(String sectionName,
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
	public CustomerWorkrequestServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */

		registerGetActionMapping(
				CustomerWorkRequestSection.ACTIVE_REQUESTS
						.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(CustomerWorkRequestSection.ACTIVE_REQUESTS,
								req, resp);
					}
				});

		registerGetActionMapping(
				CustomerWorkRequestSection.ARCHIVED_REQUESTS
						.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(
								CustomerWorkRequestSection.ARCHIVED_REQUESTS,
								req, resp);
					}
				});

		registerGetActionMapping("view", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showWorkRequest(req, resp);
			}
		});

		/* POST request actions */

		registerPostActionMapping("respond", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doRespondToWorkRequest(req, resp);
			}
		});

		registerPostActionMapping("completed", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doMarkRequestAsCompleted(req, resp);
			}
		});

		registerPostActionMapping("sendmsg", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doSendMessage(req, resp);
			}
		});

		registerPostActionMapping("insertfeedback", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doInsertFeedback(req, resp);
			}
		});

		registerPostActionMapping("replyfeedback", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) {
				doReplyToFeedback(req, resp);
			}
		});
	}

	/* Methods to respond to different requests */

	private void doRespondToWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		Integer reqId = getWorkRequestId(req);
		Boolean response = getCustomerResponse(req);

		if (reqId == null || response == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
		}

		String loggedUsername = getUsername(session);

		WorkRequestControllerRemote workReqCtrl = lookupBean(
				WorkRequestControllerRemote.class, Misc.BeanNames.WORKREQUEST);

		try {
			workReqCtrl.respondToWorkRequest(loggedUsername, response, reqId);
		} catch (UnauthorizedRequestException e) {
			sendError(req, resp, ErrorType.UNAUTHORIZED_REQUEST);
			return;
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.INVALID_REQUEST);
			return;
		}
		
		resp.sendRedirect(req.getContextPath() + "/works/view?w=" + reqId);
	}

	private void doMarkRequestAsCompleted(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doSendMessage(HttpServletRequest req, HttpServletResponse resp) {
	}

	private void doInsertFeedback(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void doReplyToFeedback(HttpServletRequest req,
			HttpServletResponse resp) {
	}

	private void showSection(CustomerWorkRequestSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.WORKS);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);

		req.getRequestDispatcher(Misc.WORKREQUEST_LIST_JSP).forward(req, resp);
	}

	private void showWorkRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String selfUsername = getUsername(session);

		Integer reqId = getWorkRequestId(req);
		if (reqId == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		WorkRequestControllerRemote workReqCtrl = lookupBean(
				WorkRequestControllerRemote.class, Misc.BeanNames.WORKREQUEST);

		WorkRequest workReq;
		try {
			workReq = workReqCtrl.getById(reqId);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		String senderUsername = workReq.getSender().getUsername();
		String recevierUsername = workReq.getReceiver().getUsername();

		if (!(selfUsername.equals(senderUsername) || selfUsername
				.equals(recevierUsername))) {
			sendError(req, resp, ErrorType.UNAUTHORIZED_REQUEST);
			return;
		}

		req.setAttribute(Misc.TARGET_WORKREQUEST, workReq);
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.WORKS);
		req.getRequestDispatcher(Misc.VIEW_WORKREQUEST_JSP).forward(req, resp);
	}

	private Integer getWorkRequestId(HttpServletRequest req) {
		try {
			return Integer.parseInt(req.getParameter("w"));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Boolean getCustomerResponse(HttpServletRequest req) {
		String desc = req.getParameter("a");
		return Boolean.valueOf(desc);
	}
}
