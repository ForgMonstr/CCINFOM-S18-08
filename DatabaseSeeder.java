import DBConnection.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


// PURELY AI FOR SEEDING THE DATABASE AND SHIT


public class DatabaseSeeder {

    private static final int BRANCH_COUNT = 5;
    private static final int SUPPLIER_COUNT = 20;
    private static final int INVENTORY_COUNT = 100;
    private static final int PRODUCT_COUNT = 40;
    private static final int ORDER_COUNT = 500;
    private static final int PURCHASE_ORDER_COUNT = 100;

    private final Random rnd = new Random();

    private final Map<Integer, Integer> branches = new HashMap<>();
    private final Map<Integer, Integer> suppliers = new HashMap<>();
    private final Map<Integer, Integer> items = new HashMap<>();
    private final Map<Integer, Integer> products = new HashMap<>();

    public void run() {
        try (Connection conn = DBConnector.getConnection()) {

            conn.setAutoCommit(false);

            try {
                seedBranches(conn);
                seedSuppliers(conn);
                seedInventory(conn);
                seedProducts(conn);
                seedProductIngredients(conn);
                seedSupplies(conn);
                seedPurchaseOrders(conn);
                seedCustomerOrders(conn);

                conn.commit();
                System.out.println("✔ Database seeding completed.");

            } catch (Exception ex) {
                conn.rollback();
                System.err.println("❌ Seeder failed. Rolling back.");
                ex.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer insert(Connection conn, String sql, Object... params) throws SQLException {

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++)
            ps.setObject(i + 1, params[i]);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // return ID if exists
        }

        return null; // <-- SAFE for junction tables
    }



    // -------------------------------------------
    // BRANCH
    // -------------------------------------------
    private void seedBranches(Connection conn) throws SQLException {
        String sql = "INSERT INTO branch (branch_code, branch_name, address) VALUES (?, ?, ?)";

        for (int i = 0; i < BRANCH_COUNT; i++) {
            String code = "B" + String.format("%03d", i + 1);
            String name = "Branch " + (i + 1);
            String address = "Street " + (100 + i);

            int id = insert(conn, sql, code, name, address);
            branches.put(i, id);
        }
        System.out.println("Branches: " + branches.size());
    }

    // -------------------------------------------
    // SUPPLIERS
    // -------------------------------------------
    private void seedSuppliers(Connection conn) throws SQLException {
        String sql = "INSERT INTO supplier (supplier_name, contact_info) VALUES (?, ?)";

        for (int i = 0; i < SUPPLIER_COUNT; i++) {
            String name = "Supplier " + (i + 1);
            String info = "Contact: +639" + (900000000 + rnd.nextInt(999999));

            int id = insert(conn, sql, name, info);
            suppliers.put(i, id);
        }
        System.out.println("Suppliers: " + suppliers.size());
    }

    // -------------------------------------------
    // INVENTORY ITEMS
    // -------------------------------------------
    private void seedInventory(Connection conn) throws SQLException {
        String sql = """
                INSERT INTO inventory
                (item_code, item_name, unit_of_measure, quantity_on_hand,
                 reorder_level, supplier_id, expiration_date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        String[] names = {"Beef Patty", "Cheese", "Bun", "Chicken", "Fries", "Lettuce",
                "Tomato", "Onion", "Ketchup", "Mayo", "Oil", "Rice", "Spice Mix",
                "Coffee Beans", "Cup", "Sauce", "Wrapper", "Ice", "Sugar", "Salt"};

        String[] uoms = {"pcs", "kg", "g", "ml", "ltr", "pack"};

        for (int i = 0; i < INVENTORY_COUNT; i++) {

            String code = "I" + String.format("%04d", i + 1);
            String name = names[rnd.nextInt(names.length)] + " " + (rnd.nextInt(5) + 1);
            String uom = uoms[rnd.nextInt(uoms.length)];
            double qty = 10 + rnd.nextInt(190);
            double reorder = 5 + rnd.nextInt(50);
            int supplierId = suppliers.get(rnd.nextInt(SUPPLIER_COUNT));

            LocalDate exp = LocalDate.now().plusDays(rnd.nextInt(365));
            Date expDate = Date.valueOf(exp);

            int id = insert(conn, sql, code, name, uom, qty, reorder, supplierId, expDate);
            items.put(i, id);
        }

        System.out.println("Inventory items: " + items.size());
    }

    // -------------------------------------------
    // PRODUCTS
    // -------------------------------------------
    private void seedProducts(Connection conn) throws SQLException {
        String sql = """
                INSERT INTO product
                (product_code, product_name, category, description, price, cost)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String[] names = {"Cheeseburger", "Burger Meal", "Chicken Meal", "Fries",
                "Coffee", "Iced Tea", "Chicken Sandwich", "Rice Meal"};

        String[] categories = {"Burger", "Chicken", "Snack", "Drink", "Meal"};

        for (int i = 0; i < PRODUCT_COUNT; i++) {

            String code = "P" + String.format("%03d", i + 1);
            String name = names[rnd.nextInt(names.length)] + " " + (rnd.nextInt(4) + 1);
            String category = categories[rnd.nextInt(categories.length)];
            String desc = name + " desc.";

            double price = 50 + rnd.nextDouble() * 300;
            double cost = price * (0.3 + rnd.nextDouble() * 0.3);

            price = Math.round(price * 100) / 100.0;
            cost = Math.round(cost * 100) / 100.0;

            int id = insert(conn, sql, code, name, category, desc, price, cost);
            products.put(i, id);
        }

        System.out.println("Products: " + products.size());
    }

    // -------------------------------------------
    // PRODUCT INGREDIENTS
    // -------------------------------------------
    private void seedProductIngredients(Connection conn) throws SQLException {
        String sql = """
                INSERT INTO productingredients (product_id, item_id, quantity_used)
                VALUES (?, ?, ?)
                """;

        for (int i = 0; i < PRODUCT_COUNT; i++) {

            int productId = products.get(i);

            int ingredientCount = 3 + rnd.nextInt(3);
            Set<Integer> used = new HashSet<>();

            while (used.size() < ingredientCount) {

                int randomItemIndex = rnd.nextInt(INVENTORY_COUNT);
                if (used.contains(randomItemIndex)) continue;

                used.add(randomItemIndex);

                int itemId = items.get(randomItemIndex);
                double qty = Math.round((0.1 + rnd.nextDouble() * 2.0) * 100) / 100.0;

                insert(conn, sql, productId, itemId, qty);
            }
        }

        System.out.println("Product Ingredients Added");
    }

    // -------------------------------------------
    // SUPPLIES
    // -------------------------------------------
    private void seedSupplies(Connection conn) throws SQLException {
        String sql = """
                INSERT INTO supplies
                (item_id, branch_id, quantity_on_hand, unit_of_measure, reorder_level)
                VALUES (?, ?, ?, ?, ?)
                """;

        String[] uoms = {"pcs", "kg", "g", "ml", "ltr"};

        for (int b = 0; b < BRANCH_COUNT; b++) {
            int branchId = branches.get(b);

            for (int i = 0; i < 40; i++) {
                int itemId = items.get(rnd.nextInt(INVENTORY_COUNT));

                double qty = 10 + rnd.nextInt(150);
                double reorder = 5 + rnd.nextInt(25);

                insert(conn, sql, itemId, branchId, qty, uoms[rnd.nextInt(uoms.length)], reorder);
            }
        }

        System.out.println("Supplies added");
    }

    // -------------------------------------------
    // PURCHASE ORDERS
    // -------------------------------------------
    private void seedPurchaseOrders(Connection conn) throws SQLException {
        String sql = """
                INSERT INTO purchaseorder
                (supplier_id, item_id, quantity, cost, order_date)
                VALUES (?, ?, ?, ?, ?)
                """;

        for (int i = 0; i < PURCHASE_ORDER_COUNT; i++) {
            int supplierId = suppliers.get(rnd.nextInt(SUPPLIER_COUNT));
            int itemId = items.get(rnd.nextInt(INVENTORY_COUNT));

            double qty = 10 + rnd.nextInt(100);
            double cost = Math.round((5 + rnd.nextDouble() * 100) * 100) / 100.0;

            LocalDate d = LocalDate.now().minusDays(rnd.nextInt(90));

            insert(conn, sql, supplierId, itemId, qty, cost, Date.valueOf(d));
        }

        System.out.println("Purchase Orders Added");
    }

    // -------------------------------------------
    // CUSTOMER ORDERS + ORDER DETAILS
    // -------------------------------------------
    private void seedCustomerOrders(Connection conn) throws SQLException {

        String orderSql = """
                INSERT INTO customerorder (order_date, branch_id, total_amount)
                VALUES (?, ?, ?)
                """;

        String detailSql = """
                INSERT INTO orderdetails (order_id, product_id, quantity, subtotal)
                VALUES (?, ?, ?, ?)
                """;

        for (int i = 0; i < ORDER_COUNT; i++) {

            LocalDate d = LocalDate.now().minusDays(rnd.nextInt(90));
            int branchId = branches.get(rnd.nextInt(BRANCH_COUNT));

            int orderId = insert(conn, orderSql, Date.valueOf(d), branchId, 0.0);

            double total = 0;

            int lines = 1 + rnd.nextInt(4);

            for (int j = 0; j < lines; j++) {

                int productId = products.get(rnd.nextInt(PRODUCT_COUNT));
                double price = fetchProductPrice(conn, productId);

                int qty = 1 + rnd.nextInt(3);
                double subtotal = Math.round(price * qty * 100) / 100.0;

                total += subtotal;

                insert(conn, detailSql, orderId, productId, qty, subtotal);
            }

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE customerorder SET total_amount = ? WHERE order_id = ?");

            ps.setDouble(1, total);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }

        System.out.println("Orders Added");
    }

    private double fetchProductPrice(Connection conn, int productId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT price FROM product WHERE product_id = ?");
        ps.setInt(1, productId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getDouble("price");
        return 0;
    }
}
