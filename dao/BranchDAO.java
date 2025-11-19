package dao;

import DBConnection.DBConnector;
import models.branch.Branch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BranchDAO {

    public void insertBranch(Branch b) {
        String sql = "INSERT INTO branch (branch_code, branch_name, address) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, b.getBranchCode());
            stmt.setString(2, b.getBranchName());
            stmt.setString(3, b.getAddress());
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Branch getBranchById(int id) {
        String sql = "SELECT * FROM branch WHERE branch_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("branch_code"),
                        rs.getString("branch_name"),
                        rs.getString("address")
                );
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Branch> getAllBranches() {
        List<Branch> list = new ArrayList<>();
        String sql = "SELECT * FROM branch";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("branch_code"),
                        rs.getString("branch_name"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void updateBranch(Branch b) {
        String sql = "UPDATE branch SET branch_code=?, branch_name=?, address=? WHERE branch_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, b.getBranchCode());
            stmt.setString(2, b.getBranchName());
            stmt.setString(3, b.getAddress());
            stmt.setInt(4, b.getBranchId());
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteBranch(int id) {
        String sql = "DELETE FROM branch WHERE branch_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
