package ua.sumdu.group73.model;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.objects.*;

public class Messager {

	private static final Logger log = Logger.getLogger(Messager.class);
	private static final String CLASSNAME = "Messager: ";
	
	public static void sendEndAuctionMessage(int productID) {
		
		log.info(CLASSNAME + "Method sendEndAuctionMessage starts.....");
		
		OracleDataBase database = OracleDataBase.getInstance();
		MailSender mailer = MailSender.getInstance();
		
		Product product = database.getProduct(productID);
		
		if (product.getCurrentPrice() == 0) 
			return;
		
		User buyer = database.getUser(product.getCurrentBuyerID());
		User seller = database.getUser(product.getSellerID());
		
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
	
	public static void registrationMail(int userID) {
		
		log.info(CLASSNAME + "Method registrationMail starts.....");
		
		MailSender mailer = MailSender.getInstance();
		
		User user = OracleDataBase.getInstance().getUser(userID);

		StringBuilder textSB = new StringBuilder();
		textSB.append("Hello, " + user.getName() +"!\n");
		textSB.append("You just registred at our auction Lab3!\n\n");
				
		textSB.append(
				"<link rel=\""
			+ "Click this link and verify your account\""
				+ " href=\""
			+ "http://localhost:7001/WebApplication/Verification?token=" + getToken(user)
				+ "\">");
		
		textSB.append("Login: " + user.getLogin() + "\n");
		textSB.append("Password: " + user.getPassword() + "\n\n");
		textSB.append("This mail was generated automatically, please don't answer on it");

		
		
		mailer.send("Auction Lab3: REGISTRATION", textSB.toString(), user.geteMail());
	} 
	
	private static String getToken(User user) {
		String login = user.getLogin();
		long regDate = user.getRegistrationDate().getTime();
		
		return login + regDate;//TODO security
	}
}
