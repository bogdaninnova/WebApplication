package ua.sumdu.group73.model.interfaces;

import java.util.*;

import ua.sumdu.group73.model.objects.*;

public interface ProductDBInterface {

	public static final int LOTS_ON_PAGE = 10;
	
	public int addProduct(int sellerID, String name, String description,
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
	
	public User getProductSeller(int productID);
	
	public boolean deleteProducts(List<Integer> productsID);
	
	/**
	 * position: start with 1. Every page has LOTS_ON_PAGE lots
	 * categoryID: 0 if without categories;
	 * minPrice: 0 if without minimal price;
	 * maxPrice: 0 for infinity.
	 * */
	public List<Product> getProducts(
			int postiton, int categoryID, int minPrice, int maxPrice);
	
	/**
	 * categoryID: 0 if without categories;
	 * minPrice: 0 if without minimal price;
	 * maxPrice: 0 for infinity.
	 * */
	public int getCount(int categoryID, int minPrice, int maxPrice);
}
