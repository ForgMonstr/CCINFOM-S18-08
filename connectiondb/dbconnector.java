package connectiondb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbconnector {

    public dbconnector (String con, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(con, user, password);
    }
}
