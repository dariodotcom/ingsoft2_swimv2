package it.polimi.swim.web.pagesupport;

/**
 * ErrorType is an enumeration which provides informations about an occurred
 * error in order to notify it to the user.
 */
public enum ErrorType {

	BAD_REQUEST(
			"Bad Request",
			"C'è un errore nella tua richiesta. Se hai digitato l'url controlla di non aver fatto errori."), GENERIC(
			"Unknown Error",
			"Sembra che uno degli sviluppatori sia stato pigro nel trattare questo errore..."),
	LOGIN_REQUIRED(
			"Login Required",
			"Non puoi accedere alla funzionalità che hai richiesto perchè essa è disponibile solo per gli utenti registrati."
			);

	private String errorName, errorDescription;

	/**
	 * Constructor method.
	 * 
	 * @param errorName
	 *            a String displayed as the name of an error.
	 * @param errorDescription
	 *            a String displayed as a description of an error.
	 */
	ErrorType(String errorName, String errorDescription) {
		this.errorName = errorName;
		this.errorDescription = errorDescription;
	}

	/**
	 * Getter method implemented in order to provide the name of an error.
	 * 
	 * @return the name of an error.
	 */
	public String getErrorName() {
		return errorName;
	}

	/**
	 * Getter method implemented in order to provide the description of an
	 * error.
	 * 
	 * @return the description of an error.
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
}
