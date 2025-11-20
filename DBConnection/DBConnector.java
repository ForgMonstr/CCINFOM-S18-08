package DBConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    public static Connection getConnection() throws SQLException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("dev.properties"));
        String password = prop.getProperty("DB_PASS");
        String user = prop.getProperty("DB_USER");

        String URL = prop.getProperty("DB_URL");
        return (Connection) DriverManager.getConnection(URL, user, password);
    }
}
