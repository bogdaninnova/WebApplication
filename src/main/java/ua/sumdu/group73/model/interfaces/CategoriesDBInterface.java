package ua.sumdu.group73.model.interfaces;

import java.util.*;

import objects.*;

public interface CategoriesDBInterface {

	public boolean addCategory(int parentID, int productID, String name);
	
	public boolean addCategory(int productID, String name);
	
	public Category getCategory(int categoryID);
	
	public List<Integer> getProductsByCategory(int categoryID);

	public List<Integer> getAllCategoriesID();

	public List<Integer> getSubcategories(int categoryID);
}
