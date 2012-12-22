package it.polimi.swim.web.pagesupport;

public enum CustomerMenu implements MenuDescriptor{
		
	HOME("Home", "/home/", "home"),
	SEARCH("Search", "/search/", "search"),
	WORKS("Lavori", "/works/", "works"),
	FEEDBACKS("Feedback", "/feedbacks/", "feedbacks"),
	FRIENDS("Amici", "/friends/", "friends");
	
	private String tabName, tabLink, elemId;
	
	CustomerMenu(String tabName, String tabLink, String elemId){
		this.elemId =elemId;
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
