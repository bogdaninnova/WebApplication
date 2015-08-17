package ua.sumdu.group73.model.interfaces;

import java.util.*;

import ua.sumdu.group73.model.objects.*;

public interface CategoriesDBInterface {

	public boolean addCategory(int parentID, String name);
	
	public boolean addCategory(String name);
	
	public Category getCategory(int categoryID);
	
	public boolean deleteCategory(int categoryID, List<Category> list);
	
	public boolean changeCategory(int categoriesID, String newName);
	
	public List<Product> getProductsByCategory(int categoryID);	
	
	public List<Category> getCategoriesOfProduct(int productID);
	
	public List<Category> getAllCategories();
	
	public boolean addCategoriesToProduct(int productID, List<Integer> categories);

}
