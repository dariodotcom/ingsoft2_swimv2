package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
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
 * Servlet implementation class FeedbackServlet.
 */
public class CustomerFeedbackServlet extends SwimServlet {

	private static final long serialVersionUID = 5352393029820748102L;

	public static final String CONTEXT_NAME = "feedbacks";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerFeedbackServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		/* GET request actions */

		/* Received feedbacks */
		registerGetActionMapping(
				CustomerFeedbackSection.RECEIVED_FEEDBACKS
						.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(CustomerFeedbackSection.RECEIVED_FEEDBACKS,
								req, resp);
					}
				});

		/* Sent feedbacks */
		registerGetActionMapping(
				CustomerFeedbackSection.SENT_FEEDBACKS.getSectionIdentifier(),
				new ServletAction() {
					public void runAction(HttpServletRequest req,
							HttpServletResponse resp) throws IOException,
							ServletException {
						showSection(CustomerFeedbackSection.SENT_FEEDBACKS,
								req, resp);
					}
				});

		/* POST request actions */

		registerPostActionMapping("/respond", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				respondToReceivedFeedback(req, resp);
			}
		});

	}

	/* Methods to respond to different requests */

	private void respondToReceivedFeedback(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

	}

	private void showSection(CustomerFeedbackSection section,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession();

		if (!isCustomerLoggedIn(session)) {
			sendError(req, resp, ErrorType.LOGIN_REQUIRED);
			return;
		}

		String username = getUsername(session);
		List<?> feedbackList = null;

		UserProfileControllerRemote profileCtrl = lookupBean(
				UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

		switch (section) {
		case SENT_FEEDBACKS:
			feedbackList = profileCtrl.getSentFeedbacks(username);
			break;
		case RECEIVED_FEEDBACKS:
			feedbackList = profileCtrl.getReceivedFeedbacks(username);
			break;
		}

		req.setAttribute(Misc.FEEDBACK_LIST, feedbackList);

		req.setAttribute(Misc.SELECTED_TAB_ATTR, CustomerMenu.FEEDBACKS);
		req.setAttribute(Misc.SELECTED_SECTION_ATTR, section);
		req.getRequestDispatcher(Misc.FEEDBACKS_JSP).forward(req, resp);
		return;
	}

	/* Enumerations */
	
	/**
	 * CustomerFeedbackSection is an enumeration useful to provide all the
	 * possible sections accessible from the feedback page of a logged user.
	 */
	public enum CustomerFeedbackSection {

		RECEIVED_FEEDBACKS("Feedback ricevuti", ""), SENT_FEEDBACKS(
				"Feedback inviati", "sent");

		private String sectionName, sectionIdentifier;

		private CustomerFeedbackSection(String sectionName,
				String sectionIdentifier) {
			this.sectionIdentifier = sectionIdentifier;
			this.sectionName = sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the name of this
		 *         CustomerFeedbackSection.
		 */
		public String getSectionName() {
			return sectionName;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the identifier of this
		 *         CustomerFeedbackSection.
		 */
		public String getSectionIdentifier() {
			return sectionIdentifier;
		}
	}
}