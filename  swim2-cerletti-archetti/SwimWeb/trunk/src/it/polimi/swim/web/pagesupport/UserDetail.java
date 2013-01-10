package it.polimi.swim.web.pagesupport;

public class UserDetail {
	private String detailName;
	private Boolean detailPrivate;
	private String value;

	public UserDetail(String detailName, Boolean detailPrivate, String value) {
		this.detailName = detailName;
		this.detailPrivate = detailPrivate;
		this.value = value;
	}

	public String getDetailName() {
		return detailName;
	}

	public Boolean isDetailPrivate() {
		return detailPrivate;
	}

	public String getValue() {
		return value;
	}
}
