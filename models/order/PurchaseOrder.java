package models.order;

import java.sql.Date;

public class PurchaseOrder {
    private int poId;
    private int supplierId;
    private int itemId;
    private double quantity;
    private double cost;
    private Date orderDate;

    public PurchaseOrder() {}

    public PurchaseOrder(int poId, int supplierId, int itemId, double quantity,
                         double cost, Date orderDate) {
        this.poId = poId;
        this.supplierId = supplierId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.cost = cost;
        this.orderDate = orderDate;
    }

    // getters and setters below

    public int getPoId() { return poId; }
    public void setPoId(int poId) { this.poId = poId; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
