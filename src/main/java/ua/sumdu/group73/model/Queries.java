package ua.sumdu.group73.model;

public class Queries {

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
					+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS WHERE ID = ?";
	
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
			+ " TRUNC((SYSDATE - BIRTH)/365) AS \"AGE\""
			+ " FROM USERS";
	
	public static final String GET_USERS_PRODUCTS =
			"SELECT * FROM PRODUCTS WHERE SELLER_ID = ?";
    
	public static final String IS_USER_ADMIN =
			"SELECT * FROM USERS WHERE ID = ? AND STATUS = 'admin'";
    
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
					+ "?,"
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
    
	public static final String FINISH_AUCTIONS =
			"SELECT * FROM PRODUCTS WHERE IS_ACTIVE = 'active' AND END_DATE < ?";
    
	public static final String GET_ALL_PRODUCTS =
			"SELECT * FROM PRODUCTS";
    
	public static final String FIND_PRODUCTS =
			"SELECT * FROM PRODUCTS"
				+ " WHERE "
					+ "LOWER(DESCRIPTION) LIKE LOWER(?)"
				+ " OR "
					+ "LOWER(NAME) LIKE LOWER(?)";
    
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
