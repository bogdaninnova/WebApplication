package ua.sumdu.group73.model;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Locale;

/**
 * This class singleton for connection to database.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class ConnectionDB {
    private static final Logger log = Logger.getLogger(ConnectionDB.class);
    private static Connection instance;
    private ConnectionDB() {}

    /**
     * This method returned Connection or create his.
     *
     * @return Connection.
     */
    public static Connection getInstance() {
        Locale.setDefault(Locale.ENGLISH);
        if (instance != null) {
            return instance;
        } else {
            Hashtable ht = new Hashtable();
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            ht.put(Context.PROVIDER_URL, "t3://localhost:7701");
            try {
                Context ctx = new InitialContext(ht);
                DataSource dataSource = (DataSource) ctx.lookup("auction");
                instance = dataSource.getConnection();
            } catch (NamingException e) {
                log.error(e);
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return instance;
    }
}