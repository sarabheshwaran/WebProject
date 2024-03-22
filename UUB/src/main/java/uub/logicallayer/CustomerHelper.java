package uub.logicallayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uub.enums.Exceptions;
import uub.enums.TransactionStatus;
import uub.enums.TransferType;
import uub.model.Account;
import uub.model.Customer;
import uub.model.Transaction;
import uub.persistentinterfaces.IAccountDao;
import uub.persistentinterfaces.ICustomerDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
import uub.staticlayer.HelperUtils;
import uub.staticlayer.TransactionUtils;

public class CustomerHelper extends UserHelper {

	protected IAccountDao accountDao;
	protected ICustomerDao customerDao;

	public CustomerHelper() throws CustomBankException {

		try {
			Class<?> AccountDao = Class.forName("uub.persistentlayer.AccountDao");
			Constructor<?> accDao = AccountDao.getDeclaredConstructor();

			Class<?> CustomerDao = Class.forName("uub.persistentlayer.CustomerDao");
			Constructor<?> cusDao = CustomerDao.getDeclaredConstructor();

			accountDao = (IAccountDao) accDao.newInstance();
			customerDao = (ICustomerDao) cusDao.newInstance();

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			throw new CustomBankException(Exceptions.DATABASE_CONNECTION_ERROR, e);
		}
	}

	public Customer getCustomer(int id) throws CustomBankException {

		Object lock = Lock.get(id);

		synchronized (lock) {

		Customer customer = customerCache.get(id);

		if (customer != null) {
			return customer;
		}else {

		List<Customer> customers = customerDao.getCustomers(id);

		if (!customers.isEmpty()) {
			customer = customers.get(0);
			UserHelper.customerCache.set(id, customer);
			return customer;

		} else {
			throw new CustomBankException(Exceptions.CUSTOMER_NOT_FOUND);
		}}}

	}

	public Map<Integer, Account> getAccounts(int customerId) throws CustomBankException {

		List<Integer> accNos = accountMapCache.get(customerId);

		Map<Integer, Account> accounts = new HashMap<>();

		if (accNos != null) {

			for (int accNo : accNos) {

				accounts.put(accNo, getAccount(accNo));

			}
			return accounts;
		} else {
			accounts = accountDao.getUserAccounts(customerId);

			if (!accounts.isEmpty()) {

				accountMapCache.set(customerId, new ArrayList<>(accounts.keySet()));

				for(Account account : accounts.values()) {
					accountCache.set(account.getAccNo(), account);
				}

				return accounts;

			} else {
				throw new CustomBankException(Exceptions.ACCOUNT_NOT_FOUND);
			}
		}

	}

	public Account getAccount(int accNo) throws CustomBankException {

		Account account = UserHelper.accountCache.get(accNo);

		if (account != null) {
			return account;
		} else {

			List<Account> accounts = accountDao.getAccount(accNo);

			if (!accounts.isEmpty()) {

				account = accounts.get(0);

				UserHelper.accountCache.set(accNo, account);

				return account;

			} else {
				throw new CustomBankException(Exceptions.ACCOUNT_NOT_FOUND);
			}
		}

	}

	public void makeTransaction(Transaction transaction, String password) throws CustomBankException {

		HelperUtils.nullCheck(transaction);
		HelperUtils.nullCheck(password);

		TransactionHelper transactionHelper = new TransactionHelper();

		TransactionUtils.validateTransaction(transaction);

		try {

			passwordValidate(transaction.getUserId(), password);

			String id = TransactionUtils.generateUniqueId(transaction.getAccNo(), transaction.getTransactionAcc());
			transaction.setId(id);

			transaction.setStatus(TransactionStatus.SUCCESS);

			TransferType type = transaction.getType();

			switch (type) {

			case WITHDRAW:
			case DEPOSIT: {
				transactionHelper.selfTransfer(transaction, type);
				break;
			}
			case INTER_BANK: {
				transactionHelper.outBankTransfer(transaction);
				break;
			}
			case INTRA_BANK: {
				transactionHelper.inBankTransfer(transaction);
				break;
			}
			}

		} catch (CustomBankException e) {
			throw new CustomBankException(Exceptions.TRANSACTION_ERROR, e);
		}

	}

	public List<Transaction> getNDaysTransaction(int accNo, int days, int limit, int page) throws CustomBankException {

		TransactionHelper transactionHelper = new TransactionHelper();
		long todayMillis = System.currentTimeMillis();

		long ansMillis = todayMillis - 86400000 * (days);

		return transactionHelper.getTransactions(accNo, ansMillis, todayMillis, limit, (page - 1) * limit);

	}

	public List<Transaction> getTransaction(int accNo, String from, String to, int limit, int page)
			throws CustomBankException {

		HelperUtils.nullCheck(from);
		HelperUtils.nullCheck(to);

		TransactionHelper transactionHelper = new TransactionHelper();
		LocalDate fromDate = LocalDate.parse(from);
		LocalDate toDate = LocalDate.parse(to);

		toDate = toDate.plusDays(1);

		long fromMillis = DateUtils.formatDate(fromDate);
		long toMillis = DateUtils.formatDate(toDate);

		return transactionHelper.getTransactions(accNo, fromMillis, toMillis, limit, (page - 1) * limit);
	}

}
