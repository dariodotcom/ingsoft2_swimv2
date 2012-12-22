package it.polimi.swim.web.pagesupport;

public enum AdminMenu implements MenuDescriptor {

	MANAGEMENT("Gestione", "/admin/", "management");

	private String tabName, tabLink, elemId;

	AdminMenu(String tabName, String tabLink, String elemId) {
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
