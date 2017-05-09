package hello.service;

import java.io.IOException;
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

import hello.model.Event;
import hello.model.Offer;
import hello.model.Purchase;
import hello.model.User;
import hello.repo.OfferRepository;
import hello.repo.PurchaseRepository;
import hello.repo.UserRepository;

@Service
public class OfferHandler implements Runnable {

	long[] seatToBook;
	public long event;
	public User user;

	@Autowired
	OfferRepository offerRepo;
	@Autowired
	PurchaseRepository purchaserepo;
	@Autowired
	UserRepository userRepo;

	public void setSeatToBook(long[] seats) {
		this.seatToBook = seats;
	}

	public void setEvent(long eventid) {
		this.event = eventid;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		// dataservice
		System.out.println("test db");

		/*
		// ticket op nul zetten
		for (long seat : seatToBook) {
			Offer s = offerRepo.findByOfferID(seat);
			System.out.println("AAA");
			s.setOfferTicksAvl(0);
			offerRepo.saveAndFlush(s);
			System.out.println("BBB");
			
			System.out.println("CCC");

			Timestamp purchaseTime = new Timestamp((new java.util.Date()).getTime());
			
			System.out.println("DDD");
			Purchase p = new Purchase(s, user, purchaseTime);
			System.out.println("EEEE");
			purchaserepo.saveAndFlush(p);
			System.out.println("FFFF");

		}

		 */
		// verzenden van mail

		
		String from = "lisadujardin4164@gmail.com";// change accordingly
		final String username = "sneuvill";
		final String password = "";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.atlantis.UGent.be");
		props.put("mail.smtp.port", "25");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		/*
		try {
			System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
			String text = readFile("MailConfirmationOrder.txt", Charset.defaultCharset());
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("moustapha.ramachi@gmail.com"));

			message.setSubject("let's do this");

			message.setContent(text, "text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	private String readFile(String path, Charset encoding) throws IOException {
		// TODO Auto-generated method stub
		 byte[] encoded = Files.readAllBytes(Paths.get(path));
		 return new String(encoded, encoding);
		  
	}

}
