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
}
