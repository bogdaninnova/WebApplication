package ua.sumdu.group73.model.interfaces;

import java.util.*;

import ua.sumdu.group73.model.objects.*;

public interface CategoriesDBInterface {

	public boolean addCategory(int parentID, int productID, String name);
	
	public boolean addCategory(int productID, String name);
	
	public Category getCategory(int categoryID);
	
	public List<Product> getProductsByCategory(String categoryName);

	public List<Category> getAllCategories();

	@Deprecated
	public List<Category> getSubcategories(int categoryID);
}
