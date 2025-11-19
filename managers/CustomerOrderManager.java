package managers;

public class CustomerOrderManager {
    private final CustomerOrderDAO orderDAO;

    public CustomerOrderManager() {
        this.orderDAO = new CustomerOrderDAO();
    }

    public void createOrder(CustomerOrder order) {
        orderDAO.insertOrder(order);
    }

    public CustomerOrder getOrder(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public List<CustomerOrder> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public void updateOrder(CustomerOrder order) {
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int orderId) {
        orderDAO.deleteOrder(orderId);
    }
}

