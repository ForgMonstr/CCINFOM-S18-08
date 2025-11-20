package managers;

import dao.BranchDAO;
import models.branch.Branch;

import java.util.List;

public class BranchManager {
    private final BranchDAO branchDAO;

    public BranchManager() {
        this.branchDAO = new BranchDAO();
    }

    public void addBranch(Branch b) {
        branchDAO.insertBranch(b);
    }

    public Branch getBranch(int branchId) {
        return branchDAO.getBranchById(branchId);
    }

    public List<Branch> getAllBranches() {
        return branchDAO.getAllBranches();
    }

    public void updateBranch(Branch b) {
        branchDAO.updateBranch(b);
    }

    public void deleteBranch(int branchId) {
        branchDAO.deleteBranch(branchId);
    }

}

