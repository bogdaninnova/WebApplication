package ua.sumdu.group73.model;

import java.util.*;

import ua.sumdu.group73.model.objects.Product;

public class ServerTimerTask {

	private static final int DELAY = 300000;//Every 300 seconds
	private static Timer timer = null;
	
	private static ServerTimerTask instance;
	
	public static void run() {
		if (timer == null)
			timer = new Timer();
		if (instance == null)
			instance = new ServerTimerTask();
	}
	
	private ServerTimerTask() {
        TimerTask st = new TimerTask() {
			@Override
			public void run() {
		    	finishAuctions();
		    	OracleDataBase.getInstance().removeUnactivatedUsers();
			}
		};
		timer.schedule(st, DELAY, DELAY);
	}
	
	public static void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
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