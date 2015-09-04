package ua.sumdu.group73.model;

import java.util.*;
import ua.sumdu.group73.model.objects.Category;

public class CategoriesTree {

	private static final int MAX_NAME_LENGTH = 20;
	
	private CategoriesTree() {}
	
	public static boolean isCategoryChild(Category category, int categoryID, List<Category> list) {
		int parrentID = category.getParentID();
		if (parrentID == categoryID)
			return true;
		if (parrentID == 0)
			return false;
		return isCategoryChild(getParentCategory(list, category.getParentID()), categoryID, list);
	}
	
	private static Category getParentCategory(List<Category> list, int categoryID) {
		for (Category category : list)
			if (category.getId() == categoryID)
				return category;
		return null;
	}
	
	 public static String printCategories(List<Category> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"multi-derevo\">\n");
		sb.append("<a href=\"#\">CATEGORIES</a>\n");
		sb.append("<ul>\n");
		Map<Category, Boolean> map = new HashMap<Category, Boolean>();
		for (Category category : list)
			map.put(category, true);

		for (Category category : map.keySet())
			if (map.get(category) && (category.getParentID() == 0))
				printSubcategories(map, category, sb);
		sb.append("</ul>\n");
		sb.append("</div>\n");
		return sb.toString();
	}

	private static void printSubcategories(Map<Category, Boolean> map, Category category, StringBuilder sb) {
		map.put(category, false);
		boolean hasSubcategory = getSubcategory(map, category) != null;
		sb.append("<li>\n");
		sb.append("<span><a href=\"#" + category.getId() + "\">");

		String name = category.getName();
		
		if (name.length() > MAX_NAME_LENGTH)
			name = name.substring(0, MAX_NAME_LENGTH) + "...";

		sb.append(name);
		sb.append("</a></span>\n");
		
		if (hasSubcategory)
			sb.append("<ul>\n");
		while (getSubcategory(map, category) != null)
			printSubcategories(map, getSubcategory(map, category), sb);
		if (hasSubcategory)
			sb.append("</ul>\n");
		sb.append("</li>\n");
	}

	private static Category getSubcategory(Map<Category, Boolean> map, Category category) {
		for (Category cat : map.keySet())
			if ((map.get(cat)) && cat.getParentID() == category.getId())
				return cat;
		return null;
	}
	
}
