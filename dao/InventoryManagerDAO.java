package dao;

import DBConnection.DBConnector;
import models.inventory.Inventory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManagerDAO {

    public void insertItem(Inventory i) {
        String sql = "INSERT INTO inventory (item_code, item_name, unit_of_measure, quantity_on_hand, reorder_level, supplier_id, expiration_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, i.getItemCode());
            st.setString(2, i.getItemName());
            st.setString(3, i.getUnitOfMeasure());
            st.setDouble(4, i.getQuantityOnHand());
            st.setDouble(5, i.getReorderLevel());
            st.setInt(6, i.getSupplierId());
            st.setDate(7, i.getExpirationDate());

            st.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Inventory getItemById(int id) {
        String sql = "SELECT * FROM inventory WHERE item_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Inventory(
                        rs.getInt("item_id"),
                        rs.getString("item_code"),
                        rs.getString("item_name"),
                        rs.getString("unit_of_measure"),
                        rs.getDouble("quantity_on_hand"),
                        rs.getDouble("reorder_level"),
                        rs.getInt("supplier_id"),
                        rs.getDate("expiration_date")
                );
            }

        } catch (SQLException e) { e.printStackTrace(); } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Inventory> getAllItems() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM inventory";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Inventory(
                        rs.getInt("item_id"),
                        rs.getString("item_code"),
                        rs.getString("item_name"),
                        rs.getString("unit_of_measure"),
                        rs.getDouble("quantity_on_hand"),
                        rs.getDouble("reorder_level"),
                        rs.getInt("supplier_id"),
                        rs.getDate("expiration_date")
                ));
            }

        } catch (SQLException e) { e.printStackTrace(); } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void updateItem(Inventory i) {
        String sql = "UPDATE inventory SET item_code=?, item_name=?, unit_of_measure=?, quantity_on_hand=?, reorder_level=?, supplier_id=?, expiration_date=? WHERE item_id=?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, i.getItemCode());
            stmt.setString(2, i.getItemName());
            stmt.setString(3, i.getUnitOfMeasure());
            stmt.setDouble(4, i.getQuantityOnHand());
            stmt.setDouble(5, i.getReorderLevel());
            stmt.setInt(6, i.getSupplierId());
            stmt.setDate(7, i.getExpirationDate());
            stmt.setInt(8, i.getItemId());

            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM inventory WHERE item_id=?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
