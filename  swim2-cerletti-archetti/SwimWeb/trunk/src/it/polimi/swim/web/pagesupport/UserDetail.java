package it.polimi.swim.web.pagesupport;

/**
 * UserDetail is a class that manages the details of a user profile, i.e. name,
 * surname, birth date, location.
 * 
 */
public class UserDetail {
	private String detailName;
	private Boolean detailPrivate;
	private String getterName;

	/**
	 * Class constructor.
	 * 
	 * @param detailName
	 *            a String that contains the name of a detail of the user
	 *            profile.
	 * @param detailPrivate
	 *            a Boolean value that indicates whether the detail is a private
	 *            field or not.
	 * @param getterName
	 *            a String that contains the value inserted for the detail.
	 */
	public UserDetail(String detailName, Boolean detailPrivate,
			String getterName) {
		this.detailName = detailName;
		this.detailPrivate = detailPrivate;
		this.getterName = getterName;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the name of the detail.
	 */
	public String getDetailName() {
		return detailName;
	}

	/**
	 * Getter method.
	 * 
	 * @return true if the detail is private, false otherwise.
	 */
	public Boolean isDetailPrivate() {
		return detailPrivate;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains the value of a detail.
	 */
	public String getGetterName() {
		return getterName;
	}
}
