import DBConnection.DBConnector;
import managers.BranchManager;
import managers.ForecastManager;
import models.branch.Branch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = DBConnector.getConnection()) {

            ForecastManager fm = new ForecastManager();

            int branchId = 3;

            System.out.println("Forecasting ingredient needs for Branch " + branchId + "...\n");

            Map<Integer, Double> needed = fm.forecastReorderForBranch(conn, branchId);

            if (needed.isEmpty()) {
                System.out.println("No shortages predicted.");
            } else {
                for (var entry : needed.entrySet()) {
                    int itemId = entry.getKey();
                    double amountNeeded = entry.getValue();

                    System.out.println("Item ID " + itemId + " needs: " + amountNeeded);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

