package foodbank.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AutomatedEmailer {

	final String senderEmailID = "foodbank.inventory@gmail.com";
	final String senderPassword = "bunanas123";
	final String emailSMTPserver = "smtp.gmail.com";
	final String emailServerPort = "465";
	String receiverEmailID = null;
	String emailSubject = "Test Mail";
	String emailBody = "Test Body";
	
	public AutomatedEmailer(String receiverEmailID, String emailSubject, String emailBody) throws Exception {
		this.receiverEmailID = receiverEmailID;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;
		Properties props = new Properties();
		props.put("mail.smtp.user", senderEmailID);
		props.put("mail.smtp.host", emailSMTPserver);
		props.put("mail.smtp.port", emailServerPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", emailServerPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		SecurityManager security = System.getSecurityManager();
		// Exceptions with respect to email sending will be propagated out of the constructor
		Authenticator auth  = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		MimeMessage msg = new MimeMessage(session);
		msg.setText(emailBody);
		msg.setSubject(emailSubject);
		msg.setFrom(new InternetAddress(senderEmailID));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
		Transport.send(msg);
	}
	
	// Inner class to ensure sender's email is authenticated
	class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(senderEmailID, senderPassword);
		}
	}
	
}
