package models.inventory;

import java.sql.Date;

public class Inventory {
    private int itemId;
    private String itemCode;
    private String itemName;
    private String unitOfMeasure;
    private double quantityOnHand;
    private double reorderLevel;
    private int supplierId;
    private Date expirationDate;

    public Inventory() {}

    public Inventory(int itemId, String itemCode, String itemName,
                     String unitOfMeasure, double quantityOnHand,
                     double reorderLevel, int supplierId, Date expirationDate) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unitOfMeasure = unitOfMeasure;
        this.quantityOnHand = quantityOnHand;
        this.reorderLevel = reorderLevel;
        this.supplierId = supplierId;
        this.expirationDate = expirationDate;
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }

    public double getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(double quantityOnHand) { this.quantityOnHand = quantityOnHand; }

    public double getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(double reorderLevel) { this.reorderLevel = reorderLevel; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public Date getExpirationDate() { return expirationDate; }
    public void setExpirationDate(Date expirationDate) { this.expirationDate = expirationDate; }
}
