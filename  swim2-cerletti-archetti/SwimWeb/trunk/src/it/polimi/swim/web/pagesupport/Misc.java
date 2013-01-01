package it.polimi.swim.web.pagesupport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Misc {

	// Request attibutes definitions
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

	// Utils

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy");

	public static final int MIN_PASSWORD_LENGTH = 8;


	// Helpers

	public static String parseDate(Date d) {
		return (d == null ? "" : DATE_FORMAT.format(d));
	}

	public static String nullfix(String input) {
		return input == null ? "" : input;
	}

	public static boolean isStringEmpty(String input) {
		return input == null || input.length() == 0;
	}

	public static class BeanNames {
		public final static String AUTHENTICATION = "AuthenticationController/remote";
		public final static String PROFILE = "UserProfileController/remote";
		public static final String FRIENDSHIP = "FriendshipController/remote";
	}

	// Enums
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

		public String getButtonText() {
			return buttonText;
		}

		public String getButtonClass() {
			return buttonClass;
		}
	}
}
