package hello.mailing;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mail {
	
	public static void sendmail(){
		
		String from = "random@random.be";//change accordingly
    	final String username = "mramachi";
		final String password = "ramachijdqfkjqdkfjkdqjfkjqdkfjklqdjklfjqdlfjkqsdjfjqdkjfklqdjfkljqdklfjklqdjfklqdjklfjklqdsjfklmqdjfjqdjflm12";
		System.out.println(3);
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.atlantis.ugent.be");
		props.put("mail.smtp.port", "25");
		System.out.println(4);
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		System.out.println(4);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			
			
			/*message.setRecipients(Message.RecipientType.TO,
					//InternetAddress.parse("vop-dibs@googlegoups.com"));
					//InternetAddress.parse("moustapha.ramachi@gmail.com"));
					InternetAddress.parse("vanlaere.stephanie@gmail.com "));
			*/
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse("vanlaere.stephanie@gmail.com , si.neuville@gmail.com , moustapha.ramachi@gmail.com"));
			message.setSubject("Evaluatie VOP");
			message.setText("Dag "
				+ "\n\n Na wat overleg hebben we besloten dat het werk van vop groep dibs onvoldoende is geacht. " + "\n" + 
					"Bij deze laten wij u dus op voorhand weten dat u negatief zal worden beoordeeld op dit opleidingsonderdeel."
				+ " . Voor meer feedback verwijs ik u jullie graag door naar komende woensdag?" + 
					"Zie dit als een vroegtijdige waarschuwing, het heeft weinig zin om de komende week nog "+
				 "veel energie in jullie vop te steken." +"\n \n"
					+ " Met vriendelijke groeten" + "\n" + "Femke");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    	
    	
		
		
	}

}
