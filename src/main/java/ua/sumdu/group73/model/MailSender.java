package ua.sumdu.group73.model;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailSender {

	private static final Logger log = Logger.getLogger(ConnectionDB.class);
	private static final String CLASSNAME = "MailSender: ";
	
    private static MailSender instance;
    
    public static synchronized MailSender getInstance() {
    	log.info(CLASSNAME + "Method MailSender starts.....");
        if (instance == null) {
        	log.info(CLASSNAME + "Creates new instance.....");
        	instance = new MailSender();
        }
        return instance;
    }
	
    private String username = "auction.lab3@gmail.com";
    private String password = "auction3";
    private Properties props;

    private MailSender() {
    	log.info(CLASSNAME + "Constructor starts.....");
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void send(String subject, String text, String toEmail){
    	
    	log.info(CLASSNAME + "Method send starts.....");
    	
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "MessagingException in send()");
        }
    }

}
