package ua.sumdu.group73.model;

import java.util.Date;

import org.apache.log4j.Logger;

import ua.sumdu.group73.model.objects.*;

public class Messager {

	private static final Logger log = Logger.getLogger(Messager.class);
	
	private static final String VERIFICATION_URL =
			"http://localhost:7001/WebApplication/Verification";
	
	public static void sendEndAuctionMessage(int productID) {
		
		log.info("Method sendEndAuctionMessage starts.....");
		
		MailSender mailer = MailSender.getInstance();
		
		Product product = OracleDataBase.getInstance().getProduct(productID);
		
		if (product.getCurrentPrice() == 0) 
			return;
		
		User buyer = OracleDataBase.getInstance().getUser(product.getCurrentBuyerID());
		User seller = OracleDataBase.getInstance().getUser(product.getSellerID());
		
		String buyerName = buyer.getName();
		String buyerMail = buyer.geteMail();
		String sellerName = seller.getName();
		String sellerMail = seller.geteMail();
		String productName = product.getName();
		int price = product.getCurrentPrice();
		

		StringBuilder buyerTextSB = new StringBuilder();
		buyerTextSB.append("Hello, " + buyerName +"!\n");
		buyerTextSB.append("You won in auction and bought " + productName + "!\n");
		buyerTextSB.append("Amount of a transaction: " + price + ".\n");
		buyerTextSB.append("Please, contact with seller:\n");
		buyerTextSB.append("Name:" + sellerName + "\n");
		buyerTextSB.append("E-mail:" + sellerMail + "\n\n");
		buyerTextSB.append("This mail was generated automatically, please don't answer on it");
		
		mailer.send("Auction Lab3: BUY", buyerTextSB.toString(), buyerMail);
		
		StringBuilder sellerTextSB = new StringBuilder();
		sellerTextSB.append("Hello, " + sellerName +"!\n");
		sellerTextSB.append("You sold " + productName + " on our auction!\n");
		sellerTextSB.append("Amount of a transaction: " + price + ".\n");
		sellerTextSB.append("Please, contact with buyer:\n");
		sellerTextSB.append("Name:" + buyerName + "\n");
		sellerTextSB.append("E-mail:" + buyerMail + "\n\n");
		sellerTextSB.append("This mail was generated automatically, please don't answer on it");
		
		mailer.send("Auction Lab3: SELL", sellerTextSB.toString(), sellerMail);

	}
	
	public static void registrationMail(String login, String name, String mail) {
		
		log.info("Method registrationMail starts.....");
		
		MailSender mailer = MailSender.getInstance();
		
		StringBuilder textSB = new StringBuilder();

		textSB.append("Hello, " + name +"! <br>");
		textSB.append("You just registred at our auction Lab3!<br><br>");
		
		textSB.append("<a href=\"" + VERIFICATION_URL + getRegistrationToken(login) +
				"\" target=\"_blank\">Use this link for verifying your account</a>");
		
		textSB.append("<br><br>Login: " + login + "<br>");
		textSB.append("This mail was generated automatically, please don't answer on it");
		
		mailer.send("Auction Lab3: REGISTRATION", textSB.toString(), mail);
	}
	
	public static boolean changeMail(String login, String mail) {
		
		log.info("Method changeMailLetter starts.....");
		
		if (!OracleDataBase.getInstance().isEmailFree(mail))
			return false;
		
		MailSender mailer = MailSender.getInstance();
		
		StringBuilder textSB = new StringBuilder();

		textSB.append("Hello, " + login +"! <br>");
		textSB.append("You just changed email of your account in auction Lab3!<br><br>");
		
		textSB.append("<a href=\"" +
				VERIFICATION_URL +
				getEmailChangesToken(login, mail) +
				"\" target=\"_blank\">Use this link for verifying new email</a>");
		
		textSB.append("This mail was generated automatically, please don't answer on it");
		
		mailer.send("Auction Lab3: MAIL CHANGING", textSB.toString(), mail);
		
		return true;
	}
	
	private static String getRegistrationToken(String login) {
		String loginToken = StringCrypter.getInstance().encrypt(login);
		String regDateToken = StringCrypter.getInstance().encrypt(
				String.valueOf(new Date().getTime()));
		return "?l=" + loginToken + "&d=" + regDateToken;
	}
	
	private static String getEmailChangesToken(String login, String email) {
		String idToken = StringCrypter.getInstance().encrypt(login);
		String emailToken = StringCrypter.getInstance().encrypt(email);
		return "?l=" + idToken + "&e=" + emailToken;
	}
}
