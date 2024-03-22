package uub.persistentinterfaces;

import java.util.List;
import java.util.Map;

import uub.model.Branch;
import uub.staticlayer.CustomBankException;

public interface IBranchDao {

	public Map<Integer, Branch> getBranches() throws CustomBankException;

	public void addBranch(List<Branch> branches) throws CustomBankException;

	void updateBranches(Branch branch) throws CustomBankException;

	List<Branch> getBranch(int id) throws CustomBankException;

}
