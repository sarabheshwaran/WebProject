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
import uub.model.User;
import uub.persistentinterfaces.IBranchDao;
import uub.persistentinterfaces.IEmployeeDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
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

	public Account getAccounts(int accNo, int branchId) throws CustomBankException {



			List<Account> accounts = accountDao.getBranchAccount(accNo, branchId);

			if (!accounts.isEmpty()) {

				Account account = accounts.get(0);

				return account;

			} else {
				throw new CustomBankException(Exceptions.ACCOUNT_NOT_IN_BRANCH);
			}
		
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
		account.setLastModifiedTime(DateUtils.getTime());
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
		account.setLastModifiedTime(DateUtils.getTime());
		int result = accountDao.updateAccount(account);

		if (result == 0) {
			throw new CustomBankException(Exceptions.ACCOUNT_NOT_FOUND);
		}

	}


	public void activateUser(int userId, int modifier) throws CustomBankException {

		User user = new User();

		user.setId(userId);
		user.setStatus(UserStatus.ACTIVE);
		user.setLastModifiedBy(modifier);
		user.setLastModifiedTime(DateUtils.getTime());
		int result = userDao.updateUser(user);
		customerCache.rem(userId);
		if (result == 0) {
			throw new CustomBankException(Exceptions.USER_NOT_FOUND);
		}

	}

	public void deActivateUser(int userId, int modifier) throws CustomBankException {

		User user = new User();

		user.setId(userId);
		user.setStatus(UserStatus.INACTIVE);
		user.setLastModifiedBy(modifier);
		user.setLastModifiedTime(DateUtils.getTime());
		int result = userDao.updateUser(user);
		customerCache.rem(userId);

		if (result == 0) {
			throw new CustomBankException(Exceptions.USER_NOT_FOUND);
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

		account.setStatus(AccountStatus.ACTIVE);
		account.setLastModifiedTime(DateUtils.getTime());

		accountDao.addAccounts(List.of(account));

	}

	public void addCustomer(Customer customer) throws CustomBankException {

		HelperUtils.nullCheck(customer);

		customer.setStatus(UserStatus.ACTIVE);

		try {
			if (EmployeeUtils.validatePhone(customer.getPhone()) && EmployeeUtils.validateEmail(customer.getEmail())
					&& EmployeeUtils.validatePass(customer.getPassword())
					&& EmployeeUtils.validateAadhar(customer.getAadhar())
					&& EmployeeUtils.validatePAN(customer.getPAN())) {

				String password = customer.getPassword();
				customer.setPassword(HashEncoder.encode(password));
				customer.setLastModifiedTime(DateUtils.getTime());

				customerDao.addCustomer(List.of(customer));
			}
		} catch (Exception e) {
			throw new CustomBankException(Exceptions.SIGNUP_FAILED + e.getMessage());

		}

	}

	public void editCustomer(int id, Customer customer) throws CustomBankException {

		HelperUtils.nullCheck(customer);

		Customer compareObject = getCustomer(id);

		
		
		try {
			
			
			if (EmployeeUtils.validatePhone(customer.getPhone()) && EmployeeUtils.validateEmail(customer.getEmail())
					&& EmployeeUtils.validateAadhar(customer.getAadhar())
					&& EmployeeUtils.validatePAN(customer.getPAN())) {

				int count = 0;
				customer.setId(id);

				if (customer.getName().equals(compareObject.getName())) {
					customer.setName(null);
					count ++;
				}
				if (customer.getEmail().equals(compareObject.getEmail())) {
					customer.setEmail(null);
					count ++;
				}
				if (customer.getPhone().equals(compareObject.getPhone())) {
					customer.setPhone(null);
					count ++;
				}
				if (customer.getDOB() == compareObject.getDOB()) {
					customer.setDOB(0l);
					count ++;
				}
				if (customer.getGender().equals(compareObject.getGender())) {
					customer.setGender(null);
					count ++;
				}
				if (customer.getAadhar().equals(compareObject.getAadhar())) {
					customer.setAadhar(null);
					count ++;
				}
				if (customer.getPAN().equals(compareObject.getPAN())) {
					customer.setPAN(null);
					count ++;
				}
				if (customer.getAddress().equals(compareObject.getAddress())) {
					customer.setAddress(null);
					count ++;
				}

				if(count<8) {
				customerDao.updateCustomer(customer);
				customer.setLastModifiedTime(DateUtils.getTime());


				customerCache.rem(id);}
			}
		} catch (Exception e) {
			throw new CustomBankException(Exceptions.UPDATE_FAILED + e.getMessage());

		}

	}

}
