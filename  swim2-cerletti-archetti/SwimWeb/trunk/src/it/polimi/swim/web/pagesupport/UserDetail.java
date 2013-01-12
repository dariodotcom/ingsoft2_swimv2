package it.polimi.swim.web.pagesupport;

/**
 * UserDetail is a class that manages the details given by a user and their
 * privacy.
 */
public class UserDetail {
	private String detailName;
	private Boolean detailPrivate;
	private String value;

	/**
	 * Class constructor.
	 * 
	 * @param detailName
	 *            a String that contains the name which identifies a detail.
	 * @param detailPrivate
	 *            a Boolean value that is true if the detail has not to be
	 *            shown, false otherwise.
	 * @param value
	 *            a String that contains the value associated to the given
	 *            detail.
	 */
	public UserDetail(String detailName, Boolean detailPrivate, String value) {
		this.detailName = detailName;
		this.detailPrivate = detailPrivate;
		this.value = value;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the name of this UserDetail.
	 */
	public String getDetailName() {
		return detailName;
	}

	/**
	 * Getter method.
	 * 
	 * @return a Boolean value that contain the privacy of this UserDetail.
	 */
	public Boolean isDetailPrivate() {
		return detailPrivate;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the value of this UserDetail.
	 */
	public String getValue() {
		return value;
	}
}
