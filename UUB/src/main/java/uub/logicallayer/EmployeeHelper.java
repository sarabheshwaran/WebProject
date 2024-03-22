package uub.logicallayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import uub.enums.AccountStatus;
import uub.enums.Exceptions;
import uub.enums.UserStatus;
import uub.model.Account;
import uub.model.Branch;
import uub.model.Customer;
import uub.model.Employee;
import uub.persistentinterfaces.IBranchDao;
import uub.persistentinterfaces.IEmployeeDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.EmployeeUtils;
import uub.staticlayer.HashEncoder;
import uub.staticlayer.HelperUtils;

public class EmployeeHelper extends CustomerHelper {

	protected IEmployeeDao employeeDao;
	private IBranchDao branchDao;

	public EmployeeHelper() throws CustomBankException {

		try {
			Class<?> EmployeeDao = Class.forName("uub.persistentlayer.EmployeeDao");
			Constructor<?> empDao = EmployeeDao.getDeclaredConstructor();

			Class<?> BranchDao = Class.forName("uub.persistentlayer.BranchDao");
			Constructor<?> branDao = BranchDao.getDeclaredConstructor();

			employeeDao = (IEmployeeDao) empDao.newInstance();
			branchDao = (IBranchDao) branDao.newInstance();

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			throw new CustomBankException(Exceptions.DATABASE_CONNECTION_ERROR, e);
		}

	}

	public Employee getEmployee(int id) throws CustomBankException {

		List<Employee> employees = employeeDao.getEmployees(id);

		if (!employees.isEmpty()) {
			return employees.get(0);
		} else {
			throw new CustomBankException(Exceptions.EMPLOYEE_NOT_FOUND);
		}
	}

	public Map<Integer, Map<Integer, Account>> getActiveAccounts(int branchId, int limit, int offSet)
			throws CustomBankException {

		return accountDao.getBranchAccounts(branchId, AccountStatus.ACTIVE, limit, offSet);
	}

	public Map<Integer, Map<Integer, Account>> getInactiveAccounts(int branchId, int limit, int offSet)
			throws CustomBankException {

		return accountDao.getBranchAccounts(branchId, AccountStatus.INACTIVE, limit, offSet);
	}

	public Branch getBranch(int id) throws CustomBankException {
		List<Branch> branches = branchDao.getBranch(id);

		if (!branches.isEmpty()) {
			return branches.get(0);
		} else {
			throw new CustomBankException(Exceptions.BRANCH_NOT_FOUND);
		}
	}

	public void activateAcc(int accNo) throws CustomBankException {

		Account account = new Account();

		Object lock = Lock.get(account.getUserId());

		synchronized (lock) {

		accountCache.rem(accNo);
		}
		account.setAccNo(accNo);
		account.setStatus(AccountStatus.ACTIVE);
		int result = accountDao.updateAccount(account);

		if (result == 0) {
			throw new CustomBankException(Exceptions.ACCOUNT_NOT_FOUND);
		}
	}

	public void deActivateAcc(int accNo) throws CustomBankException {

		Account account = new Account();

		Object lock = Lock.get(account.getUserId());

		synchronized (lock) {

		accountCache.rem(accNo);
		}
		account.setAccNo(accNo);
		account.setStatus(AccountStatus.INACTIVE);
		int result = accountDao.updateAccount(account);

		if (result == 0) {
			throw new CustomBankException(Exceptions.ACCOUNT_NOT_FOUND);
		}

	}

	public void addAccount(Account account) throws CustomBankException {

		HelperUtils.nullCheck(account);

		Object lock = Lock.get(account.getUserId());

		synchronized (lock) {

		accountMapCache.rem(account.getUserId());
		accountCache.rem(account.getAccNo());
	}

		CustomerHelper customerHelper = new CustomerHelper();

		customerHelper.getCustomer(account.getUserId());

		account.setBalance(0);

		account.setStatus(AccountStatus.ACTIVE);

		accountDao.addAccounts(List.of(account));

	}

	public void addCustomer(Customer customer) throws CustomBankException {

		HelperUtils.nullCheck(customer);

		customer.setStatus(UserStatus.ACTIVE);

		try {
			if (EmployeeUtils.validatePhone(customer.getPhone())
					&& EmployeeUtils.validateEmail(customer.getEmail())
					&& EmployeeUtils.validatePass(customer.getPassword())
					&& EmployeeUtils.validateAadhar(customer.getAadhar())
					&& EmployeeUtils.validatePAN(customer.getPAN())) {


				String password = customer.getPassword();
				customer.setPassword(HashEncoder.encode(password));

				customerDao.addCustomer(List.of(customer));
			}
		} catch (Exception e) {
			throw new CustomBankException(Exceptions.SIGNUP_FAILED + e.getMessage());

		}

	}

}
