package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection(String con, String user, String password) throws SQLException {
        return (Connection) DriverManager.getConnection(con, user, password);
    }
}
