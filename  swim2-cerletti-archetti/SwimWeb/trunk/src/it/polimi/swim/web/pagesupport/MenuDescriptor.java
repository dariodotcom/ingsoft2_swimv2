package it.polimi.swim.web.pagesupport;

/**
 * MenuDescriptor is an interface which purpose is to retrieve informations
 * about menu elements.
 */
public interface MenuDescriptor {
	/**
	 * Getter method implemented in order to provide the visualized name of the
	 * tab.
	 * 
	 * @return the name of the tab in the current page
	 */
	public String getTabName();

	/**
	 * Getter method implemented in order to provide the HTML identifier of the
	 * tab.
	 * 
	 * @return the HTML identifier of the tab.
	 */
	public String getElementId();

	/**
	 * Getter method implemented in order to provide the visualized link of the
	 * tab.
	 * 
	 * @return the link of the tab.
	 */
	public String getTabLink();
}
