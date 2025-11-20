package models.branch;

public class Branch {
    private int branchId;
    private String branchCode;
    private String branchName;
    private String address;

    public Branch() {}

    public Branch(int branchId, String branchCode, String branchName, String address) {
        this.branchId = branchId;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.address = address;
    }

    public int getBranchId() { return branchId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}

