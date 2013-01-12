package it.polimi.swim.web.pagesupport;

/**
 * FrienshipStatus is an enumeration which purpose is to represent all the
 * possible values related to friendship requests on buttons in the profile of a
 * generic user different from the current one.
 */
public enum FriendshipStatus {
	NOT_FRIENDS("Invia richiesta di amicizia"), CONFIRMATION_AWAITED(
			"Amicizia in attesa di conferma"), ALREADY_FRIENDS("Siete amici"), FRIENDSHIP_UNAVAILABLE(
			"Non sei registrato");

	private String buttonText;

	private FriendshipStatus(String buttonText) {
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
}