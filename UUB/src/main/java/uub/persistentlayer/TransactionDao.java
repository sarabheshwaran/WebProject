package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import uub.model.Transaction;
import uub.persistentinterfaces.ITransactionDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.ValidationUtils;

public class TransactionDao implements ITransactionDao {

	@Override
	public List<Transaction> getTransactions(int accNo, long from, long to, int limit, int offSet)
			throws CustomBankException {

		String getQuery = "SELECT * FROM TRANSACTION WHERE ACC_NO =" + accNo + " AND TIME BETWEEN " + from + " AND "
				+ to + " ORDER BY ID DESC  LIMIT  " + limit + " OFFSET " + offSet;

		return getTransactions(getQuery);

	}

	@Override
	public List<Transaction> getTransactionsOfUser(int userId, long from, long to, int limit, int offSet)
			throws CustomBankException {

		String getQuery = "SELECT * FROM TRANSACTION WHERE USER_ID =" + userId + " AND TIME BETWEEN " + from + " AND "
				+ to + " ORDER BY ID DESC  LIMIT  " + limit + " OFFSET " + offSet;

		return getTransactions(getQuery);

	}


	@Override
	public int getLastId() throws CustomBankException {

		int lastId = -1;

		String query = "SELECT MAX(ID) AS max_id FROM TRANSACTION";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				lastId = resultSet.getInt("max_id");
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return lastId;
	}

	private List<Transaction> getTransactions(String query) throws CustomBankException {
		List<Transaction> transactions = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();) {

			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Transaction transaction = mapTransaction(resultSet);
				transactions.add(transaction);
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return transactions;
	}

	@Override
	public int getTransactionCount (int accNo, long from, long to) throws CustomBankException {

		String query = "SELECT COUNT(*) AS COUNT FROM TRANSACTION WHERE ACC_NO =" + accNo + " AND TIME BETWEEN " + from + " AND "
				+ to ;

		
		try (Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();) {

			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				return resultSet.getInt("COUNT");
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return 0;
	}


	@Override
	public void makeTransaction(List<Transaction> transactions) throws CustomBankException {

		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();

			connection.setAutoCommit(false);

			addTransaction(connection, transactions);

			updateAccount(connection, transactions);

			connection.commit();

		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}


			} catch (SQLException rbException) {
				e.addSuppressed(rbException);
			}

			throw new CustomBankException("Transaction roll-backed",e);
		} finally {
			try {
				if (connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch (SQLException e) {
				throw new CustomBankException(e.getMessage());
			}
		}

	}

	private void addTransaction(Connection connection, List<Transaction> transactions) throws SQLException {

		String addQuery = "INSERT INTO TRANSACTION (ID, USER_ID, ACC_NO, TRANSACTION_ACC, TYPE, AMOUNT, OPENING_BAL, CLOSING_BAL, DESCRIPTION, TIME, STATUS,LAST_MODIFIED_BY,LAST_MODIFIED_TIME) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(addQuery)) {

			for (Transaction transaction : transactions) {

				statement.setObject(1, transaction.getId());
				statement.setObject(2, transaction.getUserId());
				statement.setObject(3, transaction.getAccNo());
				statement.setObject(4, transaction.getTransactionAcc());
				statement.setObject(5, transaction.getType().getType());
				statement.setObject(6, transaction.getAmount());
				statement.setObject(7, transaction.getOpeningBal());
				statement.setObject(8, transaction.getClosingBal());
				statement.setObject(9, ValidationUtils.sanitate(transaction.getDesc()));
				statement.setObject(10, transaction.getTime());
				statement.setObject(11, transaction.getStatus().getStatus());
				statement.setObject(12, transaction.getLastModifiedBy());
				statement.setObject(13, transaction.getLastModifiedTime());

				statement.addBatch();
			}
			statement.executeBatch();

		} catch (SQLException e) {
			throw e;
		}
	}

	private void updateAccount(Connection connection, List<Transaction> transactions) throws SQLException {

		String updateQuery = "UPDATE ACCOUNTS SET BALANCE = ?, LAST_MODIFIED_TIME = ?,LAST_MODIFIED_BY = ? WHERE ACC_NO = ?";

		try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {

			for (Transaction transaction : transactions) {

				statement.setDouble(1, transaction.getClosingBal());
				statement.setObject(2, transaction.getLastModifiedTime());
				statement.setObject(3, transaction.getLastModifiedBy());
				statement.setInt(4, transaction.getAccNo());
				statement.addBatch();
			}
			statement.executeBatch();

		} catch (SQLException e) {
			throw e;
		}

	}


	private Transaction mapTransaction(ResultSet resultSet) throws SQLException {
		Transaction transaction = new Transaction();

		transaction.setId(resultSet.getString("ID"));
		transaction.setUserId(resultSet.getInt("USER_ID"));
		transaction.setAccNo(resultSet.getInt("ACC_NO"));
		transaction.setTransactionAcc(resultSet.getInt("TRANSACTION_ACC"));
		transaction.setType(resultSet.getInt("TYPE"));
		transaction.setAmount(resultSet.getDouble("AMOUNT"));
		transaction.setOpeningBal(resultSet.getDouble("OPENING_BAL"));
		transaction.setClosingBal(resultSet.getDouble("CLOSING_BAL"));
		transaction.setDesc(resultSet.getString("DESCRIPTION"));
		transaction.setTime(resultSet.getLong("TIME"));
		transaction.setStatus(resultSet.getInt("STATUS"));

		return transaction;
	}

}
