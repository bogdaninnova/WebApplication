package ua.sumdu.group73.model;

import java.util.List;

import ua.sumdu.group73.model.interfaces.ProductDBInterface;

public class Queries {

    //------------------------------------------------------
    //-----------------------XXX:USER-----------------------
    //------------------------------------------------------
	
	public static final String ADD_USER =
			"INSERT INTO USERS("
					+ "ID,"
					+ "LOGIN,"
					+ "PASSWORD,"
					+ "NAME,"
					+ "SECOND_NAME,"
					+ "BIRTH,"
					+ "EMAIL,"
					+ "PHONE,"
					+ "REGISTRATION_DATE,"
					+ "STATUS)"
			+ "VALUES("
					+ "USER_ID_S.NEXTVAL,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "LOWER(?),"
					+ "?,"
					+ "SYSDATE,"
					+ "?)";
	
	public static final String GET_USER =
			"SELECT"
					+ " LOGIN,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE ID = ?";
	
	public static final String GET_USER_BY_LOGIN =
			"SELECT"
					+ " ID,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE LOGIN = ?";
	
	public static final String IS_LOGIN_FREE =
			"SELECT * FROM USERS WHERE LOGIN = ?";
	
	public static final String IS_EMAIL_FREE =
			"SELECT * FROM USERS WHERE EMAIL = LOWER(?)";
    
	public static final String AUTHORIZATION =
			"SELECT"
					+ " ID,"
					+ " LOGIN,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE LOGIN = ?";
    
	public static final String AUTHORIZATION_BY_EMAIL =
			"SELECT"
					+ " ID,"
					+ " LOGIN,"
					+ " PASSWORD,"
					+ " NAME,"
					+ " SECOND_NAME,"
					+ " EMAIL,"
					+ " PHONE,"
					+ " STATUS,"
					+ " REGISTRATION_DATE,"
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE EMAIL = LOWER(?)";
    
	public static final String GET_ALL_USERS =
			"SELECT "
			+ " ID,"
			+ " LOGIN,"
			+ " PASSWORD,"
			+ " NAME,"
			+ " SECOND_NAME,"
			+ " EMAIL,"
			+ " PHONE,"
			+ " STATUS,"
			+ " REGISTRATION_DATE,"
			+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS";
	
	public static final String GET_USERS_PRODUCTS =
			"SELECT * FROM PRODUCTS WHERE SELLER_ID = ?";
    
	public static final String IS_USER_ADMIN =
			"SELECT * FROM USERS WHERE ID = ? AND STATUS = 'admin'";
    
	public static final String ACTIVATE_USER =
			"UPDATE USERS SET STATUS = 'user' WHERE LOGIN = ?";
	
	
	public static String setUserBanQuery(List<Integer> usersID) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE USERS SET STATUS = 'banned' WHERE ID IN ( ");
		
		for (int i = 0; i < usersID.size() - 1; i++)
			sb.append(usersID.get(i) + ", ");
		sb.append(usersID.get(usersID.size() - 1));	
		sb.append(") AND STATUS != 'admin' AND STATUS != 'unactivated'");
		return sb.toString();
	}

	public static final String UNBAN_ALL_USERS =
			"UPDATE USERS SET STATUS = 'user' WHERE STATUS = 'banned'";
	
	public static final String CHANGE_PASSWORD =
			"UPDATE USERS SET PASSWORD = ? WHERE ID = ? AND PASSWORD = ?";
	
	public static final String CHANGE_DATA =
			"UPDATE USERS SET NAME = ?, SECOND_NAME = ?, PHONE = ? WHERE ID = ?";
	
	public static final String CHANGE_EMAIL =
			"UPDATE USERS SET EMAIL = LOWER(?) WHERE LOGIN = ?";
	
	public static final String DELETE_UNACTIVATED_USERS = 
			"DELETE FROM USERS WHERE STATUS = 'unactivated' AND"
			+ " ((REGISTRATION_DATE + INTERVAL '24' HOUR) < SYSDATE)";
	
    //------------------------------------------------------
    //----------------XXX:PRODUCT FOLLOWING-----------------
    //------------------------------------------------------
	
	
	public static final String FOLLOW_PRODUCT =
			"INSERT INTO FOLLOWINGS("
					+ "ID,"
					+ "FOLLOWER_ID,"
					+ "PRODUCT_ID)"
			+ " VALUES "
					+ "(FOLLOWING_ID_S.NEXTVAL,"
					+ "?,"
					+ "?)";

	public static final String FINISH_PRODUCT_FOLLOWING =
			"DELETE FROM FOLLOWINGS WHERE PRODUCT_ID = ?";

	
	public static final String GET_FOLLOWING_PRODUCTS =
			"SELECT * FROM PRODUCTS"
			+ " JOIN FOLLOWINGS ON PRODUCTS.ID = FOLLOWINGS.PRODUCT_ID"
			+ " WHERE FOLLOWINGS.FOLLOWER_ID = ?";
    
	
	public static final String IS_FOLLOW_QUERY =
			"SELECT * FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";

	
    //------------------------------------------------------
    //---------------------XXX:PRODUCT----------------------
    //------------------------------------------------------
	
	public static final String ADD_PRODUCT =
			"INSERT INTO PRODUCTS("
					+ "ID,"
					+ "SELLER_ID,"
					+ "NAME,"
					+ "DESCRIPTION,"
					+ "START_DATE,"
					+ "END_DATE,"
					+ "START_PRICE,"
					+ "BUYOUT_PRICE,"
					+ "CURRENT_PRICE,"
					+ "CURRENT_BUYER_ID,"
					+ "IS_ACTIVE)"
			+ " VALUES"
					+ "(PRODUCT_ID_S.NEXTVAL,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "SYSDATE,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "0,"
					+ "NULL,"
					+ "'active')";
    
	public static final String GET_PRODUCT =
			"SELECT * FROM PRODUCTS WHERE ID = ?";
    
	public static final String DISACTIVATE_PRODUCT =
			"UPDATE PRODUCTS SET IS_ACTIVE = 'disactive' WHERE ID = ?";
    
	public static final String IS_PRODUCT_ACTIVE =
			"SELECT * FROM PRODUCTS WHERE ID = ? AND IS_ACTIVE = 'active'";
    
	public static final String QET_CURRENT_PRICE =
			"SELECT CURRENT_PRICE FROM PRODUCTS WHERE ID = ?";
    
	public static final String MAKE_A_BET =
			"UPDATE PRODUCTS SET"
					+ " CURRENT_PRICE = ?,"
					+ " CURRENT_BUYER_ID = ?"
			+ " WHERE ID = ?";
	
	public static final String BUYOUT =
			"UPDATE PRODUCTS SET"
					+ " CURRENT_PRICE = BUYOUT_PRICE,"
					+ " CURRENT_BUYER_ID = ?,"
					+ " END_DATE = SYSDATE,"
					+ " IS_ACTIVE = 'disactive'"
			+ " WHERE ID = ?";
    
	public static final String FINISH_AUCTIONS =
			"SELECT * FROM PRODUCTS"
			+ " WHERE IS_ACTIVE = 'active' AND END_DATE < SYSDATE";
    
	public static final String GET_ALL_PRODUCTS =
			"SELECT * FROM PRODUCTS";
	
	public static final String GET_USER_BUYING =
			"SELECT * FROM PRODUCTS WHERE CURRENT_BUYER_ID = ? AND IS_ACTIVE = 'disactive'";
    
	public static final String FIND_PRODUCTS =
			"SELECT * FROM PRODUCTS"
				+ " WHERE "
					+ "LOWER(DESCRIPTION) LIKE LOWER(?)"
				+ " OR "
					+ "LOWER(NAME) LIKE LOWER(?)";
    
	public static final String deleteProductsByIdFromPRODUCTS(List<Integer> productsID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PRODUCTS WHERE ID IN ( ");
		for (int i = 0; i < productsID.size() - 1; i++)
			sb.append(productsID.get(i) + ", ");
		sb.append(productsID.get(productsID.size() - 1) + " )");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromTRANSACTIONS(List<Integer> productsID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TRANSACTIONS WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < productsID.size() - 1; i++)
			sb.append(productsID.get(i) + ", ");
		sb.append(productsID.get(productsID.size() - 1) + " )");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromPICTURES(List<Integer> productsID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PICTURES WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < productsID.size() - 1; i++)
			sb.append(productsID.get(i) + ", ");
		sb.append(productsID.get(productsID.size() - 1) + " )");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromFOLLOWINGS(List<Integer> productsID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM FOLLOWINGS WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < productsID.size() - 1; i++)
			sb.append(productsID.get(i) + ", ");
		sb.append(productsID.get(productsID.size() - 1) + " )");
		return sb.toString();
	}
	
	public static final String deleteProductsByIdFromPRODUCT_CATEGORY(List<Integer> productsID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PRODUCT_CATEGORY WHERE PRODUCT_ID IN ( ");
		for (int i = 0; i < productsID.size() - 1; i++)
			sb.append(productsID.get(i) + ", ");
		sb.append(productsID.get(productsID.size() - 1) + " )");
		return sb.toString();
	}
	
	public static final String GET_PRODUCT_SELLER =
			"SELECT USERS.* FROM USERS"
			+ " JOIN PRODUCTS ON USERS.ID = PRODUCTS.SELLER_ID"
			+ " WHERE PRODUCTS.ID = ?";

	public static String getProductCountQuery(int categoryID, int minPrice, int maxPrice) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT COUNT(*) AS COUNT FROM PRODUCT_CATEGORY ");
		sb.append(" LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID ");
		sb.append(" LEFT JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID WHERE ");

		if (categoryID != 0)
			sb.append(" (CATEGORIES.ID = " + categoryID + ") AND");
		
		sb.append(" (START_PRICE BETWEEN " + minPrice + " AND ");
		if (maxPrice == 0)
			sb.append("BINARY_DOUBLE_INFINITY)");
		else
			sb.append(maxPrice + ")");
		sb.append(" AND (IS_ACTIVE = 'active')");
		return sb.toString();
	}
	
	public static String getProductsQuery(
			int categoryID, int minPrice, int maxPrice, int position) {
		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT * FROM ( SELECT T.*, ROWNUM RN FROM ( ");
		sb.append(" SELECT PRODUCTS.* FROM PRODUCT_CATEGORY ");
		sb.append(" LEFT JOIN CATEGORIES ON CATEGORIES.ID = PRODUCT_CATEGORY.CATEGORY_ID ");
		sb.append(" LEFT JOIN PRODUCTS ON PRODUCTS.ID = PRODUCT_CATEGORY.PRODUCT_ID WHERE");
		if (categoryID != 0)
			sb.append(" (CATEGORIES.ID = " + categoryID + ") AND ");
		sb.append(" (START_PRICE BETWEEN " + minPrice + " AND ");
		if (maxPrice == 0)
			sb.append(" BINARY_DOUBLE_INFINITY ");
		else
			sb.append(maxPrice);
		sb.append(") AND (IS_ACTIVE = 'active') ) T) ");
		sb.append("WHERE (RN > (" +
				ProductDBInterface.LOTS_ON_PAGE + " * " + position + ") - " + 
				ProductDBInterface.LOTS_ON_PAGE + ")"
				+ " AND (RN <= " + position + " * " +
				ProductDBInterface.LOTS_ON_PAGE + ") ORDER BY ID DESC");
		return sb.toString();
				
	}
	
	
	public static String getCountFindQuery(int minPrice, int maxPrice, String keyWord) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT COUNT(*) AS COUNT FROM PRODUCTS ");
		sb.append(" WHERE START_PRICE BETWEEN ");
		sb.append(minPrice);
		
		if (maxPrice == 0)
			sb.append(" AND BINARY_DOUBLE_INFINITY ");
		else
			sb.append(" AND " + maxPrice );
		sb.append(" AND (IS_ACTIVE = 'active')");
		sb.append(" AND ((LOWER(DESCRIPTION) LIKE LOWER('%" + keyWord + "%')) OR (LOWER(NAME) LIKE LOWER('%" + keyWord + "%'))) ");
		return sb.toString();
	}
	
	public  static String getProductsFindQuery(int postiton, int minPrice, int maxPrice, String keyWord) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT * FROM ( SELECT T.*, ROWNUM RN FROM (  ");
		sb.append(" SELECT P.* FROM PRODUCT_CATEGORY PC ");
		sb.append(" LEFT JOIN CATEGORIES C ON C.ID = PC.CATEGORY_ID ");
		sb.append(" LEFT JOIN PRODUCTS P ON P.ID = PC.PRODUCT_ID WHERE ");
		sb.append(" START_PRICE BETWEEN ");
		sb.append(minPrice);
		if (maxPrice == 0)
			sb.append(" AND BINARY_DOUBLE_INFINITY ");
		else
			sb.append(" AND " + maxPrice);
		sb.append(" AND (IS_ACTIVE = 'active') ");
		sb.append(" AND ((LOWER(P.DESCRIPTION) LIKE LOWER('%" + keyWord + "%')) OR (LOWER(P.NAME) LIKE LOWER('%" + keyWord + "%'))) ");
		sb.append(" ) T) ");
		
		sb.append(" WHERE (RN > (" + ProductDBInterface.LOTS_ON_PAGE + " * " + postiton + " - " + ProductDBInterface.LOTS_ON_PAGE + ") ");
		sb.append(" AND (RN <= " + postiton + " * " + ProductDBInterface.LOTS_ON_PAGE + ")) ");
		sb.append(" ORDER BY ID DESC ");
		
		return sb.toString();
	}
	
	
	
	
    //------------------------------------------------------
    //--------------------XXX:CATEGORY----------------------
    //------------------------------------------------------
	
	
	public static final String GET_CATEGORY =
			"SELECT * FROM CATEGORIES WHERE ID = ?";
    
	public static final String ADD_CATEGORY =
			"INSERT INTO CATEGORIES("
					+ "ID,"
					+ "PARENT_ID,"
					+ "NAME)"
			+ "VALUES"
					+ "(CATEGORY_ID_S.NEXTVAL,"
					+ "?,"
					+ "?)";
	
	public static final String ADD_ROOT_CATEGORY =
			"INSERT INTO CATEGORIES("
					+ "ID,"
					+ "NAME)"
			+ "VALUES"
					+ "(CATEGORY_ID_S.NEXTVAL,"
					+ "?)";
    
	public static final String GET_ALL_CATEGORIES =
			"SELECT * FROM CATEGORIES";
    
	public static final String GET_PRODUCTS_BY_CATEGORY =
			"SELECT PRODUCTS.* FROM PRODUCT_CATEGORY"
				+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID ="
						+ " PRODUCT_CATEGORY.CATEGORY_ID"
				+ " LEFT JOIN PRODUCTS ON PRODUCTS.ID ="
						+ " PRODUCT_CATEGORY.PRODUCT_ID WHERE CATEGORIES.ID = ?";

	public static final String GET_CATEGORIES_OF_PRODUCT =
			"SELECT CATEGORIES.* FROM PRODUCT_CATEGORY"
				+ " LEFT JOIN CATEGORIES ON CATEGORIES.ID ="
						+ " PRODUCT_CATEGORY.CATEGORY_ID"
				+ " LEFT JOIN PRODUCTS ON PRODUCTS.ID ="
						+ " PRODUCT_CATEGORY.PRODUCT_ID WHERE PRODUCTS.ID = ?";
    
	public static final String setCategoriesToProductQuery(int productID, List<Integer> categories) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		for (int categoryID : categories)
			sb.append("INTO PRODUCT_CATEGORY (CATEGORY_ID, PRODUCT_ID) VALUES (" + categoryID +", " + productID + ") ");
		sb.append("SELECT * FROM dual");
		return sb.toString();
	}
	
	public static final String CHANGE_CATEGORY =
			"UPDATE CATEGORIES SET NAME = ? WHERE ID = ?";
		
	
	public static final String deleteCategories(List<Integer> categoriesID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM CATEGORIES WHERE ID IN ( ");
		for (int i = 0; i < categoriesID.size() - 1; i++)
			sb.append(categoriesID.get(i) + ", ");
		sb.append(categoriesID.get(categoriesID.size() - 1) + " )");
		return sb.toString();
	}
	
	public static final String deleteProductsCategories(List<Integer> categoriesID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM PRODUCT_CATEGORY WHERE CATEGORY_ID IN ( ");
		for (int i = 0; i < categoriesID.size() - 1; i++)
			sb.append(categoriesID.get(i) + ", ");
		sb.append(categoriesID.get(categoriesID.size() - 1) + " )");
		return sb.toString();
	}
	
    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------
	
	
	public static final String GET_PICTURES_OF_PRODUCT =
			"SELECT * FROM PICTURES WHERE PRODUCT_ID = ?";
    
	public static final String ADD_PICTURE =
			"INSERT INTO PICTURES("
					+ "PRODUCT_ID,"
					+ "URL)"
			+ "VALUES("
					+ "?,"
					+ "?)";
	
	public static final String GET_ALL_PICTURES =
			"SELECT * FROM PICTURES";
	
	public static String addPicturesToProduct(int productID, List<String> picturesURL) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		for (String pic : picturesURL)
			sb.append("INTO PICTURES (PRODUCT_ID, URL) VALUES (" + productID + ", '" + pic + "') ");
		sb.append("SELECT * FROM dual");
		return sb.toString();
	}
	
	public static String getProductCurrVal() {
		return "SELECT PRODUCT_ID_S.CURRVAL from dual";
	}
}
