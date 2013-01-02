package it.polimi.swim.web.pagesupport;

public enum NotificationMessages {

	EMAIL_CHANGED("L'indirizzo email � stato modificato."),
	PASSWORD_CHANGED("La password � stata modificata."),
	DETAILS_CHANGED("I tuoi dati sono stati aggiornati."),
	ABILITY_ADDED("La nuova abilit&agrave; � stata aggiunta."),
	REQUEST_SENT("La richiesta � stata inviata."),
	ABILITY_REMOVED("L'abilit&agrave; &egrave; stata rimossa.");
	
	private String description;
	
	NotificationMessages(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
