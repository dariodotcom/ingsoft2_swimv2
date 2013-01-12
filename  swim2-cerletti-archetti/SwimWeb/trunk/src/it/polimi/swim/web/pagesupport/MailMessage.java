package it.polimi.swim.web.pagesupport;

import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * MailMessage is a class which manages the messages that have to be sent to a
 * user via email.
 */
public class MailMessage {

	private final String platformMail = "heartripperdesign@gmail.com";
	private final String platformMailPassword = "swimpassword";

	private String to;
	private String subject;
	private String message;

	/**
	 * Class constructor.
	 * 
	 * @param to
	 *            a String that identifies the receiver of a message.
	 * @param subject
	 *            a String that contains the subject of a message.
	 * @param message
	 *            a String that is the content of a message.
	 */
	public MailMessage(String to, String subject, String message) {
		this.to = to;
		this.subject = "[SWIM] " + subject;
		this.message = message;
	}

	/**
	 * This method provides informations about the status of a mail messages.
	 * 
	 * @return true if the message has been sent correctly, false otherwise.
	 * @throws IOException
	 *             there is an error in the input.
	 */
	public boolean sendMail() throws IOException {

		try {
			Properties props = new Properties();
			props.setProperty("mail.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.starttls.enable", "true");

			Authenticator auth = new SMTPAuthenticator(platformMail,
					platformMailPassword);

			Session session = Session.getInstance(props, auth);

			MimeMessage msg = new MimeMessage(session);
			msg.setText(message);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(platformMail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(msg);

		} catch (AuthenticationFailedException ex) {
			return false;
		} catch (AddressException ex) {
			return false;
		} catch (MessagingException ex) {
			return false;
		}
		return true;
	}

	private class SMTPAuthenticator extends Authenticator {

		private PasswordAuthentication authentication;

		public SMTPAuthenticator(String login, String password) {
			authentication = new PasswordAuthentication(login, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}