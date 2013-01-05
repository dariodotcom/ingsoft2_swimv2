package it.polimi.swim.web.pagesupport;

import java.io.IOException;

public class MailSender {

	private static final String pwdResetStartMessage = "Hai"
			+ " richiesto di resettare la tua password. Per confermare,"
			+ " clicca sul link sottostante. Se non hai effettuato tu la"
			+ " richiesta, ignora questa mail.\n\n";

	private static final String pwdResetStartSubject = "Richiesta di reset password.";

	private static final String pwdResetConfMessage = "La password è stata reimpostata."
			+ "\n\nLa tua nuova password è %s. Ora puoi accedere a Swim con la nuova "
			+ "password e, una volta autenticato, potrai cambiare la password";

	private static final String pwdResetConfSubject = "Password reimpostata.";

	private static final String emailConfirmationMessage = "Per convalidare il tuo "
			+ "indirizzo email, copia e incolla nel tuo browser il link sottostante.\n\n";

	private static final String emailConfirmationSubject = "Conferma email swim";

	public static void sendPasswordResetStartEmail(String customerMail,
			String resetLink) {
		MailMessage email = new MailMessage(customerMail, pwdResetStartSubject,
				pwdResetStartMessage + resetLink);

		try {
			email.sendMail();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendPasswordResetConfirmEmail(String customerMail,
			String newPassword) {
		MailMessage email = new MailMessage(customerMail, pwdResetConfSubject,
				String.format(pwdResetConfMessage, newPassword));

		try {
			email.sendMail();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendValidationEmail(String email, String link) {
		String message = emailConfirmationMessage + link;

		MailMessage mess = new MailMessage(email, emailConfirmationSubject,
				message);

		try {
			mess.sendMail();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
