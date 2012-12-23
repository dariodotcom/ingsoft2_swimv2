package it.polimi.swim.web.pagesupport;

public enum ErrorType {

	BAD_REQUEST(
			"Bad Request",
			"C'è un errore nella tua richiesta. Se hai digitato l'url controlla di non aver fatto errori."),
	GENERIC(
			"Unknown Error",
			"Sembra che uno degli sviluppatori sia stato pigro nel trattare questo errore...");

	private String errorName, errorDescription;

	ErrorType(String errorName, String errorDescription) {
		this.errorName = errorName;
		this.errorDescription = errorDescription;
	}

	public String getErrorName() {
		return errorName;
	}

	public String getErrorDescription() {
		return errorDescription;
	}
}
