package models.order;

import java.sql.Date;

public class CustomerOrder {
    private int orderId;
    private Date orderDate;
    private int branchId;
    private double totalAmount;

    public CustomerOrder() {}

    public CustomerOrder(int orderId, Date orderDate, int branchId, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.branchId = branchId;
        this.totalAmount = totalAmount;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public int getBranchId() { return branchId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
