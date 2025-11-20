package managers;

import dao.InventoryManagerDAO;
import models.inventory.Inventory;

import java.util.List;

public class InventoryManager {
    private final InventoryManagerDAO inventoryDAO;

    public InventoryManager() {
        this.inventoryDAO = new InventoryManagerDAO();
    }

    public void createItem(Inventory item) {
        inventoryDAO.insertItem(item);
    }

    public Inventory getItem(int itemId) {
        return inventoryDAO.getItemById(itemId);
    }

    public List<Inventory> getAllItems() {
        return inventoryDAO.getAllItems();
    }

    public void updateItem(Inventory item) {
        inventoryDAO.updateItem(item);
    }

    public void deleteItem(int itemId) {
        inventoryDAO.deleteItem(itemId);
    }
}
