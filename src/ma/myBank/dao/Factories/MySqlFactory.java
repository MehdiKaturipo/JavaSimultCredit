package ma.myBank.dao.Factories;

import ma.myBank.dao.daoException.DAOConfigException;
import ma.myBank.dao.IDao;
import ma.myBank.dao.daoMySQL.CréditDao;
import ma.myBank.modéle.Crédit;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlFactory extends Factory {

    private static final String PROPERTIES_FILE = "ma/myBank/dao/dao.properties",
            URL = "SDB_URL",
            DB = "DBNAME",
            LOGIN = "SDB_LOGIN",
            PASS = "SDB_PASS",
            DRIVER = "SDB_DRIVER";

    public static MySqlFactory INSTANCE = getInstance();
    private static Connection connection;
    private String url, login, pass;

    private MySqlFactory(String url, String login, String pass) throws SQLException {
        this.url = url;
        this.login = login;
        this.pass = pass;
        connection = DriverManager.getConnection(url, login, pass);
    }

    public static MySqlFactory getInstance() {
        MySqlFactory instance = null;
        String property_URL, property_LOGIN, property_PASS, property_DRIVER, property_DBNAME;
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fileProperties = classLoader.getResourceAsStream(PROPERTIES_FILE);
        if (fileProperties == null)
            throw new DAOConfigException("file " + PROPERTIES_FILE + " not found!");
        else {
            try {
                properties.load(fileProperties);
                property_URL = properties.getProperty(URL);
                property_LOGIN = properties.getProperty(LOGIN);
                property_PASS = properties.getProperty(PASS);
                property_DRIVER = properties.getProperty(DRIVER);
                property_DBNAME = properties.getProperty(DB);
                fileProperties.close();
                Class.forName(property_DRIVER);
                property_URL = property_URL + "/" + property_DBNAME;
                instance = new MySqlFactory(property_URL, property_LOGIN, property_PASS);
                System.out.println("MYSQL FACTORY INSTANCE FOR DATABASE : [" + property_DBNAME + "] READY TO CONSUME");
            } catch (IOException e) {
                throw new DAOConfigException("Error while loading properties file " + PROPERTIES_FILE, e);
            } catch (ClassNotFoundException e) {
                throw new DAOConfigException("Error while loading driver " + e.getMessage());
            } catch (SQLException e) {
                throw new DAOConfigException("Error while connecting to database " + e.getMessage());
            } finally {
                properties.clear();
            }
        }
        return instance;
    }

    public static Connection getConnection() {
        if (connection == null) INSTANCE = getInstance();
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) connection.close();
                System.out.println("MYSQL FACTORY INSTANCE FOR DATABASE : [" + INSTANCE.url + "] CLOSED");
            } catch (SQLException e) {
                throw new DAOConfigException(e.getCause() + " : " + e.getMessage());
            }
        }
    }

    @Override
    public IDao<Crédit, Long> getCréditDao() {
        return new CréditDao(getConnection());
    }

}