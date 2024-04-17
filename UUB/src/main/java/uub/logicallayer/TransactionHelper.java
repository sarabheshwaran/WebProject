package uub.logicallayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import uub.enums.Exceptions;
import uub.enums.TransferType;
import uub.model.Account;
import uub.model.Transaction;
import uub.persistentinterfaces.ITransactionDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
import uub.staticlayer.TransactionUtils;

public class TransactionHelper {

	private ITransactionDao transactionDao;

	public TransactionHelper() throws CustomBankException {

		try {

			Class<?> TransactionDao = Class.forName("uub.persistentlayer.TransactionDao");
			Constructor<?> transDao = TransactionDao.getDeclaredConstructor();

			transactionDao = (ITransactionDao) transDao.newInstance();

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			throw new CustomBankException(Exceptions.DATABASE_CONNECTION_ERROR, e);
		}

	}

	public void selfTransfer(Transaction transaction, TransferType type) throws CustomBankException {

		if (type == TransferType.WITHDRAW) {

			transaction.setAmount(0 - transaction.getAmount());

		}

		Object lock = Lock.get(transaction.getUserId());

		synchronized (lock) {

			setTransaction(transaction);
			transaction.setLastModifiedTime(DateUtils.getTime());
			transactionDao.makeTransaction(List.of(transaction));

		}

	}

	public void outBankTransfer(Transaction transaction) throws CustomBankException {

		transaction.setAmount(0 - transaction.getAmount());

		Object lock = Lock.get(transaction.getUserId());

		synchronized (lock) {
			setTransaction(transaction);
			transaction.setLastModifiedTime(DateUtils.getTime());

			transactionDao.makeTransaction(List.of(transaction));
		}
	}

	public void inBankTransfer(Transaction transaction) throws CustomBankException {

		transaction.setAmount(0 - transaction.getAmount());

		Transaction rTransaction = generateReceiverTransaction(transaction);

		Object lock = Lock.get(transaction.getUserId());

		synchronized (lock) {

			setTransaction(transaction);
			setTransaction(rTransaction);

			transaction.setLastModifiedTime(DateUtils.getTime());
			rTransaction.setLastModifiedTime(DateUtils.getTime());
			transactionDao.makeTransaction(List.of(transaction, rTransaction));
		}
	}

	private void setTransaction(Transaction transaction) throws CustomBankException {

		CustomerHelper customerHelper = new CustomerHelper();

		transaction.setTime(System.currentTimeMillis());

		int accNo = transaction.getAccNo();

		Account account = customerHelper.getAccount(accNo);

		UserHelper.accountCache.rem(accNo);

		TransactionUtils.validateAccount(account);

		double balance = account.getBalance();

		double closingBalance = balance + transaction.getAmount();

		if (closingBalance < 0) {
			throw new CustomBankException(Exceptions.BALANCE_INSUFFICIENT);
		}

		transaction.setOpeningBal(balance);
		transaction.setClosingBal(closingBalance);

	}

	private Transaction generateReceiverTransaction(Transaction transaction) throws CustomBankException {

		CustomerHelper customerHelper = new CustomerHelper();

		Transaction rTransaction = new Transaction();

		rTransaction.setId(transaction.getId());
		rTransaction.setTime(transaction.getTime());
		rTransaction.setDesc(transaction.getDesc());
		rTransaction.setType(transaction.getType());
		rTransaction.setStatus(transaction.getStatus());
		rTransaction.setLastModifiedBy(transaction.getLastModifiedBy());

		int accNo = transaction.getTransactionAcc();

		rTransaction.setAccNo(accNo);
		rTransaction.setTransactionAcc(transaction.getAccNo());
		rTransaction.setAmount(0 - transaction.getAmount());

		Account account = customerHelper.getAccount(accNo);

		int userId = account.getUserId();

		rTransaction.setUserId(userId);

		return rTransaction;

	}

	public List<Transaction> getTransactions(int accNo, long from, long to, int limit, int offSet)
			throws CustomBankException {
		return transactionDao.getTransactions(accNo, from, to, limit, offSet);
	}

	public int getTransactionCount(int accNo, long from, long to) throws CustomBankException {

		return transactionDao.getTransactionCount(accNo, from, to);
	}

}
