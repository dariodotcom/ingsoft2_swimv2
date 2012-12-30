package it.polimi.swim.web.pagesupport;

public class UserDetail {
	private String detailName;
	private Boolean detailPrivate;
	private String getterName;

	public UserDetail(String detailName, Boolean detailPrivate,
			String getterName) {
		this.detailName = detailName;
		this.detailPrivate = detailPrivate;
		this.getterName = getterName;
	}

	public String getDetailName() {
		return detailName;
	}

	public Boolean isDetailPrivate() {
		return detailPrivate;
	}

	public String getGetterName() {
		return getterName;
	}
}
