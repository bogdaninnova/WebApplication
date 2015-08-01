package ua.sumdu.group73.model;

import ua.sumdu.group73.model.interfaces.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.sumdu.group73.model.objects.*;

public class OracleDataBase implements UserDBInterface, PicturesDBInterface,
        TransactionDBIinterface, ProductDBInterface,
        FollowingDBInterface, CategoriesDBInterface {

	private static final Logger log = Logger.getLogger(ConnectionDB.class);
	private static final String CLASSNAME = "OracleDataBase: ";
	
    private static final String ADD_USER_QUERY = "INSERT INTO USERS(ID, LOGIN, PASSWORD, NAME, SECOND_NAME, BIRTH, EMAIL, PHONE, STATUS) VALUES(USER_ID_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_QUERY = "SELECT * FROM USERS WHERE ID = ?";
    private static final String IS_LOGIN_FREE_QUERY = "SELECT * FROM USERS WHERE LOGIN = ?";
    private static final String IS_EMAIL_FREE_QUERY = "SELECT * FROM USERS WHERE EMAIL = ?";
    private static final String AUTHORIZATION_QUERY = "SELECT * FROM USERS WHERE LOGIN = ?";
    private static final String AUTHORIZATION_BY_EMAIL_QUERY = "SELECT * FROM USERS WHERE EMAIL = ?";
    private static final String GET_USERS_PRODUCTS = "SELECT * FROM PRODUCTS WHERE SELLER_ID = ?";
    private static final String IS_USER_ADMIN_QUERY = "SELECT * FROM USERS WHERE ID = ? AND STATUS = 'admin'";
    private static final String FOLLOW_PRODUCT_QUERY = "INSERT INTO FOLLOWINGS(ID, FOLLOWER_ID, PRODUCT_ID) VALUES (FOLLOWING_ID_S.NEXTVAL, ?, ?)";
    private static final String IS_FOLLOW_QUERY = "SELECT * FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";
    private static final String UNFOLLOW_QUERY = "DELETE FROM FOLLOWINGS WHERE FOLLOWER_ID = ? AND PRODUCT_ID = ?";
    private static final String GET_FOLLOWING_PRODUCTS_QUERY = "SELECT PRODUCT_ID FROM FOLLOWINGS WHERE FOLLOWER_ID = ?";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO PRODUCTS(ID, SELLER_ID, NAME, DESCRIPTION, START_DATE, END_DATE, START_PRICE, BUYOUT_PRICE, CURRENT_PRICE, CURRENT_BUYER_ID, IS_ACTIVE) VALUES (PRODUCT_ID_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 0, NULL, 'active')";
    private static final String GET_PRODUCT_QUERY = "SELECT * FROM PRODUCTS WHERE ID = ?";
    private static final String DISACTIVATE_PRODUCT_QUERY = "UPDATE PRODUCTS SET IS_ACTIVE = 'disactive' WHERE ID = ?";
    private static final String IS_PRODUCT_ACTIVE_QUERY = "SELECT * FROM PRODUCTS WHERE ID = ? AND IS_ACTIVE = 'active'";
    private static final String QET_CURRENT_PRICE_QUERY = "SELECT CURRENT_PRICE FROM PRODUCTS WHERE ID = ?";
    private static final String MAKE_A_BET_QUERY = "UPDATE PRODUCTS SET CURRENT_PRICE = ?, CURRENT_BUYER_ID = ? WHERE ID = ?";
    private static final String FINISH_AUCTIONS_QUERY = "SELECT * FROM PRODUCTS WHERE IS_ACTIVE = 'active' AND END_DATE < ?";
    private static final String GET_CATEGORY_QUERY = "SELECT * FROM CATEGORIES WHERE ID = ?";
    private static final String ADD_SUBCATEGORY_QUERY = "INSERT INTO CATEGORIES(ID, PARENT_ID, PRODUCT_ID, NAME) VALUES (CATEGORY_ID_S.NEXTVAL, ?, ?, ?)";
    private static final String ADD_CATEGORY_QUERY = "INSERT INTO CATEGORIES(ID, PARENT_ID, PRODUCT_ID, NAME) VALUES (CATEGORY_ID_S.NEXTVAL, CATEGORY_ID_S.CURRVAL, ?, ?)";
    private static final String GET_ALL_CATEGORIES_QUERY = "SELECT * FROM CATEGORIES";
    private static final String GET_PRODUCTS_BY_CATEGORY_QUERY = "SELECT PRODUCT_ID FROM CATEGORIES WHERE NAME = ?";
    private static final String GET_SUBCATEGORY = "SELECT ID FROM CATEGORIES WHERE PARENT_ID = ?";
    private static final String GET_SALLERS_TRANSACTIONS_QUERY = "SELECT * FROM TRANSACTIONS WHERE SELLER_ID = ?";
    private static final String GET_BUYERS_TRANSACTIONS_QUERY = "SELECT * FROM TRANSACTIONS WHERE BUYER_ID = ?";
    private static final String ADD_TRANSACTION_QUERY = "INSERT INTO TRANSACTIONS(ID, BUYER_ID, SELLER_ID, PRODUCT_ID, PRICE, SALE_DATE) VALUES(TRANSACTION_ID_S.NEXTVAL, ?, ?, ?, ?, ?)";
    private static final String GET_TRANSACTION_QUERY = "SELECT * FROM TRANSACTIONS WHERE ID = ?";
    private static final String GET_PICTURES_URL_QUERY = "SELECT URL FROM PICTURES WHERE PRODUCT_ID = ?";
    private static final String ADD_PICTURE_QUERY = "INSERT INTO PICTURES(ID, PRODUCT_ID, URL) VALUES(PICTURE_ID_S.NEXTVAL, ?, ?)";


    private static OracleDataBase instance;
    private Connection conn = null;

    public static synchronized OracleDataBase getInstance() {
    	log.info(CLASSNAME + "Method getInstance starts.....");
        if (instance == null) {
            instance = new OracleDataBase();
            log.info(CLASSNAME + "Created new instance");
        }
        return instance;
    }

    private OracleDataBase() {
    	log.info(CLASSNAME + "Method OracleDataBase starts.....");
        if (conn == null) 
            this.conn = getConnection();
    }


    private Connection getConnection() {
    	log.info(CLASSNAME + "Method getConnection starts.....");
        Locale.setDefault(Locale.ENGLISH);
        Connection connectionInstance = null;
        Hashtable ht = new Hashtable();
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            Context ctx = new InitialContext(ht);
            log.info(CLASSNAME + "Created new InitialContext");
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/JDBCDS");
            log.info(CLASSNAME + "Created new DataSource");
            connectionInstance = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "NamingException in getConnection()");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getConnection()");
        }
        return connectionInstance;
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
    	try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
            		connection.prepareStatement(ADD_USER_QUERY);
        ) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, secondName);
            preparedStatement.setDate(5, new java.sql.Date(birthDate));
            preparedStatement.setString(6, eMail);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, "user");
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in addUser()");
        }
        return false;
    }

    public User getUser(int id) {
    	log.info(CLASSNAME + "Method getUser starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(GET_USER_QUERY);
   		) {
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

            byte age = (byte) ((new Date().getTime() - birthDate.getTime()) / 365 / 24 / 60 / 60 / 1000);

            return new User(id, login, password, name, secondName, age, eMail, phone, status);

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getUser()");
        }
        return null;
    }

    public boolean isLoginFree(String login) {
    	log.info(CLASSNAME + "Method isLoginFree starts.....");
        try (
        	Connection connection = getConnection();
            PreparedStatement preparedStatement =
            		connection.prepareStatement(IS_LOGIN_FREE_QUERY);
  		) {
        	preparedStatement.setString(1, login);
        	ResultSet rs = preparedStatement.executeQuery();

            return !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in isLoginFree()");
        }
        return false;
    }

    public boolean isEmailFree(String login) {
    	log.info(CLASSNAME + "Method isEmailFree starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(IS_EMAIL_FREE_QUERY);
        ) {
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in isEmailFree()");
        }
        return false;
    }


    /**
     * Check login and password and do authorization.
     *
     * @return id of authorizated user. If login or password is wrong - method returns -1.
     */
    public User authorization(String login, String password) {
    	log.info(CLASSNAME + "Method authorization starts.....");
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(AUTHORIZATION_QUERY);
        ) {
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
	            int id = rs.getInt("ID");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            Date birthDate = rs.getDate("BIRTH");
	            String eMail = rs.getString("EMAIL");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	                 
	            byte age = (byte) ((new Date().getTime() - birthDate.getTime()) / 365 / 24 / 60 / 60 / 1000);
	            
	            if (rs.getString("PASSWORD").equals(password))
	            	return new User(id, login, password, name, secondName, age, eMail, phone, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in authorization()");
        } 
        return null;
    }


    public User authorizationByEmail(String eMail, String password) {
    	log.info(CLASSNAME + "Method authorizationByEmail starts.....");
        try (
             Connection connection = getConnection();
             PreparedStatement preparedStatement =
            		 connection.prepareStatement(AUTHORIZATION_BY_EMAIL_QUERY);
        ) {

            preparedStatement.setString(1, eMail);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
	            int id = rs.getInt("ID");
	            String name = rs.getString("NAME");
	            String secondName = rs.getString("SECOND_NAME");
	            Date birthDate = rs.getDate("BIRTH");
	            String login = rs.getString("LOGIN");
	            String phone = rs.getString("PHONE");
	            String status = rs.getString("STATUS");
	                 
	            byte age = (byte) ((new Date().getTime() - birthDate.getTime()) / 365 / 24 / 60 / 60 / 1000);
	            
	            if (rs.getString("PASSWORD").equals(password))
	            	return new User(id, login, password, name, secondName, age, eMail, phone, status);
	            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in authorizationByEmail()");
        }
        return null;
    }


    public boolean isAdmin(int userID) {
    	log.info(CLASSNAME + "Method isAdmin starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(IS_USER_ADMIN_QUERY);
   		) {
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in isAdmin()");
        }
        return false;
    }
    
    public List<Product> getUsersProducts(int userID) {
    	log.info(CLASSNAME + "Method getUsersProducts starts.....");
        List<Product> list = new ArrayList<Product>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_USERS_PRODUCTS);
        ) {
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) 
                list.add(
                		new Product(
                				rs.getInt("PRODUCT_ID"),
                				rs.getInt("SELLER_ID"),
                				rs.getString("NAME"),
                				rs.getString("DESCRIPTION"),
                				rs.getDate("START_DATE"),
                				rs.getDate("END_DATE"),
                				rs.getInt("START_PRICE"),
                				rs.getInt("BUYOUT_PRICE"),
                				rs.getInt("CURRENT_PRICE"),
                				rs.getInt("CURRENT_BUYER_ID"),
                				rs.getString("IS_ACTIVE").equals("active")));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getUsersProducts()");
        }
        return list;
    }


    //------------------------------------------------------
    //----------------XXX:PRODUCT FOLLOWING-----------------
    //------------------------------------------------------


    public boolean followProduct(int productID, int followerID) {
    	log.info(CLASSNAME + "Method followProduct starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(FOLLOW_PRODUCT_QUERY);
        		) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in followProduct()");
        }
        return false;

    }

    public boolean isFollowProduct(int followerID, int productID) {
    	log.info(CLASSNAME + "Method isFollowProduct starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(IS_FOLLOW_QUERY);
        ) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in isFollowProduct()");
        }
        return false;
    }

    public boolean unfollowProduct(int productID, int followerID) {
    	log.info(CLASSNAME + "Method unfollowProduct starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(UNFOLLOW_QUERY);
   		) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in unfollowProduct()");
        }
        return false;
    }

    /**
     * Returns list of products ID, that followed by user.
     * With this ID's we can restore objects "Product".
     */
    public List<Product> getFollowingProductsID(int userID) {
    	log.info(CLASSNAME + "Method getFollowingProductsID starts.....");
        List<Product> list = new ArrayList<Product>();

        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(GET_FOLLOWING_PRODUCTS_QUERY);
        ) {
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                list.add(getProduct(rs.getInt("PRODUCT_ID")));
            
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getFollowingProductsID()");
        }

        return list;
    }


    //------------------------------------------------------
    //---------------------XXX:PRODUCT----------------------
    //------------------------------------------------------


    public boolean addProduct(int sellerID, String name, String description,
                              Date startDate, Date endDate, int startPrice, int buyoutPrice) {
    	log.info(CLASSNAME + "Method addProduct starts.....");
    	try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(ADD_PRODUCT_QUERY);
        ) {
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
            log.error(CLASSNAME + "SQLException in addProduct()");
        }
        return true;
    }

    public Product getProduct(int id) {
    	log.info(CLASSNAME + "Method getProduct starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(GET_PRODUCT_QUERY);	
        ) {
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

            return new Product(id, sellerID, name, description,
                    startDate, endDate, startPrice, buyoutPrice,
                    currentPrice, currentBuyerID, activity.equals("active"));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getProduct()");
        }

        return null;
    }

    public boolean disactivateProduct(int productID) {
    	log.info(CLASSNAME + "Method disactivateProduct starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(DISACTIVATE_PRODUCT_QUERY);
        ) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in disactivateProduct()");
        }
        return false;
    }

    public boolean isProductActive(int productID) {
    	log.info(CLASSNAME + "Method isProductActive starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(IS_PRODUCT_ACTIVE_QUERY);
   		) {
            preparedStatement.setInt(1, productID);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in isProductActive()");
        }
        return false;
    }

    public int getCurrentPrice(int productID) {
    	log.info(CLASSNAME + "Method getCurrentPrice starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(QET_CURRENT_PRICE_QUERY);
        ) {
            preparedStatement.setLong(1, productID);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt("CURRENT_PRICE");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getCurrentPrice()");
        }
        return -1;
    }


    public boolean makeBet(int productID, int buyerID, int price) {
    	log.info(CLASSNAME + "Method makeBet starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
        				connection.prepareStatement(MAKE_A_BET_QUERY);
        ) {
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, buyerID);
            preparedStatement.setInt(3, productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in makeBet()");
        }
        return true;
    }

    /**
     * @return list of products id, that was finished just now
     */
    public ArrayList<Product> finishAuctions() {
    	log.info(CLASSNAME + "Method finishAuctions starts.....");
        ArrayList<Product> list = new ArrayList<Product>();
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
                		connection.prepareStatement(FINISH_AUCTIONS_QUERY);
        		) {

            preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                list.add(
                		new Product(
                				rs.getInt("PRODUCT_ID"),
                				rs.getInt("SELLER_ID"),
                				rs.getString("NAME"),
                				rs.getString("DESCRIPTION"),
                				rs.getDate("START_DATE"),
                				rs.getDate("END_DATE"),
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
            
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in finishAuctions()");
        }

        return list;
    }


    //------------------------------------------------------
    //--------------------XXX:CATEGORY----------------------
    //------------------------------------------------------

    public Category getCategory(int categoryID) {
    	log.info(CLASSNAME + "Method getCategory starts.....");
        try (
        		Connection connection = getConnection();
        		PreparedStatement preparedStatement =
                		connection.prepareStatement(GET_CATEGORY_QUERY);
        		) {
            preparedStatement.setLong(1, categoryID);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Integer parentID = rs.getInt("PARENT_ID");
            int productID = rs.getInt("PRODUCT_ID");
            String name = rs.getString("NAME");
            return new Category(categoryID, parentID, productID, name);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getCategory()");
        }
        return null;
    }

    public boolean addCategory(int parentID, int productID, String name) {
    	log.info(CLASSNAME + "Method addCategory(int, int, String) starts.....");
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(ADD_SUBCATEGORY_QUERY);
        ) {
            preparedStatement.setInt(1, parentID);
            preparedStatement.setInt(2, productID);
            preparedStatement.setString(3, name);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in addCategory()");
        }        
        return false;
    }

    public boolean addCategory(int productID, String name) {
    	log.info(CLASSNAME + "Method addCategory(int, String) starts.....");
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(ADD_CATEGORY_QUERY);
        ) {
            preparedStatement.setInt(1, productID);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in addCategory(int, String)");
        }
        return false;
    }

    public List<Category> getAllCategories() {
    	log.info(CLASSNAME + "Method getAllCategories starts.....");
        List<Category> list = new ArrayList<Category>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_ALL_CATEGORIES_QUERY);
        ) {

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            while (rs.next())
                list.add(
                		new Category(
                				rs.getInt("ID"),
                				rs.getInt("PARENT_ID"),
                				rs.getInt("PRODUCT_ID"),
                				rs.getString("NAME")));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getAllCategories()");
        }
        return list;
    }

    public List<Product> getProductsByCategory(String categoryName) {
    	log.info(CLASSNAME + "Method getProductsByCategory starts.....");
        List<Product> list = new ArrayList<Product>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY_QUERY);
        ) {

            preparedStatement.setString(1, categoryName);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            while (rs.next())
                list.add(getProduct(rs.getInt("PRODUCT_ID")));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getProductsByCategory()");
        }
        return list;
    }

    @Deprecated
    public List<Category> getSubcategories(int categoryID) {
    	log.info(CLASSNAME + "Method getSubcategories starts.....");
        List<Category> list = new ArrayList<Category>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_SUBCATEGORY);
        ) {
            preparedStatement.setInt(1, categoryID);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            while (rs.next())
                list.add(
                		new Category(
                				rs.getInt("ID"),
                				rs.getInt("PARENT_ID"),
                				rs.getInt("PRODUCT_ID"),
                				rs.getString("NAME")));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getSubcategories()");
        }
        return list;
    }

    //------------------------------------------------------
    //------------------XXX:TRANSACTION---------------------
    //------------------------------------------------------


    public List<Transaction> getSalersTransaction(int sellerID) {
    	log.info(CLASSNAME + "Method getSalersTransaction starts.....");
        List<Transaction> list = new ArrayList<Transaction>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_SALLERS_TRANSACTIONS_QUERY);
        ) {

            preparedStatement.setInt(1, sellerID);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            while (rs.next())
                list.add(
                		new Transaction(
                				rs.getInt("ID"),
                				rs.getInt("BUYER_ID"),
                				rs.getInt("SELLER_ID"),
                				rs.getInt("PRODUCT_ID"),
                				rs.getInt("PRICE"),
                				rs.getDate("SALE_DATE")));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getSalersTransaction()");
        }
        return list;
    }

    public List<Transaction> getBuyersTransaction(int buyerID) {
    	log.info(CLASSNAME + "Method getBuyersTransaction starts.....");
        List<Transaction> list = new ArrayList<Transaction>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_BUYERS_TRANSACTIONS_QUERY);
        ) {

            preparedStatement.setInt(1, buyerID);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            while (rs.next())
                list.add(
                		new Transaction(
                				rs.getInt("ID"),
                				rs.getInt("BUYER_ID"),
                				rs.getInt("SELLER_ID"),
                				rs.getInt("PRODUCT_ID"),
                				rs.getInt("PRICE"),
                				rs.getDate("SALE_DATE")));

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getBuyersTransaction()");
        }
        return list;
    }

    public boolean addTransaction(int buyerID, int sellerID, int productID, int price, Date saleDate) {
    	log.info(CLASSNAME + "Method addTransaction starts.....");
    	try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(ADD_TRANSACTION_QUERY);
        ) {
            preparedStatement.setInt(1, buyerID);
            preparedStatement.setInt(2, sellerID);
            preparedStatement.setInt(3, productID);
            preparedStatement.setInt(4, price);
            preparedStatement.setDate(5, new java.sql.Date(saleDate.getTime()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in addTransaction()");
        }
        return false;
    }

    public Transaction getTransaction(int transactionID) {
    	log.info(CLASSNAME + "Method getTransaction starts.....");
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_TRANSACTION_QUERY);
        ) {

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
            log.error(CLASSNAME + "SQLException in getTransaction()");
        }
        return null;
    }


    //------------------------------------------------------
    //--------------------XXX:PICTURES----------------------
    //------------------------------------------------------


    public List<String> getPicturesURLs(int productID) {
    	log.info(CLASSNAME + "Method getPicturesURLs starts.....");
        List<String> list = new ArrayList<String>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(GET_PICTURES_URL_QUERY);
        ) {

            preparedStatement.setInt(1, productID);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            while (rs.next())
                list.add(rs.getString("URL"));
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in getPicturesURLs()");
        }
        return list;
    }

    public boolean addPictures(int productID, String URL) {
    	log.info(CLASSNAME + "Method addPictures starts.....");
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(ADD_PICTURE_QUERY);
        ) {

            preparedStatement.setInt(1, productID);
            preparedStatement.setString(2, URL);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(CLASSNAME + "SQLException in addPictures()");
        }
        return false;
    }

}
