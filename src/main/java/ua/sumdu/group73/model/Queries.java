package ua.sumdu.group73.model;

import java.util.List;

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
					+ "LOWER(?),"
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
			+ " FROM USERS WHERE LOGIN = LOWER(?)";
	
	public static final String IS_LOGIN_FREE =
			"SELECT * FROM USERS WHERE LOGIN = LOWER(?)";
	
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
			+ " FROM USERS WHERE LOGIN = LOWER(?)";
    
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
			"UPDATE USERS SET STATUS = 'user' WHERE LOGIN = LOWER(?)";
	
	
	public static String setUserBanQuery(List<Integer> usersID) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE USERS SET STATUS = 'banned' WHERE ID IN ( ");
		
		for (int i = 0; i < usersID.size() - 1; i++)
			sb.append(usersID.get(i) + ", ");
		sb.append(usersID.get(usersID.size() - 1));	
		sb.append(") AND STATUS != 'admin' AND STATUS != 'unactivated'");
			System.out.println(sb.toString());
		return sb.toString();
	}

	public static final String UNBAN_ALL_USERS =
			"UPDATE USERS SET STATUS = 'user' WHERE STATUS = 'banned'";
	
	public static final String CHANGE_PASSWORD =
			"UPDATE USERS SET PASSWORD = ? WHERE ID = ? AND PASSWORD = ?";
	
	public static final String CHANGE_DATA =
			"UPDATE USERS SET NAME = ?, SECOND_NAME = ?, BIRTH = ?, PHONE = ?"
			+ " WHERE ID = ?";
	
	public static final String CHANGE_EMAIL =
			"UPDATE USERS SET EMAIL = LOWER(?) WHERE LOGIN = LOWER(?)";
	
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
    
	public static final String IS_FOLLOW =
			"SELECT * FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";
    
	public static final String UNFOLLOW =
			"DELETE FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";
    
	public static final String GET_FOLLOWING_PRODUCTS =
			"SELECT PRODUCT_ID FROM FOLLOWINGS WHERE FOLLOWER_ID = ?";
    
	
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
    
	public static final String setCategoriesToProductQuery(int productID, List<Integer> categoriesID) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ALL ");
		for (int categoryID : categoriesID)
			sb.append("INTO PRODUCT_CATEGORY (CATEGORY_ID, PRODUCT_ID) VALUES (" + categoryID +", " + productID + ") ");
		sb.append("SELECT * FROM dual");
		return sb.toString();
	}
	
	
    //------------------------------------------------------
    //------------------XXX:TRANSACTION---------------------
    //------------------------------------------------------
	
	
	public static final String GET_SALLERS_TRANSACTIONS =
			"SELECT * FROM TRANSACTIONS WHERE SELLER_ID = ?";
    
	public static final String GET_BUYERS_TRANSACTIONS =
			"SELECT * FROM TRANSACTIONS WHERE BUYER_ID = ?";
    
	public static final String ADD_TRANSACTION =
			"INSERT INTO TRANSACTIONS("
					+ "ID,"
					+ "BUYER_ID,"
					+ "SELLER_ID,"
					+ "PRODUCT_ID,"
					+ "PRICE,"
					+ "SALE_DATE)"
			+ "VALUES"
					+ "TRANSACTION_ID_S.NEXTVAL,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?)";
    
	public static final String GET_TRANSACTION =
			"SELECT * FROM TRANSACTIONS WHERE ID = ?";
    
	
    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------
	
	
	public static final String GET_PICTURES_OF_PRODUCT =
			"SELECT * FROM PICTURES WHERE PRODUCT_ID = ?";
    
	public static final String ADD_PICTURE =
			"INSERT INTO PICTURES("
					+ "ID,"
					+ "PRODUCT_ID,"
					+ "URL)"
			+ "VALUES("
					+ "PICTURE_ID_S.NEXTVAL,"
					+ "?,"
					+ "?)";
	
	public static final String GET_ALL_PICTURES =
			"SELECT * FROM PICTURES";
}
