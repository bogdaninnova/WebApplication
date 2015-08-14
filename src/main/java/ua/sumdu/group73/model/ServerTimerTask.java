package ua.sumdu.group73.model;

import java.util.*;

import ua.sumdu.group73.model.objects.Product;

public class ServerTimerTask {

	private static final int DELAY = 30000;//Every 30 seconds
	
	private static ServerTimerTask instance;
	
	public static ServerTimerTask getInstance() {
		if (instance == null)
			instance = new ServerTimerTask();
		return instance;
	}
	
	private ServerTimerTask() {
        TimerTask st = new TimerTask() {
			@Override
			public void run() {
		    	finishAuctions();
		    	OracleDataBase.getInstance().removeUnactivatedUsers();
			}
		};
		new Timer().schedule(st, DELAY, DELAY);
	}
	
	private static void finishAuctions() {
		
		List<Product> products = OracleDataBase.getInstance().finishAuctions();
		for (Product product : products) {
			OracleDataBase.getInstance().disactivateProduct(product.getId());
	        
	        if (product.getCurrentPrice() != 0) {
	        	OracleDataBase.getInstance()
	        		.finishProductFollowings(product.getId());
	        	Messager.sendEndAuctionMessage(product.getId());
	        }
		}
	}
}