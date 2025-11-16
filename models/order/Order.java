package models.order;

import java.sql.Date; // use java.sql.Date if working with JDBC

public class Order {
    private int orderId;
    private Date orderDate;
    private int branchId;
    private double totalAmount;

    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(int orderId, Date orderDate, int branchId, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.branchId = branchId;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", branchId=" + branchId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

