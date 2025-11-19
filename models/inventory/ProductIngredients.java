package models.inventory;

public class ProductIngredients {
    private int productId;
    private int itemId;
    private double quantityUsed;

    public ProductIngredients() {}

    public ProductIngredients(int productId, int itemId, double quantityUsed) {
        this.productId = productId;
        this.itemId = itemId;
        this.quantityUsed = quantityUsed;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public double getQuantityUsed() { return quantityUsed; }
    public void setQuantityUsed(double quantityUsed) { this.quantityUsed = quantityUsed; }
}

