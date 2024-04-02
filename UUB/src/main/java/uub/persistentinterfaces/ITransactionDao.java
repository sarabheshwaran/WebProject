package uub.persistentinterfaces;

import java.util.List;

import uub.model.Transaction;
import uub.staticlayer.CustomBankException;

public interface ITransactionDao {

	public int getLastId() throws CustomBankException;

	List<Transaction> getTransactions(int accNo, long from, long to, int limit, int offSet) throws CustomBankException;

	List<Transaction> getTransactionsOfUser(int userId, long from, long to, int limit, int offSet) throws CustomBankException;

	public void makeTransaction(List<Transaction> transactions) throws CustomBankException;

	int getTransactionCount(int accNo, long from, long to) throws CustomBankException;

}
