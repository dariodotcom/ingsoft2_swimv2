package it.polimi.swim.web.pagesupport;

/**
 * ErrorType is an enumeration which provides informations about an occurred
 * error in order to notify it to the user.
 */
public enum ErrorType {

	BAD_REQUEST(
			"Bad Request",
			"C'� un errore nella tua richiesta. Se hai digitato l'url controlla di non aver fatto errori."), GENERIC(
			"Unknown Error",
			"Sembra che uno degli sviluppatori sia stato pigro nel trattare questo errore..."),
	LOGIN_REQUIRED(
			"Login Required",
			"Non puoi accedere alla funzionalit� che hai richiesto perch� essa � disponibile solo per gli utenti registrati."
			),
	BAD_EMAIL(
			"Email non valida",
			"L'indirizzo email inserito non � valido."
			),
	INCORRECT_PASSWORD(
			"Password non corretta",
			"La password corrente inserita non � corretta."
			),
	INVALID_PASSWORD(
			"Password non valida",
			"La nuova password inserita inserita non � valida."
			),
	EMAIL_NOT_AVAILABLE(
			"Email non libera",
			"L'indirizzo email inserito � gi� in uso."
			),
	EMPTY_FIELDS(
			"Empty Fields",
			"Alcuni campi obbligatori sono vuoti."
			),
	USERNAME_NOT_AVAILABLE(
			"Username non disponibile",
			"Lo username che hai scelto non � disponibile."
			),
	INVALID_CREDENTIALS(
			"Credenziali non valide",
			"Le credenziali inserite non sono valide"
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
