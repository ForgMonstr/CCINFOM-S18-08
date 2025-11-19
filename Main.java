import DBConnection.DBConnector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("dev.properties"));

        String password = prop.getProperty("DB_PASS");
        String user = prop.getProperty("DB_USER");

        String URL = prop.getProperty("DB_URL");
        System.out.println(URL);

        Connection conn = DBConnector.getConnection(URL, user, password);

    }
}
