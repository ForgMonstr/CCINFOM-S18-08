package models.inventory;

public class Product {
    private int productId;
    private String productCode;
    private String productName;
    private String category;
    private String description;
    private double price;
    private double cost;
    // getters/setters/constructors omitted for brevity (same as previous)

    public Product(int productId, String productCode, String productName,
                   String category, String description, double price, double cost) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.cost = cost;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}

