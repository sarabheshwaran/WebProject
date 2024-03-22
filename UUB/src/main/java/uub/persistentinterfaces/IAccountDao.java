package uub.persistentinterfaces;

import java.util.List;
import java.util.Map;

import uub.enums.AccountStatus;
import uub.model.Account;
import uub.staticlayer.CustomBankException;

public interface IAccountDao {

	void addAccounts(List<Account> accounts) throws CustomBankException;

	int updateAccount(Account account) throws CustomBankException;

	List<Account> getAccount(int accNo) throws CustomBankException;

	Map<Integer, Map<Integer,Account>> getBranchAccounts(int branchId, AccountStatus status, int limit, int offSet) throws CustomBankException;

	Map<Integer, Account> getUserAccounts(int userId) throws CustomBankException;
}