package it.polimi.swim.web.pagesupport;

/**
 * AdminMenu is an enumeration which implements interface MenuDescriptor in
 * order to provide informations on the menu displayed to the administrator.
 */
public enum AdminMenu implements MenuDescriptor {

	MANAGEMENT("Gestione", "/admin/", "management");

	private String tabName, tabLink, elemId;

	/**
	 * Constructor method.
	 * 
	 * @param tabName
	 *            a String displayed as the name of the tab.
	 * @param tabLink
	 *            a String which identifies the path of the link of the tab.
	 * @param elemId
	 *            a String which identifies the HTML identifier of the tab.
	 */
	AdminMenu(String tabName, String tabLink, String elemId) {
		this.elemId = elemId;
		this.tabLink = tabLink;
		this.tabName = tabName;
	}

	/**
	 * @see MenuDescriptor
	 */
	public String getTabName() {
		return tabName;
	}

	/**
	 * @see MenuDescriptor
	 */
	public String getElementId() {
		return elemId;
	}

	/**
	 * @see MenuDescriptor
	 */
	public String getTabLink() {
		return tabLink;
	}
}
