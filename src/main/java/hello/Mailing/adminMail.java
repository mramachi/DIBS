package hello.mailing;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class adminMail implements Runnable {

	String sender; String receiver; String Subject; String content;
	
	public adminMail(String s, String r, String ss, String c){
		sender=s;
		receiver=r;
		Subject=ss;
		content=c;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String from = sender;// change accordingly
		final String username = sender;
		final String password = "";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.atlantis.UGent.be");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.connectiontimeout", "200");
	    props.put("mail.smtp.timeout", "200");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			
			String text = content;
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

			message.setSubject(Subject);

			message.setText(content);

			Transport.send(message);
			
			System.err.println("message send");
		} catch (MessagingException e) {
			System.err.println("message not send");
			throw new RuntimeException(e);
	}
	
	}
	
}