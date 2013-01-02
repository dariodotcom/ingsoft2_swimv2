package it.polimi.swim.web.pagesupport;

/**
 * ErrorType is an enumeration which provides informations about an occurred
 * error in order to notify it to the user.
 */
public enum ErrorType {

	BAD_REQUEST(
			"Bad Request",
			"C'� un errore nella tua richiesta. Se hai digitato l'url controlla di non aver fatto errori."),
	INVALID_REQUEST(
			"Richiesta non valida",
			"Non � possibile portare a termine la richiesta effettuata perch� non � valida."),
	UNAUTHORIZED_REQUEST(
			"Richiesta non autorizzata",
			"Non sei autorizzato ad effettuare la richiesta da te inviata."
			),
	GENERIC(
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
			"Le credenziali inserite non sono valide."
			), 
	BAD_DATE(
			"Data non valida",
			"La data inserita non � valida."
			),
	BAD_ABILITY_NAME(
			"Nome non valido",
			"Professionalit&grave; inserita non valida"
			), 
	ALREADY_DECLARED_ABILITY(
			"Professionalit&agrave; gi&agrave; dichiarata",
			"Hai gi&agrave; dichiarato tale professionalit&agrave;"
			),
	ABILITY_NOT_DECLARED(
			"Professionalit&agrave; non dichiarata",
			"Non hai dichiarato tale la professionalit&agrave;."
			),
	ABILITY_NAME_TAKEN(
			"Nome professionalit&agrave; non disponibile",
			"Il nome della professionalit&agrave; inserita non &egrave; disponibile."
			),
	INVALID_ABILITY_SELECTION(
			"Selezione professionalit&agrave; non valida",
			"La selezione della professionalit&agrave; del destinatario non � valida."
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
