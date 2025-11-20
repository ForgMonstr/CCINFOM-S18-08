package managers;

import java.sql.*;
import java.util.*;

public class ForecastManager {

    public Map<Integer, Double> computeIngredientUsageLast30Days(Connection conn) throws SQLException {

        Map<Integer, Integer> productSales = new HashMap<>();
        Map<Integer, Double> ingredientUsage = new HashMap<>();

        String salesSql = """
            SELECT od.product_id, SUM(od.quantity) AS qty_sold
            FROM orderdetails od
            JOIN customerorder co ON od.order_id = co.order_id
            WHERE co.order_date >= CURRENT_DATE - INTERVAL 30 DAY
            GROUP BY od.product_id
        """;

        try (PreparedStatement ps = conn.prepareStatement(salesSql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productSales.put(rs.getInt("product_id"), rs.getInt("qty_sold"));
            }
        }

        String ingredientSql = """
            SELECT item_id, quantity_used
            FROM productingredients
            WHERE product_id = ?
        """;

        for (var entry : productSales.entrySet()) {
            int productId = entry.getKey();
            int qtySold = entry.getValue();

            try (PreparedStatement ps = conn.prepareStatement(ingredientSql)) {
                ps.setInt(1, productId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int itemId = rs.getInt("item_id");
                        double qtyUsed = rs.getDouble("quantity_used");

                        ingredientUsage.merge(itemId, qtySold * qtyUsed, Double::sum);
                    }
                }
            }
        }

        return ingredientUsage;
    }

    public Map<Integer, Double> forecastNext30Days(Map<Integer, Double> usageLast30) {
        Map<Integer, Double> forecast = new HashMap<>();

        for (var entry : usageLast30.entrySet()) {
            int itemId = entry.getKey();
            double last30Usage = entry.getValue();

            double avgDaily = last30Usage / 30.0;
            double next30 = avgDaily * 30;

            forecast.put(itemId, Math.round(next30 * 100.0) / 100.0);
        }

        return forecast;
    }

    public Map<Integer, Double> computeReorderNeeds(Connection conn, int branchId, Map<Integer, Double> forecast)
            throws SQLException {

        Map<Integer, Double> needs = new HashMap<>();

        String sql = """
            SELECT item_id, quantity_on_hand
            FROM supplies
            WHERE branch_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, branchId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    int itemId = rs.getInt("item_id");
                    double stock = rs.getDouble("quantity_on_hand");

                    double predicted = forecast.getOrDefault(itemId, 0.0);

                    double deficit = predicted - stock;

                    if (deficit > 0) {
                        needs.put(itemId, Math.round(deficit * 100.0) / 100.0);
                    }
                }
            }
        }

        return needs;
    }

    public Map<Integer, Double> forecastReorderForBranch(Connection conn, int branchId) throws SQLException {

        Map<Integer, Double> last30 = computeIngredientUsageLast30Days(conn);
        Map<Integer, Double> forecast = forecastNext30Days(last30);
        return computeReorderNeeds(conn, branchId, forecast);
    }
}

