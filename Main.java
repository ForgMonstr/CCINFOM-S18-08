import DBConnection.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Program logic here

        String password = "r@R3R@R3";
        String user = "user";

        String URL = "jdbc:mysql://localhost:3306/restaurant_forecast";
        System.out.println(URL);

        Connection conn = DBConnector.getConnection(URL, user, password);

    }
}
