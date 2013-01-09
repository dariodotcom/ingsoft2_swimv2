package it.polimi.swim.web.pagesupport;

/**
 * NotificationMessages is an enumeration that contains all the possible
 * notification a user can visualize.
 */
public enum NotificationMessages {

	EMAIL_CHANGED("L'indirizzo email è stato modificato."),
	PASSWORD_CHANGED("La password è stata modificata."),
	DETAILS_CHANGED("I tuoi dati sono stati aggiornati."),
	ABILITY_ADDED("La nuova professionalit&agrave; è stata aggiunta."),
	REQUEST_SENT("La richiesta è stata inviata."),
	ABILITY_REMOVED("L'abilit&agrave; &egrave; stata rimossa."),
	EMAIL_VALIDATED("L'indirizzo email è stato convalidato."),
	ABILITY_REQ_REFUSED("La richiesta è stata rifiutata."),
	PHOTO_CHANGED("La tua foto del profilo è stata modificata.");

	private String description;

	NotificationMessages(String description) {
		this.description = description;
	}

	/**
	 * Getter method.
	 * 
	 * @return a String that contains this notification.
	 */
	public String getDescription() {
		return description;
	}
}
