package models.inventory;

public class Supplies {
    private int supplyId;
    private int itemId;
    private int branchId;
    private double quantityOnHand;
    private String unitOfMeasure;
    private double reorderLevel;

    // constructors + getters + setters

     public Supplies(int supplyId, int itemId, int branchId, double quantityOnHand,
                    String unitOfMeasure, double reorderLevel) {
        this.supplyId = supplyId;
        this.itemId = itemId;
        this.branchId = branchId;
        this.quantityOnHand = quantityOnHand;
        this.unitOfMeasure = unitOfMeasure;
        this.reorderLevel = reorderLevel;
    }

    public int getSupplyId() { return supplyId; }
    public void setSupplyId(int supplyId) { this.supplyId = supplyId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getBranchId() { return branchId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }

    public double getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(double quantityOnHand) { this.quantityOnHand = quantityOnHand; }

    public String getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }

    public double getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(double reorderLevel) { this.reorderLevel = reorderLevel; }
}


