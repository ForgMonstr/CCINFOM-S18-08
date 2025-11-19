package models.order;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactInfo;

    public Supplier() {}

    public Supplier(int supplierId, String supplierName, String contactInfo) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
    }

    // getters and setters
}
