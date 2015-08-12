package ua.sumdu.group73.model;

import ua.sumdu.group73.model.interfaces.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.sumdu.group73.model.objects.*;

public class OracleDataBase implements UserDBInterface, PicturesDBInterface,
        TransactionDBIinterface, ProductDBInterface,
        FollowingDBInterface, CategoriesDBInterface {

	private static final Logger log = Logger.getLogger(OracleDataBase.class);
	
    private static OracleDataBase instance;
    
    
    private Connection conn = null;
    
    private OracleDataBase() {}

    
    private void initConnection() {
    	log.info("Method initConnection starts.....");
        if (conn == null) 
            conn = getConnection();
    }
    
    private void closeConnection() {
    	log.info("Method closeConnection starts.....");
    	if (conn != null)
	    	try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				log.error("SQLException in closeConnection()", e);
			}
    }
    
    
    public static synchronized OracleDataBase getInstance() {
    	log.info("Method getInstance starts.....");
        if (instance == null) 
            instance = new OracleDataBase();
        return instance;
    }
    
    private Connection getConnection() {
    	log.info("Method getConnection starts.....");
        Locale.setDefault(Locale.ENGLISH);
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/JDBCDS");
            return dataSource.getConnection();
        } catch (NamingException e) {
            log.error("NamingException in getConnection()", e);
        } catch (SQLException e) {
            log.error("SQLException in getConnection()", e);
        }
        return null;
    }
    

    
    //------------------------------------------------------
    //-----------------------XXX:USER-----------------------
    //------------------------------------------------------

    
    /**
     * Creating of a new User.
     * date is long
     * @return false if login or email is not free or happened some error.
     */
    public boolean addUser(String login, String password, String name, String secondName,
                           long birthDate, String eMail, String phone) {
    	log.info("Method addUser starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_USER)) {    		
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, secondName);
            preparedStatement.setDate(5, new java.sql.Date(birthDate));
            preparedStatement.setString(6, eMail);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, "unactivated");
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addUser()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public User getUser(int id) {
    	log.info("Method getUser starts.....");
    	User user = null;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USER)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            String login = rs.getString("LOGIN");
	            String password = rs.getString("PASSWORD");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            int age = rs.getInt("AGE");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	            Date regDate = rs.getTimestamp("REGISTRATION_DATE");
	            
	            user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
            }
        } catch (SQLException e) {
            log.error("SQLException in getUser()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }
    
    public User getUser(String login) {
    	log.info("Method getUser starts.....");
    	User user = null;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USER_BY_ID)) {
            preparedStatement.setString(1, login);
            try (ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            int id = rs.getInt("ID");
	            String password = rs.getString("PASSWORD");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            int age = rs.getInt("AGE");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	            Date regDate = rs.getTimestamp("REGISTRATION_DATE");
	            
	            user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
            }
        } catch (SQLException e) {
            log.error("SQLException in getUser()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }

	public boolean isLoginFree(String login) {
		log.info("Method isLoginFree starts.....");
		boolean result = false;
		initConnection();
	    try (PreparedStatement preparedStatement =
	    		conn.prepareStatement(Queries.IS_LOGIN_FREE)) {	
	    	preparedStatement.setString(1, login);
	    	try(ResultSet rs = preparedStatement.executeQuery()) {
	    		result = !rs.next();
	    	}
	    } catch (SQLException e) {
	        log.error("SQLException in isLoginFree()", e);
	    } finally {
	    	closeConnection();
	    }
	    return result;
	}

    public boolean isEmailFree(String login) {
    	log.info("Method isEmailFree starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_EMAIL_FREE)) {
            preparedStatement.setString(1, login);
            try(ResultSet rs = preparedStatement.executeQuery()) {
            	result = !rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isEmailFree()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }


    /**
     * Check login and password and do authorization.
     *
     * @return id of authorizated user. If login or password is wrong - method returns -1.
     */
    public User authorization(String login, String password) {
    	log.info("Method authorization starts.....");
    	User user = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.AUTHORIZATION)) {
            preparedStatement.setString(1, login);
            try(ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
		            int id = rs.getInt("ID");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            String eMail = rs.getString("EMAIL");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		            int age = rs.getByte("AGE");
		            Date regDate = rs.getTimestamp("REGISTRATION_DATE");
		            
		            if (rs.getString("PASSWORD").equals(password) &&
				       (!status.equals("unactivated")) &&
				       (!status.equals("banned")))
		            		user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
	            }
            }
        } catch (SQLException e) {
            log.error("SQLException in authorization()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }


    public User authorizationByEmail(String eMail, String password) {
    	log.info("Method authorizationByEmail starts.....");
    	User user = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.AUTHORIZATION_BY_EMAIL)) {
        	preparedStatement.setString(1, eMail);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            if (rs.next()) {
		            int id = rs.getInt("ID");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            String login = rs.getString("LOGIN");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		            int age = rs.getByte("AGE");
		            Date regDate = rs.getTimestamp("REGISTRATION_DATE");
		            
		            if (rs.getString("PASSWORD").equals(password) &&
		            	(!status.equals("unactivated")) &&
		            	(!status.equals("banned")))
		            		user = new User(id, login, password, name, secondName, age, eMail, phone, status, regDate);
	            }
            }
        } catch (SQLException e) {
            log.error("SQLException in authorizationByEmail()", e);
        } finally {
        	closeConnection();
        }
        return user;
    }


    public boolean isAdmin(int userID) {
    	log.info("Method isAdmin starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_USER_ADMIN)){
        	preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isAdmin()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public List<Product> getUsersProducts(int userID) {
    	log.info("Method getUsersProducts starts.....");
        List<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_USERS_PRODUCTS)) {
            preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	                list.add(
	                		new Product(
	                				rs.getInt("PRODUCT_ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				rs.getTimestamp("START_DATE"),
	                				rs.getTimestamp("END_DATE"),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getUsersProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<User> getAllUsers() {
    	log.info("Method getAllUsers starts.....");
    	List<User> list = new ArrayList<User>();
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_USERS)) {
    		try (ResultSet rs = preparedStatement.executeQuery()) {
    			while (rs.next()) {
    				int id = rs.getInt("ID");
		            String login = rs.getString("LOGIN");
		            String password = rs.getString("PASSWORD");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            int age = rs.getByte("AGE");
		            String eMail = rs.getString("EMAIL");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		            Date regDate = rs.getTimestamp("REGISTRATION_DATE");
		            
		            list.add(new User(id, login, password, name, secondName, age, eMail, phone, status, regDate));
    			}
    		}
        } catch (SQLException e) {
            log.error("SQLException in getAllUsers()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}
	
	@Override
	public boolean setUserBan(int userID) {
    	log.info("Method setUserBan starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.SET_USER_BAN)) {
        	preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in setUserBan()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}
	
	@Override
	public boolean unBanAllUsers() {
    	log.info("Method unBanAllUsers starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.UNBAN_ALL_USERS)) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in unBanAllUsers()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}


	@Override
	public boolean activateUser(String login) {
    	log.info("Method activateUser starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ACTIVATE_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in activateUser()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}

	
	@Override
	public boolean removeUnactivatedUsers() {
    	log.info("Method removeUnactivatedUsers starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.DELETE_UNACTIVATED_USERS)) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in removeUnactivatedUsers()", e);
        } finally {
        	closeConnection();
        }
        return result;
	}


    //------------------------------------------------------
    //----------------XXX:PRODUCT FOLLOWING-----------------
    //------------------------------------------------------


    public boolean followProduct(int productID, int followerID) {
    	log.info("Method followProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FOLLOW_PRODUCT)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in followProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean isFollowProduct(int followerID, int productID) {
    	log.info("Method isFollowProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_FOLLOW)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isFollowProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean unfollowProduct(int productID, int followerID) {
    	log.info("Method unfollowProduct starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.UNFOLLOW)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in unfollowProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    /**
     * Returns list of products ID, that followed by user.
     * With this ID's we can restore objects "Product".
     */
    public List<Product> getFollowingProductsID(int userID) {
    	log.info("Method getFollowingProductsID starts.....");
        List<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_FOLLOWING_PRODUCTS)) {
            preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	while (rs.next())
            		list.add(getProduct(rs.getInt("PRODUCT_ID")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getFollowingProductsID()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }


    //------------------------------------------------------
    //---------------------XXX:PRODUCT----------------------
    //------------------------------------------------------


    public boolean addProduct(int sellerID, String name, String description,
                              Date startDate, Date endDate, int startPrice, int buyoutPrice) {
    	log.info("Method addProduct starts.....");
    	boolean result = false;
    	initConnection();
    	try (
        	PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_PRODUCT);
        ) {
            preparedStatement.setInt(1, sellerID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setDate(4, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(endDate.getTime()));
            preparedStatement.setInt(6, startPrice);
            preparedStatement.setInt(7, buyoutPrice);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public Product getProduct(int id) {
    	log.info("Method getProduct starts.....");
    	Product product = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PRODUCT)) {
            preparedStatement.setLong(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	
	            int sellerID = rs.getInt("SELLER_ID");
	            String name = rs.getString("NAME");
	            String description = rs.getString("DESCRIPTION");
	            Date startDate = rs.getTimestamp("START_DATE");
	            Date endDate = rs.getTimestamp("END_DATE");
	            int startPrice = rs.getInt("START_PRICE");
	            int buyoutPrice = rs.getInt("BUYOUT_PRICE");
	            int currentPrice = rs.getInt("CURRENT_PRICE");
	            int currentBuyerID = rs.getInt("CURRENT_BUYER_ID");
	            String activity = rs.getString("IS_ACTIVE");
	
	            product =  new Product(id, sellerID, name, description,
	                    startDate, endDate, startPrice, buyoutPrice,
	                    currentPrice, currentBuyerID, activity.equals("active"));
            }
        } catch (SQLException e) {
            log.error("SQLException in getProduct()", e);
        } finally {
        	closeConnection();
        }
        return product;
    }

    public boolean disactivateProduct(int productID) {
    	log.info("Method disactivateProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.DISACTIVATE_PRODUCT)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in disactivateProduct()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean isProductActive(int productID) {
    	log.info("Method isProductActive starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_PRODUCT_ACTIVE)) {
            preparedStatement.setInt(1, productID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error("SQLException in isProductActive()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public int getCurrentPrice(int productID) {
    	log.info("Method getCurrentPrice starts.....");
    	initConnection();
    	int result = -1;
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.QET_CURRENT_PRICE)) {
            preparedStatement.setLong(1, productID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            result = rs.getInt("CURRENT_PRICE");
            }
        } catch (SQLException e) {
            log.error("SQLException in getCurrentPrice()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }


    public boolean makeBet(int productID, int buyerID, int price) {
    	log.info("Method makeBet starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.MAKE_A_BET)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, buyerID);
            preparedStatement.setInt(3, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in makeBet()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public boolean buyout(int productID, int buyerID) {
    	log.info("Method makeBet starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.BUYOUT)) {
            preparedStatement.setInt(1, buyerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in makeBet()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    /**
     * @return list of products id, that was finished just now
     */
    public ArrayList<Product> finishAuctions() {
    	log.info("Method finishAuctions starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FINISH_AUCTIONS)) {
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				rs.getTimestamp("START_DATE"),
	                				rs.getTimestamp("END_DATE"),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
            }
        } catch (SQLException e) {
            log.error("SQLException in finishAuctions()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
    public List<Product> getAllProducts() {
    	log.info("Method getAllProducts starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_PRODUCTS)) {
           
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				rs.getTimestamp("START_DATE"),
	                				rs.getTimestamp("END_DATE"),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
        	}            
        } catch (SQLException e) {
            log.error("SQLException in getAllProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Product> getAllActiveProducts() {
    	log.info("Method getAllActiveProducts starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_PRODUCTS)) {
           
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                if (rs.getString("IS_ACTIVE").equals("active"))
	            	list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				rs.getTimestamp("START_DATE"),
	                				rs.getTimestamp("END_DATE"),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				true));
	            }
        	}            
        } catch (SQLException e) {
            log.error("SQLException in getAllActiveProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}
    
	@Override
	public List<Product> findProducts(String substring) {
    	log.info("Method findProducts starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FIND_PRODUCTS)) {
        	preparedStatement.setString(1, "%" + substring + "%");
        	preparedStatement.setString(2, "%" + substring + "%");
            
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				rs.getTimestamp("START_DATE"),
	                				rs.getTimestamp("END_DATE"),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
        	}
        } catch (SQLException e) {
            log.error("SQLException in findProducts()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }


    //------------------------------------------------------
    //--------------------XXX:CATEGORY----------------------
    //------------------------------------------------------

    public Category getCategory(int categoryID) {
    	log.info("Method getCategory starts.....");
    	Category category = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_CATEGORY)) {
            preparedStatement.setLong(1, categoryID);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	            String name = rs.getString("NAME");
	            int parentID = rs.getInt("PARENT_ID");
	            category = new Category(categoryID, parentID, name);
            }
        } catch (SQLException e) {
            log.error("SQLException in getCategory()", e);
        } finally {
        	closeConnection();
        }
        return category;
    }

    public boolean addCategory(int parentID, String name) {
    	log.info("Method addCategory(int, String) starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_CATEGORY)) {
            preparedStatement.setInt(1, parentID);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addCategory()", e);
        } finally {
        	closeConnection();
        }      
        return result;
    }

    public boolean addCategory(String name) {
    	log.info("Method addCategory(String) starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_CATEGORY)) {
        	preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addCategory(int, String)", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public List<Category> getAllCategories() {
    	log.info("Method getAllCategories starts.....");
        List<Category> list = new ArrayList<Category>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_CATEGORIES)) {
        	 try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	        		list.add(
	                		new Category(
	                				rs.getInt("ID"),
	                				rs.getInt("PARENT_ID"),
	                				rs.getString("NAME")));
        	 }
        } catch (SQLException e) {
            log.error("SQLException in getAllCategories()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Category> getCategoriesOfProduct(int productID) {
    	log.info("Method getAllCategories starts.....");
        List<Category> list = new ArrayList<Category>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_CATEGORIES_OF_PRODUCT)) {
        	preparedStatement.setInt(1, productID);

            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	        		list.add(
	                		new Category(
	                				rs.getInt("ID"),
	                				rs.getInt("PARENT_ID"),
	                				rs.getString("NAME")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getAllCategories()", e);
        } finally {
        	closeConnection();
        }
        return list;
	}

	@Override
	public List<Product> getProductsByCategory(int categoryID) {
    	log.info("Method getProductsByCategory starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PRODUCTS_BY_CATEGORY)) {	
        	preparedStatement.setInt(1, categoryID);
            
        	try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) {
	                list.add(
	                		new Product(
	                				rs.getInt("ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getString("NAME"),
	                				rs.getString("DESCRIPTION"),
	                				rs.getTimestamp("START_DATE"),
	                				rs.getTimestamp("END_DATE"),
	                				rs.getInt("START_PRICE"),
	                				rs.getInt("BUYOUT_PRICE"),
	                				rs.getInt("CURRENT_PRICE"),
	                				rs.getInt("CURRENT_BUYER_ID"),
	                				rs.getString("IS_ACTIVE").equals("active")));
	            }
            }
        } catch (SQLException e) {
            log.error("SQLException in getProductsByCategory()", e);
        } finally {
        	closeConnection();
        }
		return list;
	}
  

    //------------------------------------------------------
    //------------------XXX:TRANSACTION---------------------
    //------------------------------------------------------


    public List<Transaction> getSalersTransaction(int sellerID) {
    	log.info("Method getSalersTransaction starts.....");
        List<Transaction> list = new ArrayList<Transaction>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_SALLERS_TRANSACTIONS)) {
            preparedStatement.setInt(1, sellerID);

            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next())
	                list.add(
	                		new Transaction(
	                				rs.getInt("ID"),
	                				rs.getInt("BUYER_ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getInt("PRODUCT_ID"),
	                				rs.getInt("PRICE"),
	                				rs.getTimestamp("SALE_DATE")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getSalersTransaction()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }

    public List<Transaction> getBuyersTransaction(int buyerID) {
    	log.info("Method getBuyersTransaction starts.....");
        List<Transaction> list = new ArrayList<Transaction>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_BUYERS_TRANSACTIONS)) {
            preparedStatement.setInt(1, buyerID);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next())
	                list.add(
	                		new Transaction(
	                				rs.getInt("ID"),
	                				rs.getInt("BUYER_ID"),
	                				rs.getInt("SELLER_ID"),
	                				rs.getInt("PRODUCT_ID"),
	                				rs.getInt("PRICE"),
	                				rs.getTimestamp("SALE_DATE")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getBuyersTransaction()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }

    public boolean addTransaction(int buyerID, int sellerID, int productID, int price, Date saleDate) {
    	log.info("Method addTransaction starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_TRANSACTION)) {
            preparedStatement.setInt(1, buyerID);
            preparedStatement.setInt(2, sellerID);
            preparedStatement.setInt(3, productID);
            preparedStatement.setInt(4, price);
            preparedStatement.setDate(5, new java.sql.Date(saleDate.getTime()));
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addTransaction()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }

    public Transaction getTransaction(int transactionID) {
    	log.info("Method getTransaction starts.....");
    	Transaction transaction = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_TRANSACTION)) {
            preparedStatement.setLong(1, transactionID);
            try(ResultSet rs = preparedStatement.executeQuery()){
	            rs.next();
	            int buyerID = rs.getInt("BUYER_ID");
	            int sellerID = rs.getInt("SELLER_ID");
	            int productID = rs.getInt("PRODUCT_ID");
	            int price = rs.getInt("PRICE");
	            Date saleDate = rs.getTimestamp("SALE_DATE");
	            transaction = new Transaction(transactionID, buyerID, sellerID, productID, price, saleDate);
            }
        } catch (SQLException e) {
            log.error("SQLException in getTransaction()", e);
        } finally {
        	closeConnection();
        }
        return transaction;
    }


    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------


    public List<Picture> getPictures(int productID) {
    	log.info("Method getPictures starts.....");
        List<Picture> list = new ArrayList<Picture>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PICTURES_OF_PRODUCT)) {
            preparedStatement.setInt(1, productID);

            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	            	list.add(new Picture(
	            			rs.getInt("ID"),
	            			rs.getInt("PRODUCT_ID"),
	            			rs.getString("URL")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getPictures()", e);
        } finally {
        	closeConnection();
        }
        return list;
    }

    public boolean addPictures(int productID, String URL) {
    	log.info("Method addPictures starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_PICTURE)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.setString(2, URL);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error("SQLException in addPictures()", e);
        } finally {
        	closeConnection();
        }
        return result;
    }


	@Override
	public List<Picture> getAllPictures() {
    	log.info("Method getAllPictures starts.....");
        List<Picture> list = new ArrayList<Picture>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_ALL_PICTURES)) {
            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next()) 
	            	list.add(new Picture(
	            			rs.getInt("ID"),
	            			rs.getInt("PRODUCT_ID"),
	            			rs.getString("URL")));
            }
        } catch (SQLException e) {
            log.error("SQLException in getAllPictures()", e);
        } finally {
        	closeConnection();
        }
        return list;
		
	}
}
