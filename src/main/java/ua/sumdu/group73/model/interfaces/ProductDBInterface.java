package ua.sumdu.group73.model.interfaces;

import java.util.*;

import ua.sumdu.group73.model.objects.*;

public interface ProductDBInterface {

	public boolean addProduct(int sellerID, String name, String description,
			long endDate, int startPrice, int buyoutPrice);
	
	public Product getProduct(int id);
	
	public boolean disactivateProduct(int productID);
	
	public boolean isProductActive(int productID);
	
	public int getCurrentPrice(int productID);
		
	public boolean makeBet(int productID, int buyerID, int price);
	
	public boolean buyout(int productID, int buyerID);
	
	public ArrayList<Product> finishAuctions();
	
	public List<Product> getAllProducts();
	
	public List<Product> getAllActiveProducts();
	
	public List<Product> findProducts(String substring);
	
	public boolean deleteProducts(List<Integer> productsID);
	
}
