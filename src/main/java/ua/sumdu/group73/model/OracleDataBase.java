package main;

import interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import objects.*;

public class OracleDataBase implements UserDBInterface, PicturesDBInterface,
										TransactionDBIinterface, ProductDBInterface,
										FollowingDBInterface, CategoriesDBInterface {

	public static final String ADD_USER_QUERY = "INSERT INTO USERS(ID, LOGIN, PASSWORD, NAME, SECOND_NAME, BIRTH, EMAIL, PHONE, STATUS) VALUES(USER_ID_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_USER_QUERY = "SELECT * FROM USERS WHERE ID = ?";
	public static final String IS_LOGIN_FREE_QUERY = "SELECT COUNT(*) as count FROM USERS WHERE LOGIN = ?";
	public static final String IS_EMAIL_FREE_QUERY = "SELECT COUNT(*) as count FROM USERS WHERE EMAIL = ?";
	public static final String AUTHORIZATION_QUERY = "SELECT PASSWORD, ID FROM USERS WHERE LOGIN = ?";
	public static final String IS_USER_ADMIN_QUERY = "SELECT COUNT(*) as count FROM USERS WHERE ID = ? AND STATUS = 'admin'";
	public static final String FOLLOW_PRODUCT_QUERY = "INSERT INTO FOLLOWINGS(ID, FOLLOWER_ID, PRODUCT_ID) VALUES (FOLLOWING_ID_S.NEXTVAL, ?, ?)";
	public static final String IS_FOLLOW_QUERY = "SELECT COUNT(*) as count FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";
	public static final String UNFOLLOW_QUERY = "DELETE FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";
	public static final String GET_FOLLOWING_PRODUCTS_QUERY = "SELECT PRODUCT_ID FROM FOLLOWINGS WHERE FOLLOWER_ID = ?";
	public static final String ADD_PRODUCT_QUERY = "INSERT INTO PRODUCTS(ID, SELLER_ID, NAME, DESCRIPTION, START_DATE, END_DATE, START_PRICE, BUYOUT_PRICE, CURRENT_PRICE, CURRENT_BUYER_ID, IS_ACTIVE) VALUES (PRODUCT_ID_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 0, NULL, 'active')";
	public static final String GET_PRODUCT_QUERY = "SELECT * FROM PRODUCTS WHERE ID = ?";
	public static final String DISACTIVATE_PRODUCT_QUERY = "UPDATE PRODUCTS SET IS_ACTIVE = 'disactive' WHERE ID = ?";
	public static final String IS_PRODUCT_EXIST_QUERY = "SELECT COUNT(*) as count FROM PRODUCTS WHERE ID = ?";
	public static final String IS_PRODUCT_ACTIVE_QUERY = "SELECT COUNT(*) as count FROM PRODUCTS WHERE ID = ? AND IS_ACTIVE = 'active'";
	public static final String QET_CURRENT_PRICE_QUERY = "SELECT CURRENT_PRICE FROM PRODUCTS WHERE ID = ?";
	public static final String MAKE_A_BET_QUERY = "UPDATE PRODUCTS SET CURRENT_PRICE = ?, CURRENT_BUYER_ID = ? WHERE ID = ?";
	public static final String FINISH_AUCTIONS_QUERY = "SELECT ID FROM PRODUCTS WHERE IS_ACTIVE = 'active' AND END_DATE < ?";
	public static final String GET_CATEGORY_QUERY = "SELECT * FROM CATEGORIES WHERE ID = ?";
	public static final String ADD_SUBCATEGORY_QUERY = "INSERT INTO CATEGORIES(ID, PARENT_ID, PRODUCT_ID, NAME) VALUES (CATEGORY_ID_S.NEXTVAL, ?, ?, ?)";
	public static final String ADD_CATEGORY_QUERY = "INSERT INTO CATEGORIES(ID, PARENT_ID, PRODUCT_ID, NAME) VALUES (CATEGORY_ID_S.NEXTVAL, CATEGORY_ID_S.CURRVAL, ?, ?)";
	public static final String GET_ALL_CATEGORIES_QUERY = "SELECT ID FROM CATEGORIES";
	public static final String GET_PRODUCTS_BY_CATEGORY_QUERY = "SELECT PRODUCT_ID FROM CATEGORIES WHERE ID = ?";
	public static final String GET_SUBCATEGORY = "SELECT ID FROM CATEGORIES WHERE PARENT_ID = ?";
	public static final String GET_SALLERS_TRANSACTIONS_QUERY = "SELECT ID FROM TRANSACTIONS WHERE SELLER_ID = ?";
	public static final String GET_BUYERS_TRANSACTIONS_QUERY = "SELECT ID FROM TRANSACTIONS WHERE BUYER_ID = ?";
	public static final String ADD_TRANSACTION_QUERY = "INSERT INTO TRANSACTIONS(ID, BUYER_ID, SELLER_ID, PRODUCT_ID, PRICE, SALE_DATE) VALUES(TRANSACTION_ID_S.NEXTVAL, ?, ?, ?, ?, ?)";
	public static final String GET_TRANSACTION_QUERY = "SELECT * FROM TRANSACTIONS WHERE ID = ?";
	public static final String GET_PICTURES_URL_QUERY = "SELECT URL FROM PICTURES WHERE PRODUCT_ID = ?";
	public static final String ADD_PICTURE_QUERY = "INSERT INTO PICTURES(ID, PRODUCT_ID, URL) VALUES(PICTURE_ID_S.NEXTVAL, ?, ?)";

	
	
	
    private static OracleDataBase instance;
    
    public static synchronized OracleDataBase getInstance() {
        if (instance == null) {
        	instance = new OracleDataBase();
        }
        return instance;
    }
    
    private OracleDataBase(){};
	
	
    private static Connection connectionInstance;
	
    private static Connection getConnection() {
        Locale.setDefault(Locale.ENGLISH);
        if (connectionInstance != null) {
            return connectionInstance;
        } else {
            Hashtable ht = new Hashtable();
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
            try {
                Context ctx = new InitialContext(ht);
                DataSource dataSource = (DataSource) ctx.lookup("jdbc/JDBCDS");
                connectionInstance = dataSource.getConnection();
            } catch (NamingException e) {
            	e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connectionInstance;
    }
    
    
    
	

    //------------------------------------------------------
	//-----------------------XXX:USER-----------------------
	//------------------------------------------------------
		
	
	/**
	 * Creating of a new User.
	 * @return false if login or email is not free or happened some error.
	 * */
	public boolean addUser(String login, String password, String name, String secondName,
		String birthDate, String eMail, String phone) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(ADD_USER_QUERY);
			Date date = format.parse(birthDate);
						preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, secondName);
			preparedStatement.setDate(5, (java.sql.Date) date);
			preparedStatement.setString(6, eMail);
			preparedStatement.setString(7, phone);
			preparedStatement.setString(8, "user");
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User getUser(int id) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_USER_QUERY);
			preparedStatement.setLong(1, id); 
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			String login = rs.getString("LOGIN");
			String password = rs.getString("PASSWORD");
			String name = rs.getString("NAME");
			String secondName = rs.getString("SECOND_NAME");
			Date birthDate = rs.getDate("BIRTH");
			String eMail = rs.getString("EMAIL");
			String phone = rs.getString("PHONE");
			String status = rs.getString("STATUS");
			
			byte age = (byte)((new Date().getTime() - birthDate.getTime()) / 365/24/60/60/1000);

			return new User(id, login, password, name, secondName, age, eMail, phone, status);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isLoginFree(String login) {
		int count = 0;
		try {
			Connection connection = getConnection();	
			PreparedStatement preparedStatement =
					connection.prepareStatement(IS_LOGIN_FREE_QUERY);
			preparedStatement.setString(1, login);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count == 0;
	}
	
	public boolean isEmailFree(String login) {
		int count = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(IS_EMAIL_FREE_QUERY);
			preparedStatement.setString(1, login);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count == 0;
	}
	
	
	/**
	 * Check login and password and do authorization.
	 * @return id of authorizated user. If login or password is wrong - method returns -1.
	 * 
	 * */
	public int authorization(String login, String password) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(AUTHORIZATION_QUERY);
			preparedStatement.setString(1, login);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			if (rs.getString("PASSWORD").equals(password))
				return rs.getInt("ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean isAdmin(int userID) {
		int count = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(IS_USER_ADMIN_QUERY);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count != 0;
	}
	
	

	//------------------------------------------------------
	//----------------XXX:PRODUCT FOLLOWING-----------------
	//------------------------------------------------------
	
	
	public boolean followProduct(int productID, int followerID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(FOLLOW_PRODUCT_QUERY);
			preparedStatement.setInt(1, followerID);
			preparedStatement.setInt(2, productID);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean isFollowProduct(int followerID, int productID) {
		int count = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(IS_FOLLOW_QUERY);
			preparedStatement.setInt(1, followerID);
			preparedStatement.setInt(2, productID);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count == 0;
	}

	public boolean unfollowProduct(int productID, int followerID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(UNFOLLOW_QUERY);
			preparedStatement.setInt(1, followerID);
			preparedStatement.setInt(2, productID);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns list of products ID, that followed by user.
	 * With this ID's we can restore objects "Product".
	 * */
	public List<Integer> getFollowingProductsID(int userID) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_FOLLOWING_PRODUCTS_QUERY);
			preparedStatement.setInt(1, userID);
			ResultSet rs = preparedStatement.executeQuery();
		
			while(rs.next()) 
				list.add(rs.getInt("PRODUCT_ID"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	//------------------------------------------------------
	//---------------------XXX:PRODUCT----------------------
	//------------------------------------------------------
	
	
	
	public boolean addProduct(int sellerID, String name, String description,
    		Date startDate, Date endDate, int startPrice, int buyoutPrice) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(ADD_PRODUCT_QUERY);
			preparedStatement.setInt(1, sellerID);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, description);
			preparedStatement.setDate(4, new java.sql.Date(startDate.getTime()));
			preparedStatement.setDate(5, new java.sql.Date(endDate.getTime()));
			preparedStatement.setInt(6, startPrice);
			preparedStatement.setInt(7, buyoutPrice);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Product getProduct(int id) {
		Product product = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_PRODUCT_QUERY);
			preparedStatement.setLong(1, id); 
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			int sellerID = rs.getInt("SELLER_ID");
			String name = rs.getString("NAME");
			String description = rs.getString("DESCRIPTION");
			Date startDate = rs.getDate("START_DATE");
			Date endDate = rs.getDate("END_DATE");
			int startPrice = rs.getInt("START_PRICE");
			int buyoutPrice = rs.getInt("BUYOUT_PRICE");
			int currentPrice = rs.getInt("CURRENT_PRICE");
			int currentBuyerID = rs.getInt("CURRENT_BUYER_ID");
			String activity = rs.getString("IS_ACTIVE");

			product = new Product(id, sellerID, name, description,
		    		startDate, endDate, startPrice, buyoutPrice,
		    		currentPrice, currentBuyerID, activity.equals("active"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	public boolean disactivateProduct(int productID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(DISACTIVATE_PRODUCT_QUERY);
			preparedStatement.setInt(1, productID);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isProductExist(int productID) {
		int count = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(IS_PRODUCT_EXIST_QUERY);
			preparedStatement.setInt(1, productID);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count != 0;
	}
	
	public boolean isProductActive(int productID) {
		int count = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(IS_PRODUCT_ACTIVE_QUERY);
			preparedStatement.setInt(1, productID);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count != 0;
	}

	public int getCurrentPrice(int productID) {
		int price = -1;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(QET_CURRENT_PRICE_QUERY);
			preparedStatement.setLong(1, productID); 
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			price = rs.getInt("CURRENT_PRICE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}
	
	
	public boolean makeBet(int productID, int buyerID, int price) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(MAKE_A_BET_QUERY);
			preparedStatement.setInt(1, price);
			preparedStatement.setInt(2, buyerID);
			preparedStatement.setInt(3, productID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * @return list of products id, that was finished just now
	 * */
	public ArrayList<Integer> finishAuctions() {
		ArrayList<Product> list = new ArrayList<Product>();
		ArrayList<Integer> listID = new ArrayList<Integer>();
		
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(FINISH_AUCTIONS_QUERY);
			preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) 
				list.add(getProduct(rs.getInt("PRODUCT_ID")));
			
			for (Product product : list) {
				disactivateProduct(product.getId());
				if (product.getCurrentPrice() != 0) {
					addTransaction(product.getCurrentBuyerID(),
							product.getSellerID(), product.getId(),
							product.getCurrentPrice(), product.getEndDate());
					listID.add(product.getId());
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listID;
	}
	
	
	//------------------------------------------------------
	//--------------------XXX:CATEGORY----------------------
	//------------------------------------------------------
	
	@Override
	public Category getCategory(int categoryID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_CATEGORY_QUERY);
			preparedStatement.setLong(1, categoryID); 
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			Integer parentID = rs.getInt("PARENT_ID");
			int productID = rs.getInt("PRODUCT_ID");
			String name = rs.getString("NAME");
			return new Category(categoryID, parentID, productID, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addCategory(int parentID, int productID, String name) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(ADD_SUBCATEGORY_QUERY);
			preparedStatement.setInt(1, parentID);
			preparedStatement.setInt(2, productID);
			preparedStatement.setString(3, name);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean addCategory(int productID, String name) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(ADD_CATEGORY_QUERY);
			preparedStatement.setInt(1, productID);
			preparedStatement.setString(2, name);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Integer> getAllCategoriesID() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_ALL_CATEGORIES_QUERY);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();

			while(rs.next()) 
				list.add(rs.getInt("ID"));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Integer> getProductsByCategory(int categoryID) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY_QUERY);
			preparedStatement.setInt(1, categoryID);
			
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			while(rs.next()) 
				list.add(rs.getInt("PRODUCT_ID"));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> getSubcategories(int categoryID) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_SUBCATEGORY);
			preparedStatement.setInt(1, categoryID);
		
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			while(rs.next()) 
				list.add(rs.getInt("ID"));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//------------------------------------------------------
	//------------------XXX:TRANSACTION---------------------
	//------------------------------------------------------
	
	
	@Override
	public List<Integer> getSalersTransaction(int sellerID) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_SALLERS_TRANSACTIONS_QUERY);
			preparedStatement.setInt(1, sellerID);
			
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			while(rs.next()) 
				list.add(rs.getInt("ID"));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> getBuyersTransaction(int buyerID) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_BUYERS_TRANSACTIONS_QUERY);
			preparedStatement.setInt(1, buyerID);
			
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			while(rs.next()) 
				list.add(rs.getInt("ID"));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean addTransaction(int buyerID, int sellerID, int productID, int price, Date saleDate) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(ADD_TRANSACTION_QUERY);
			preparedStatement.setInt(1, buyerID);
			preparedStatement.setInt(2, sellerID);
			preparedStatement.setInt(3, productID);
			preparedStatement.setInt(4, price);
			preparedStatement.setDate(5, new java.sql.Date(saleDate.getTime()));
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Transaction getTransaction(int transactionID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_TRANSACTION_QUERY);
			preparedStatement.setLong(1, transactionID); 
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			int buyerID = rs.getInt("BUYER_ID");
			int sellerID = rs.getInt("SELLER_ID");
			int productID = rs.getInt("PRODUCT_ID");
			int price = rs.getInt("PRICE");
			Date saleDate = rs.getDate("SALE_DATE");
			return new Transaction(transactionID, buyerID, sellerID, productID, price, saleDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//------------------------------------------------------
	//--------------------XXX:PICTURES----------------------
	//------------------------------------------------------
	
	
	@Override
	public List<String> getPicturesURLs(int productID) {
		List<String> list = new ArrayList<String>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(GET_PICTURES_URL_QUERY);
			preparedStatement.setInt(1, productID);
			
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			
			while(rs.next()) 
				list.add(rs.getString("URL"));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean addPictures(int productID, String URL) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement =
					connection.prepareStatement(ADD_PICTURE_QUERY);
			preparedStatement.setInt(1, productID);
			preparedStatement.setString(2, URL);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<Integer> getUsersProducts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
 
}
