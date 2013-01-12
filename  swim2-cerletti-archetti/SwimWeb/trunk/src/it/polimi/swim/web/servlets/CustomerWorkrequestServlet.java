package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.FeedbackControllerRemote;
import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.bean.remote.WorkRequestControllerRemote;
import it.polimi.swim.business.entity.Feedback;
import it.polimi.swim.business.entity.WorkRequest;
import it.polimi.swim.business.exceptions.BadRequestException;
import it.polimi.swim.business.exceptions.InvalidStateException;
import it.polimi.swim.business.exceptions.UnauthorizedRequestException;
import it.polimi.swim.web.pagesupport.CustomerMenu;
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
 * Servlet implementation class UserWorkrequestServlet.
 */
public class CustomerWorkrequestServlet extends SwimServlet {

	private static final long serialVersionUID = -4086600990410926694L;

	public static final String CONTEXT_NAME = "works";
	public final static String WORK_REQUEST_PARAM = "w";

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

		registerPostActionMapping("complete", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doMarkRequestAsCompleted(req, resp);
			}
		});

		registerPostActionMapping("sendmsg", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doSendMessage(req, resp);
			}
		});

		registerPostActionMapping("insertfeedback", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				doInsertFeedback(req, resp);
			}
		});

		registerPostActionMapping("replyfeedback", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
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

		redirectToRequestView(req, resp, reqId);
	}

	private void doMarkRequestAsCompleted(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String loggedUsername = getUsername(session);
		Integer reqId = getWorkRequestId(req);

		if (reqId == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		WorkRequestControllerRemote workReqCtrl = lookupBean(
				WorkRequestControllerRemote.class, Misc.BeanNames.WORKREQUEST);

		try {
			workReqCtrl.markRequestAsCompleted(loggedUsername, reqId);
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

		redirectToRequestView(req, resp, reqId);
	}

	private void doSendMessage(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String username = getUsername(session);

		Integer reqId = getWorkRequestId(req);
		String text = (String) req.getParameter("messageText");

		if (Misc.isStringEmpty(text) || reqId == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		WorkRequestControllerRemote workReqCtrl = lookupBean(
				WorkRequestControllerRemote.class, Misc.BeanNames.WORKREQUEST);

		try {
			workReqCtrl.sendMessage(username, text, reqId);
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

		redirectToRequestView(req, resp, reqId);
	}

	private void doInsertFeedback(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String authorUsr = getUsername(session);
		Integer reqId = getWorkRequestId(req);
		String review = req.getParameter("review");
		Integer mark = getMark(req);

		if (reqId == null || review == null || mark == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		FeedbackControllerRemote feedbackCtrl = lookupBean(
				FeedbackControllerRemote.class, Misc.BeanNames.FEEDBACK);

		try {
			feedbackCtrl.createFeedback(reqId, authorUsr, mark, review);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		} catch (UnauthorizedRequestException e) {
			sendError(req, resp, ErrorType.UNAUTHORIZED_REQUEST);
			return;
		} catch (InvalidStateException e) {
			sendError(req, resp, ErrorType.INVALID_REQUEST);
			return;
		}

		redirectToRequestView(req, resp, reqId);
	}

	private void doReplyToFeedback(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		Integer reqId = getWorkRequestId(req);
		String username = getUsername(session);
		String reply = req.getParameter("reply");

		if (reqId == null || Misc.isStringEmpty(reply)) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		FeedbackControllerRemote feedbackCtrl = lookupBean(
				FeedbackControllerRemote.class, Misc.BeanNames.FEEDBACK);

		try {
			feedbackCtrl.replyToFeedback(reqId, username, reply);
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

		redirectToRequestView(req, resp, reqId);
	}

	private void showSection(CustomerWorkRequestSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String username = getUsername(session);
		List<?> sentRequest, receivedRequest;

		UserProfileControllerRemote profileCtrl = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		if (section.equals(CustomerWorkRequestSection.ACTIVE_REQUESTS)) {
			sentRequest = profileCtrl.getSentActiveWorkRequest(username);
			receivedRequest = profileCtrl
					.getReceivedActiveWorkRequest(username);
		} else {
			sentRequest = profileCtrl.getSentArchivedWorkRequest(username);
			receivedRequest = profileCtrl
					.getReceivedArchivedWorkRequest(username);
		}

		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.WORKS);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);

		req.setAttribute(Misc.SENT_WORKREQUEST_LIST, sentRequest);
		req.setAttribute(Misc.RECEIVED_WORKREQUEST_LIST, receivedRequest);

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

		List<?> messageList;

		try {
			messageList = workReqCtrl.getMessageList(reqId);
		} catch (BadRequestException e) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
			return;
		}

		Feedback f = workReqCtrl.getFeedback(reqId);

		req.setAttribute(Misc.FEEDBACK, f);
		req.setAttribute(Misc.MESSAGE_LIST, messageList);
		req.setAttribute(Misc.TARGET_WORKREQUEST, workReq);
		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.WORKS);
		req.getRequestDispatcher(Misc.VIEW_WORKREQUEST_JSP).forward(req, resp);
	}

	/* Helpers */
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

	private Integer getMark(HttpServletRequest req) {
		try {
			return Integer.parseInt(req.getParameter("mark"));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void redirectToRequestView(HttpServletRequest req,
			HttpServletResponse resp, int reqId) throws IOException {
		String link = String.format("%s/%s/view?%s=%s", req.getContextPath(),
				CONTEXT_NAME, WORK_REQUEST_PARAM, reqId);
		resp.sendRedirect(link);
	}

	/* Enumerations */

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
}