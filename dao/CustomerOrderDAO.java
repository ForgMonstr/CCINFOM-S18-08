package dao;

import DBConnection.DBConnector;
import models.order.CustomerOrder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderDAO {

    public void insertOrder(CustomerOrder o) {
        String sql = "INSERT INTO customerorder (order_date, branch_id, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, o.getOrderDate());
            stmt.setInt(2, o.getBranchId());
            stmt.setDouble(3, o.getTotalAmount());
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public CustomerOrder getOrderById(int id) {
        String sql = "SELECT * FROM customerorder WHERE order_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CustomerOrder(
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getInt("branch_id"),
                        rs.getDouble("total_amount")
                );
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<CustomerOrder> getAllOrders() {
        List<CustomerOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM customerorder";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new CustomerOrder(
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getInt("branch_id"),
                        rs.getDouble("total_amount")
                ));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void updateOrder(CustomerOrder o) {
        String sql = "UPDATE customerorder SET order_date=?, branch_id=?, total_amount=? WHERE order_id=?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, o.getOrderDate());
            stmt.setInt(2, o.getBranchId());
            stmt.setDouble(3, o.getTotalAmount());
            stmt.setInt(4, o.getOrderId());
            stmt.executeUpdate();

        } catch (SQLException | IOException e) { e.printStackTrace(); }
    }

    public void deleteOrder(int id) {
        String sql = "DELETE FROM customerorder WHERE order_id=?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }
}

