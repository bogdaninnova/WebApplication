package ua.sumdu.group73.model.interfaces;

import java.util.ArrayList;
import java.util.Date;

import ua.sumdu.group73.model.objects.*;

public interface ProductDBInterface {

	public boolean addProduct(int sellerID, String name, String description,
    		Date startDate, Date endDate, int startPrice, int buyoutPrice);
	
	public Product getProduct(int id);
	
	public boolean disactivateProduct(int productID);
	
	public boolean isProductExist(int productID);
	
	public boolean isProductActive(int productID);
	
	public int getCurrentPrice(int productID);
		
	public boolean makeBet(int productID, int buyerID, int price);
	
	public ArrayList<Integer> finishAuctions();
	
}
