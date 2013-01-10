package it.polimi.swim.web.pagesupport;

import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.web.servlets.CustomerImageServlet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Misc {

	/* Request attibutes definitions */

	public static final String PAGE_TITLE_ATTR = "pageTitle";
	public static final String SELECTED_SECTION_ATTR = "selectedSection";
	public static final String SELECTED_TAB_ATTR = "selectedTab";
	public static final String USER_TO_SHOW = "userToShow";

	public static final String BASE_PAGE_TITLE = "Swim";
	public static final String PAGE_TITLE_SEP = " | ";

	public static final String ERROR_ATTR = "errorType";
	public static final String NOTIFICATION_ATTR = "notificationType";

	public static final String FRIENDSHIP_STATUS = "friendshipStatus";
	public static final String FRIENDLIST_ATTR = "friendlist";

	public static final String LOGGED_ATTRIBUTE = "loggedIn";
	public static final String LOGGED_USERNAME = "loggedUsername";
	public static final String LOGGED_USERTYPE = "loggedUserType";

	public static final String ABILITY_LIST = "abilityList";

	public static final String ABILITY_NAME = "abilityName";
	public static final String TARGET_WORKREQUEST = "targetWorkRequest";

	public static final String SENT_WORKREQUEST_LIST = "workRequestList";
	public static final String RECEIVED_WORKREQUEST_LIST = "receivedRequest";

	public static final String FEEDBACK_LIST = "feedbackList";

	public static final String MESSAGE_LIST = "messageList";

	public static final String MARK_VALUE = "markValue";

	// JSP list
	public static final String HOME_JSP = "/home.jsp";
	public static final String ERROR_JSP = "/error.jsp";
	public static final String LANDING_JSP = "/landing.jsp";
	public static final String FEEDBACKS_JSP = "/feedbacks.jsp";
	public static final String WORKREQUEST_LIST_JSP = "/workrequestlist.jsp";
	public static final String FRIENDS_JSP = "/friends.jsp";
	public static final String USER_JSP = "/user.jsp";
	public static final String SEARCH_JSP = "/search.jsp";
	public static final String ABOUT_JSP = "/about.jsp";
	public static final String ADMIN_JSP = "/admin.jsp";
	public static final String CREATE_WORKREQUEST_JSP = "/create_workrequest.jsp";
	public static final String VIEW_WORKREQUEST_JSP = "/view_workrequest.jsp";
	public static final String MAILVALIDATION_JSP = "/mailvalidation.jsp";
	public static final String PASSWORD_RESET_JSP = "/passwordreset.jsp";
	public static final String ABILITY_REQUEST_JSP = "/suggestability.jsp";

	/* Utils */

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy");
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm");
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");

	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int MAX_MARK = 5;
	public static final String FEEDBACK = "feedback";
	public static final String MAIL_VALIDATION_PENDING = "mailValidationPending";
	public static final String CUSTOMER_EMAIL = "customerEmail";
	public static final String SEARCH_RESULTS = "searchResults";
	public static final String DEFAULT_PHOTO_FULL_URL = "/resources/user.png";
	public static final String DEFAULT_PHOTO_THUMB_URL = "/resources/user_th.png";
	public static final String CUSTOMER_PHOTO_FULL_URL = "/userphoto/full.png";
	public static final String CUSTOMER_PHOTO_THUMB_URL = "/userphoto/thumb.png";

	private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;";

	/* Helpers */

	/**
	 * This method converts a given date element into its string representation.
	 * 
	 * @param d
	 *            a Date.
	 * @return a String that represents the given date.
	 */
	public static String parseDate(Date d) {
		return (d == null ? "" : DATE_FORMAT.format(d));
	}

	/**
	 * This method replaces the given String input with given replacement if
	 * input is null or empty.
	 * 
	 * @param input
	 *            a String that contains the input value.
	 * @param replacement a
	 *            String that contains the replacement
	 * @return the String corresponding to the given input.
	 */
	public static String replaceEmpty(String input, String replacement) {
		return isStringEmpty(input) ? replacement : input;
	}

	/**
	 * This method analyzes a given input and returns true if it contains a null
	 * or empty value.
	 * 
	 * @param input
	 *            a String that contains the input value.
	 * @return true if the input paramether is null or empty.
	 */
	public static boolean isStringEmpty(String input) {
		return input == null || input.length() == 0;
	}

	public static boolean isEmailValid(String email) {
		return Pattern.matches(emailPattern, email);
	}

	/**
	 * This method assigns the false value to a given boolean that has not been
	 * set.
	 * 
	 * @param b
	 *            a boolean value.
	 * @return false if the given boolean is set to false or null, true
	 *         otherwise.
	 */
	public static boolean boolValueOf(Boolean b) {
		return (b == null ? false : b);
	}

	public static String getPhotoUrl(Customer c) {
		return String.format("%s?%s=%s", CUSTOMER_PHOTO_FULL_URL,
				CustomerImageServlet.USER_PARAM, c.getUsername());
	}

	public static String getThumbnailPhotoUrl(Customer c) {
		return String.format("%s?%s=%s", CUSTOMER_PHOTO_THUMB_URL,
				CustomerImageServlet.USER_PARAM, c.getUsername());
	}

	/**
	 * Class BeanNames contains a set of value that represents the name of all
	 * the beans.
	 */
	public static class BeanNames {
		public final static String AUTHENTICATION = "AuthenticationController/remote";
		public final static String PROFILE = "UserProfileController/remote";
		public static final String FRIENDSHIP = "FriendshipController/remote";
		public static final String ABILITY = "AbilityController/remote";
		public static final String WORKREQUEST = "WorkRequestController/remote";
		public static final String FEEDBACK = "FeedbackController/remote";
		public static final String SEARCH = "SearchController/remote";
	}

	/**
	 * FrienshipStatus is an enumeration which purpose is to represent all the
	 * possible values related to friendship requests on buttons in the profile
	 * of a generic user different from the current one.
	 */
	public enum FriendshipStatus {
		NOT_FRIENDS("Invia richiesta di amicizia", "sendable"), CONFIRMATION_AWAITED(
				"Amicizia in attesa di conferma", "awaited"), ALREADY_FRIENDS(
				"Siete amici", "disabled"), FRIENDSHIP_UNAVAILABLE(
				"Non sei registrato", "disabled");

		private String buttonText;
		private String buttonClass;

		private FriendshipStatus(String buttonText, String buttonClass) {
			this.buttonClass = buttonClass;
			this.buttonText = buttonText;
		}

		/**
		 * Getter method.
		 * 
		 * @return a String that contains the value present on this button.
		 */
		public String getButtonText() {
			return buttonText;
		}

		/**
		 * Getter method
		 * 
		 * @return a String which says if the request on the button is sendable,
		 *         awaited or disabled.
		 */
		public String getButtonClass() {
			return buttonClass;
		}
	}
}
