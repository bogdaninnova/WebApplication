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
	private static final String CLASSNAME = "OracleDataBase: ";
	
    private static OracleDataBase instance;
    
    
    private Connection conn = null;
    
    private OracleDataBase() {}

    
    private void initConnection() {
    	log.info(CLASSNAME + "Method initConnection starts.....");
        if (conn == null) 
            conn = getConnection();
    }
    
    private void closeConnection() {
    	log.info(CLASSNAME + "Method closeConnection starts.....");
    	try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    public static synchronized OracleDataBase getInstance() {
    	log.info(CLASSNAME + "Method getInstance starts.....");
        if (instance == null) 
            instance = new OracleDataBase();
        return instance;
    }
    
    private Connection getConnection() {
    	log.info(CLASSNAME + "Method getConnection starts.....");
        Locale.setDefault(Locale.ENGLISH);
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/JDBCDS");
            return dataSource.getConnection();
        } catch (NamingException e) {
            log.error(CLASSNAME + "NamingException in getConnection()" + e.getStackTrace());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getConnection()" + e.getStackTrace());
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
    	log.info(CLASSNAME + "Method addUser starts.....");
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
            preparedStatement.setString(8, "user");
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in addUser()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public User getUser(int id) {
    	log.info(CLASSNAME + "Method getUser starts.....");
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
	            Date birthDate = rs.getTimestamp("BIRTH");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");

	            byte age = (byte) ((new Date().getTime() - birthDate.getTime()) / 365 / 24 / 60 / 60 / 1000);
	            user = new User(id, login, password, name, secondName, age, eMail, phone, status);
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in getUser()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return user;
    }

    public boolean isLoginFree(String login) {
    	log.info(CLASSNAME + "Method isLoginFree starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_LOGIN_FREE)) {	
        	preparedStatement.setString(1, login);
        	try(ResultSet rs = preparedStatement.executeQuery()) {
        		result = !rs.next();
        	}
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in isLoginFree()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean isEmailFree(String login) {
    	log.info(CLASSNAME + "Method isEmailFree starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_EMAIL_FREE)) {
            preparedStatement.setString(1, login);
            try(ResultSet rs = preparedStatement.executeQuery()) {
            	result = !rs.next();
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in isEmailFree()" + e.getStackTrace());
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
    	log.info(CLASSNAME + "Method authorization starts.....");
    	User user = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.AUTHORIZATION)) {
            preparedStatement.setString(1, login);
            try(ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
		            int id = rs.getInt("ID");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            Date birthDate = rs.getTimestamp("BIRTH");
		            String eMail = rs.getString("EMAIL");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		                 
		            byte age = (byte) ((new Date().getTime() - birthDate.getTime()) / 365 / 24 / 60 / 60 / 1000);
		            
		            if (rs.getString("PASSWORD").equals(password))
		            	user = new User(id, login, password, name, secondName, age, eMail, phone, status);
	            }
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in authorization()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return user;
    }


    public User authorizationByEmail(String eMail, String password) {
    	log.info(CLASSNAME + "Method authorizationByEmail starts.....");
    	User user = null;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.AUTHORIZATION_BY_EMAIL)) {
            preparedStatement.setString(1, eMail);
            
            try(ResultSet rs = preparedStatement.executeQuery()){
	            if (rs.next()) {
		            int id = rs.getInt("ID");
		            String name = rs.getString("NAME");
		            String secondName = rs.getString("SECOND_NAME");
		            Date birthDate = rs.getTimestamp("BIRTH");
		            String login = rs.getString("LOGIN");
		            String phone = rs.getString("PHONE");
		            String status = rs.getString("STATUS");
		                 
		            byte age = (byte) ((new Date().getTime() - birthDate.getTime()) / 365 / 24 / 60 / 60 / 1000);
		            
		            if (rs.getString("PASSWORD").equals(password))
		            	user = new User(id, login, password, name, secondName, age, eMail, phone, status);
	            }
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in authorizationByEmail()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return user;
    }


    public boolean isAdmin(int userID) {
    	log.info(CLASSNAME + "Method isAdmin starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_USER_ADMIN)){
        	preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in isAdmin()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }
    
    public List<Product> getUsersProducts(int userID) {
    	log.info(CLASSNAME + "Method getUsersProducts starts.....");
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
            log.error(CLASSNAME + "SQLException in getUsersProducts()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }


    //------------------------------------------------------
    //----------------XXX:PRODUCT FOLLOWING-----------------
    //------------------------------------------------------


    public boolean followProduct(int productID, int followerID) {
    	log.info(CLASSNAME + "Method followProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FOLLOW_PRODUCT)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in followProduct()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;

    }

    public boolean isFollowProduct(int followerID, int productID) {
    	log.info(CLASSNAME + "Method isFollowProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_FOLLOW)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in isFollowProduct()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean unfollowProduct(int productID, int followerID) {
    	log.info(CLASSNAME + "Method unfollowProduct starts.....");
    	boolean result = false;
    	initConnection();
    	try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.UNFOLLOW)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in unfollowProduct()" + e.getStackTrace());
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
    	log.info(CLASSNAME + "Method getFollowingProductsID starts.....");
        List<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_FOLLOWING_PRODUCTS)) {
            preparedStatement.setInt(1, userID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	while (rs.next())
            		list.add(getProduct(rs.getInt("PRODUCT_ID")));
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in getFollowingProductsID()" + e.getStackTrace());
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
    	log.info(CLASSNAME + "Method addProduct starts.....");
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
            log.error(CLASSNAME + "SQLException in addProduct()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public Product getProduct(int id) {
    	log.info(CLASSNAME + "Method getProduct starts.....");
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
            log.error(CLASSNAME + "SQLException in getProduct()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return product;
    }

    public boolean disactivateProduct(int productID) {
    	log.info(CLASSNAME + "Method disactivateProduct starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.DISACTIVATE_PRODUCT)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in disactivateProduct()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public boolean isProductActive(int productID) {
    	log.info(CLASSNAME + "Method isProductActive starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.IS_PRODUCT_ACTIVE)) {
            preparedStatement.setInt(1, productID);
            try(ResultSet rs = preparedStatement.executeQuery()){
            	result = rs.next();
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in isProductActive()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public int getCurrentPrice(int productID) {
    	log.info(CLASSNAME + "Method getCurrentPrice starts.....");
    	initConnection();
    	int result = -1;
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.QET_CURRENT_PRICE)) {
            preparedStatement.setLong(1, productID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
	            rs.next();
	            result = rs.getInt("CURRENT_PRICE");
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in getCurrentPrice()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }


    public boolean makeBet(int productID, int buyerID, int price) {
    	log.info(CLASSNAME + "Method makeBet starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.MAKE_A_BET)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, buyerID);
            preparedStatement.setInt(3, productID);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in makeBet()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    /**
     * @return list of products id, that was finished just now
     */
    public ArrayList<Product> finishAuctions() {
    	log.info(CLASSNAME + "Method finishAuctions starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.FINISH_AUCTIONS)) {
            preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
            
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
	                
	                disactivateProduct(rs.getInt("PRODUCT_ID"));
	                
	                if (rs.getInt("CURRENT_PRICE") != 0) {
	                    addTransaction(
	                    		rs.getInt("CURRENT_BUYER_ID"),
	                    		rs.getInt("SELLER_ID"),
	                            rs.getInt("PRODUCT_ID"),
	                            rs.getInt("CURRENT_PRICE"),
	                            rs.getDate("END_DATE"));
	                }
	            }
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in finishAuctions()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }
    
    public List<Product> getAllProducts() {
    	log.info(CLASSNAME + "Method getAllProducts starts.....");
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
            log.error(CLASSNAME + "SQLException in finishAuctions()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Product> findProducts(String substring) {
    	log.info(CLASSNAME + "Method findProducts starts.....");
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
            log.error(CLASSNAME + "SQLException in findProducts()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }


    //------------------------------------------------------
    //--------------------XXX:CATEGORY----------------------
    //------------------------------------------------------

    public Category getCategory(int categoryID) {
    	log.info(CLASSNAME + "Method getCategory starts.....");
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
            log.error(CLASSNAME + "SQLException in getCategory()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return category;
    }

    public boolean addCategory(int parentID, String name) {
    	log.info(CLASSNAME + "Method addCategory(int, String) starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_CATEGORY)) {
            preparedStatement.setInt(1, parentID);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in addCategory()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }      
        return result;
    }

    public boolean addCategory(String name) {
    	log.info(CLASSNAME + "Method addCategory(String) starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_CATEGORY)) {
        	preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in addCategory(int, String)" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public List<Category> getAllCategories() {
    	log.info(CLASSNAME + "Method getAllCategories starts.....");
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
            log.error(CLASSNAME + "SQLException in getAllCategories()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }
    
	@Override
	public List<Category> getCategoriesOfProduct(int productID) {
    	log.info(CLASSNAME + "Method getAllCategories starts.....");
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
            log.error(CLASSNAME + "SQLException in getAllCategories()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
	}

	@Override
	public List<Product> getProductsByCategory(int categoryID) {
    	log.info(CLASSNAME + "Method getProductsByCategory starts.....");
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
            log.error(CLASSNAME + "SQLException in getProductsByCategory()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
		return list;
	}
  

    //------------------------------------------------------
    //------------------XXX:TRANSACTION---------------------
    //------------------------------------------------------


    public List<Transaction> getSalersTransaction(int sellerID) {
    	log.info(CLASSNAME + "Method getSalersTransaction starts.....");
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
            log.error(CLASSNAME + "SQLException in getSalersTransaction()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }

    public List<Transaction> getBuyersTransaction(int buyerID) {
    	log.info(CLASSNAME + "Method getBuyersTransaction starts.....");
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
            log.error(CLASSNAME + "SQLException in getBuyersTransaction()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }

    public boolean addTransaction(int buyerID, int sellerID, int productID, int price, Date saleDate) {
    	log.info(CLASSNAME + "Method addTransaction starts.....");
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
            log.error(CLASSNAME + "SQLException in addTransaction()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }

    public Transaction getTransaction(int transactionID) {
    	log.info(CLASSNAME + "Method getTransaction starts.....");
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
            log.error(CLASSNAME + "SQLException in getTransaction()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return transaction;
    }


    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------


    public List<String> getPicturesURLs(int productID) {
    	log.info(CLASSNAME + "Method getPicturesURLs starts.....");
        List<String> list = new ArrayList<String>();
        initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.GET_PICTURES_URL)) {
            preparedStatement.setInt(1, productID);

            try(ResultSet rs = preparedStatement.executeQuery()){
	            while (rs.next())
	                list.add(rs.getString("URL"));
            }
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in getPicturesURLs()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return list;
    }

    public boolean addPictures(int productID, String URL) {
    	log.info(CLASSNAME + "Method addPictures starts.....");
    	boolean result = false;
    	initConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Queries.ADD_PICTURE)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.setString(2, URL);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            log.error(CLASSNAME + "SQLException in addPictures()" + e.getStackTrace());
        } finally {
        	closeConnection();
        }
        return result;
    }
}