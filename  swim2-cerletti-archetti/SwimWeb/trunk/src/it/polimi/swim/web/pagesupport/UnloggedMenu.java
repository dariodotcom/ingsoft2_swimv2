package it.polimi.swim.web.pagesupport;

public enum UnloggedMenu implements MenuDescriptor {

	HOME("Benvenuto", "/", "landing"),
	SEARCH("Cos'è Swim", "/about/", "about");

	private String tabName, tabLink, elemId;

	UnloggedMenu(String tabName, String tabLink, String elemId) {
		this.elemId = elemId;
		this.tabLink = tabLink;
		this.tabName = tabName;
	}

	public String getTabName() {
		return tabName;
	}

	public String getElementId() {
		return elemId;
	}

	public String getTabLink() {
		return tabLink;
	}
}
