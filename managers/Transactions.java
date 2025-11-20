package manager;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transactions {
    private final DataSource dataSource;

    public Transactions(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //1. SALES DATA IMPORT/UPDATE
    public static class DemandRecord {
        public final LocalDate date;
        public final String productId;
        public final int qty;
        public final double revenue; 

        public DemandRecord(LocalDate date, String productId, int qty, double revenue) {
            this.date = date;
            this.productId = productId;
            this.qty = qty;
            this.revenue = revenue;
        }
    }

    public static class SalesFrequencyRecord {
        public final String productId;
        public final int totalQuantitySold;
        public final long orderFrequency;

        public SalesFrequencyRecord(String productId, int totalQuantitySold, long orderFrequency) {
            this.productId = productId;
            this.totalQuantitySold = totalQuantitySold;
            this.orderFrequency = orderFrequency;
        }
    }  

    //2. DEMAND FORECASTING
    public static class AggregatedSalesRecord {
        public final String timePeriod; 
        public final String productId;
        public final double totalQty;
        public final double totalRevenue;

        public AggregatedSalesRecord(String timePeriod, String productId, double totalQty, double totalRevenue) {
            this.timePeriod = timePeriod;
            this.productId = productId;
            this.totalQty = totalQty;
            this.totalRevenue = totalRevenue;
        }
    }

    public static class InventoryUtilizationRecord {
        public final String itemId;
        public final double totalQuantityUsed;
        public final long productSalesCount;

        public InventoryUtilizationRecord(String itemId, double totalQuantityUsed, long productSalesCount) {
            this.itemId = itemId;
            this.totalQuantityUsed = totalQuantityUsed;
            this.productSalesCount = productSalesCount;
        }
    }

    //3. BRANCH PERFORMANCE COMPARISON
    public static class OrderItem {
        public final int productId;
        public final int quantity;
        
        public OrderItem(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
    }

    public static class OrderResult {
        public final int orderId;
        public final double totalAmount;
        
        public OrderResult(int orderId, double totalAmount) {
            this.orderId = orderId;
            this.totalAmount = totalAmount;
        }
    }

    //4 . INVENTORY PLANNING SIMULATION
    public static class InventoryUtilizationRecord {
        public final String itemId;
        public final double totalQuantityUsed;
        public final long productSalesCount;

        public InventoryUtilizationRecord(String itemId, double totalQuantityUsed, long productSalesCount) {
            this.itemId = itemId;
            this.totalQuantityUsed = totalQuantityUsed;
            this.productSalesCount = productSalesCount;
        }
    }

// ====================================================================================================================================================== //

    //1. SALES DATA IMPORT / UPDATE
    public List<DemandRecord> getRawCustomerOrders(java.sql.Date startDate, java.sql.Date endDate) throws SQLException {
        String sql = "SELECT co.order_date, od.order_id, od.product_id, od.qty, od.subtotal " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE co.order_date BETWEEN ? AND ? " +
                     "ORDER BY co.order_date, od.product_id";

        List<DemandRecord> records = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, startDate);
            ps.setDate(2, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(new DemandRecord(
                        rs.getDate("order_date").toLocalDate(),
                        rs.getString("product_id"),
                        rs.getInt("qty"),
                        rs.getDouble("subtotal") 
                    ));
                }
            }
        }
        return records;
    }

    public List<SalesFrequencyRecord> calculateSales(java.sql.Date startDate, java.sql.Date endDate) throws SQLException {
        
        String sql = "SELECT od.product_id, SUM(od.qty) AS total_qty, COUNT(DISTINCT co.order_id) AS order_count " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE co.order_date BETWEEN ? AND ? " +
                     "GROUP BY od.product_id " +
                     "ORDER BY order_count DESC, total_qty DESC";

        List<SalesFrequencyRecord> frequencyRecords = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, startDate);
            ps.setDate(2, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    frequencyRecords.add(new SalesFrequencyRecord(
                        rs.getString("product_id"),
                        rs.getInt("total_qty"),
                        rs.getLong("order_count")
                    ));
                }
            }
        }
        return frequencyRecords;
    }

    //2. GENERATE DEMAND FORECAST
    public List<AggregatedSalesRecord> getMonthlySales(int year) throws SQLException {
        String sql = "SELECT DATE_FORMAT(co.order_date, '%Y-%m') AS time_period, " +
                     "od.product_id, SUM(od.qty) AS total_qty, SUM(od.subtotal) AS total_revenue " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE YEAR(co.order_date) = ? " +
                     "GROUP BY time_period, od.product_id " +
                     "ORDER BY time_period, od.product_id";

        List<AggregatedSalesRecord> records = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(new AggregatedSalesRecord(
                        rs.getString("time_period"),
                        rs.getString("product_id"),
                        rs.getDouble("total_qty"),
                        rs.getDouble("total_revenue")
                    ));
                }
            }
        }
        return records;
    }

    public List<AggregatedSalesRecord> getQuarterlySales(int year) throws SQLException {
        String sql = "SELECT CONCAT(YEAR(co.order_date), '-Q', QUARTER(co.order_date)) AS time_period, " +
                     "od.product_id, SUM(od.qty) AS total_qty, SUM(od.subtotal) AS total_revenue " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE YEAR(co.order_date) = ? " +
                     "GROUP BY time_period, od.product_id " +
                     "ORDER BY time_period, od.product_id";

        List<AggregatedSalesRecord> records = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(new AggregatedSalesRecord(
                        rs.getString("time_period"),
                        rs.getString("product_id"),
                        rs.getDouble("total_qty"),
                        rs.getDouble("total_revenue")
                    ));
                }
            }
        }
        return records;
    }

    public List<AggregatedSalesRecord> getAnnualSales() throws SQLException {
        String sql = "SELECT YEAR(co.order_date) AS sales_year, " +
                     "od.product_id, SUM(od.qty) AS total_qty, SUM(od.subtotal) AS total_revenue " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "GROUP BY sales_year, od.product_id " +
                     "ORDER BY sales_year, od.product_id";

        List<AggregatedSalesRecord> records = new ArrayList<>();

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(new AggregatedSalesRecord(
                        String.valueOf(rs.getInt("sales_year")),
                        rs.getString("product_id"),
                        rs.getDouble("total_qty"),
                        rs.getDouble("total_revenue")
                    ));
                }
            }
        }
        return records;
    }

    //3. BRANCH PERFORMANCE COMPARISON
    public OrderResult processBranchSale(int branchId, int customerId, int salesRepId, List<OrderItem> items){
        Connection connection = null;
        double overallTotal = 0.0;
        int orderId = -1;


        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            String insertOrderSql = "INSERT INTO customerorder (branch_id, order_date, total_amount, customer_id, sales_rep_id) " +
                                    "VALUES (?, ?, 0.0, ?, ?)";
            
            try (PreparedStatement psOrder = connection.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setInt(1, branchId);
                psOrder.setDate(2, Date.valueOf(LocalDate.now())); 
                psOrder.setInt(3, customerId);
                psOrder.setInt(4, salesRepId); 
                psOrder.executeUpdate();

                try (ResultSet rs = psOrder.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to create customerorder, no ID obtained.");
                    }
                }
            }


            String getProductInfoSql = "SELECT s.quantity_on_hand, p.price " +
                                       "FROM supplies s JOIN product p ON s.product_id = p.product_id " +
                                       "WHERE s.product_id = ?";
            String insertDetailSql = "INSERT INTO orderdetails (order_id, product_id, qty, subtotal) VALUES (?, ?, ?, ?)";
        
            String updateSuppliesSql = "UPDATE supplies SET quantity_on_hand = quantity_on_hand - ? WHERE product_id = ?";
            
            for (OrderItem item : items) {
                double unitPrice;
                int currentStock;

                try (PreparedStatement psInfo = connection.prepareStatement(getProductInfoSql)) {
                    psInfo.setString(1, item.productId);
                    try (ResultSet rs = psInfo.executeQuery()) {
                        if (rs.next()) {
                            currentStock = rs.getInt("quantity_on_hand");
                            unitPrice = rs.getDouble("price");
                            
                            if (currentStock < item.quantity) {
                                throw new SQLException("Insufficient stock for product " + item.productId + ". Available: " + currentStock);
                            }
                        } else {
                            throw new SQLException("Product ID " + item.productId + " not found in products/supplies.");
                        }
                    }
                }
                
                double subtotal = unitPrice * item.quantity;
                overallTotal += subtotal;


                try (PreparedStatement psDetail = connection.prepareStatement(insertDetailSql)) {
                    psDetail.setInt(1, orderId);
                    psDetail.setString(2, item.productId);
                    psDetail.setInt(3, item.quantity);
                    psDetail.setDouble(4, subtotal);
                    psDetail.executeUpdate();
                }


                try (PreparedStatement psSupply = connection.prepareStatement(updateSuppliesSql)) {
                    psSupply.setInt(1, item.quantity); 
                    psSupply.setString(2, item.productId);
                    psSupply.executeUpdate();
                }
            }


            String updateTotalSql = "UPDATE customerorder SET total_amount = ? WHERE order_id = ?";
            try (PreparedStatement psTotal = connection.prepareStatement(updateTotalSql)) {
                psTotal.setDouble(1, overallTotal);
                psTotal.setInt(2, orderId);
                psTotal.executeUpdate();
            }

            connection.commit(); 
            return new OrderResult(orderId, overallTotal);

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    System.err.println("Transaction failed. Attempting rollback.");
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            throw new SQLException("Branch Sale transaction failed: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    //4. INVENTORY PLANNING SIMULATION
    public List<String> recordFrequentProducts(List<SalesFrequencyRecord> frequencyRecords, int topN) {
        if (frequencyRecords == null || frequencyRecords.isEmpty() || topN <= 0) {
            return new ArrayList<>();
        }

        List<String> topProducts = new ArrayList<>();
        int count = 0;
        for (SalesFrequencyRecord record : frequencyRecords) {
            if (count >= topN) {
                break;
            }
            topProducts.add(record.productId);
            count++;
        }
        return topProducts;
    }

    public List<InventoryUtilizationRecord> updateInventoryUtilization(java.sql.Date startDate, java.sql.Date endDate) {
        String sql = "SELECT " +
                     "inv.item_id, " +
                     "SUM(od.qty * pi.quantity_used) AS total_consumption, " +
                     "COUNT(DISTINCT od.order_id) AS sales_frequency " + 
                     "FROM customerorder co " +
                     "JOIN orderdetails od ON co.order_id = od.order_id " +
                     "JOIN productingredients pi ON od.product_id = pi.product_id " +
                     "JOIN inventory inv ON pi.item_id = inv.item_id " +
                     "WHERE co.order_date BETWEEN ? AND ? " +
                     "GROUP BY inv.item_id " +
                     "ORDER BY total_consumption DESC";

        List<InventoryUtilizationRecord> utilizationRecords = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String itemId = rs.getString("item_id");
                        double consumption = rs.getDouble("total_consumption");
                        long frequency = rs.getLong("sales_frequency");

                        utilizationRecords.add(new InventoryUtilizationRecord(
                            itemId, 
                            consumption, 
                            frequency
                        ));
                        
                        updateInventoryStock(connection, itemId, consumption);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating inventory utilization: " + e.getMessage(), e);
        } finally {
            if (connection != null) connection.close();
        }
        return utilizationRecords;
    }

    private void updateInventoryStock(Connection conn, String itemId, double consumption) throws SQLException {

        String updateSql = "UPDATE inventory SET quantity_on_hand = quantity_on_hand - ? WHERE item_id = ?";
        
        try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
            psUpdate.setDouble(1, consumption);
            psUpdate.setString(2, itemId);
            psUpdate.executeUpdate();
        }
    }


}