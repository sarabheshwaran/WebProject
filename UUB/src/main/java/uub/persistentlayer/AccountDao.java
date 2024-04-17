package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uub.enums.AccountStatus;
import uub.enums.AccountType;
import uub.model.Account;
import uub.persistentinterfaces.IAccountDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class AccountDao implements IAccountDao {

	@Override
	public Map<Integer, Account> getUserAccounts(int userId) throws CustomBankException {

		String getQuery = "SELECT * FROM ACCOUNTS WHERE USER_ID = ? ";

		Map<Integer, Account> accountMap = new HashMap<>();

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery)) {

			statement.setInt(1, userId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Account account;

				account = mapAccount(resultSet);
				accountMap.put(account.getAccNo(), account);

			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return accountMap;


	}

	@Override
	public Map<Integer, Map<Integer, Account>> getBranchAccounts(int branchId, AccountStatus status, int limit,
			int offSet) throws CustomBankException {

		String getQuery = "SELECT * FROM ACCOUNTS WHERE  BRANCH_ID = ? AND ACCOUNTS.STATUS = ? LIMIT ? OFFSET ?";


		Map<Integer, Map<Integer, Account>> accountMap = new HashMap<>();


		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery)) {

			statement.setInt(1, branchId);
			statement.setObject(2, status.getStatus());
			statement.setInt(3, limit);
			statement.setInt(4, offSet);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Account account;

				int id = resultSet.getInt("USER_ID");

				if (!accountMap.containsKey(id)) {

					accountMap.put(id, new HashMap<>());

				}
				account = mapAccount(resultSet);
				accountMap.get(id).put(account.getAccNo(), account);

			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return accountMap;

	}

	@Override
	public List<Account> getAccount(int accNo) throws CustomBankException {

		String getQuery = "SELECT * FROM ACCOUNTS WHERE ACC_NO = ? " ;

		List<Account> accounts = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery)) {

			statement.setInt(1, accNo);

			ResultSet resultSet = statement.executeQuery();

			Account account;
			while (resultSet.next()) {

				account = mapAccount(resultSet);
				accounts.add(account);

			}

			return accounts;
		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	@Override
	public void addAccounts(List<Account> accounts) throws CustomBankException {

		HelperUtils.nullCheck(accounts);

		String addQuery = "INSERT INTO ACCOUNTS (USER_ID,BRANCH_ID,TYPE,BALANCE,STATUS,LAST_MODIFIED_BY,LAST_MODIFIED_TIME) VALUES (?,?,?,?,?,?,?)";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(addQuery)) {
			for (Account account : accounts) {
				statement.setObject(1, account.getUserId());
				statement.setObject(2, account.getBranchId());
				statement.setObject(3, account.getType().getType());
				statement.setObject(4, account.getBalance());
				statement.setObject(5, account.getStatus().getStatus());
				statement.setObject(6, account.getLastModifiedBy());
				statement.setObject(6, account.getLastModifiedTime());

				statement.addBatch();
			}
			statement.executeBatch();

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	@Override
	public int updateAccount(Account account) throws CustomBankException {

		HelperUtils.nullCheck(account);

		StringBuilder updateQuery = new StringBuilder("UPDATE ACCOUNTS SET  ");

		updateQuery.append(buildSet(account)).append("WHERE ACC_NO = ?");

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(updateQuery.toString())) {

			setValues(statement, account);

			return statement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	private Account mapAccount(ResultSet resultSet) throws SQLException, CustomBankException {
		Account account = new Account();

		account.setAccNo(resultSet.getInt("ACC_NO"));
		account.setUserId(resultSet.getInt("USER_ID"));
		account.setBranchId(resultSet.getInt("BRANCH_ID"));
		account.setType(AccountType.valueOf(resultSet.getInt("TYPE")));
		account.setBalance(resultSet.getDouble("BALANCE"));
		account.setStatus(AccountStatus.valueOf(resultSet.getInt("STATUS")));
		account.setLastModifiedBy(resultSet.getInt("LAST_MODIFIED_BY"));
		account.setLastModifiedTime(resultSet.getLong("LAST_MODIFIED_TIME"));

		return account;
	}

	private String buildSet(Account account) {

		StringBuilder queryBuilder = new StringBuilder("  ");

		if (account.getUserId() != 0) {
			queryBuilder.append("USER_ID = ? , ");
		}
		if (account.getBranchId() != 0) {
			queryBuilder.append("BRANCH_ID = ? , ");
		}
		if (account.getType() != null) {
			queryBuilder.append("TYPE = ? , ");
		}
		if (account.getBalance() != -1) {
			queryBuilder.append("BALANCE = ? , ");
		}
		if (account.getStatus() != null) {
			queryBuilder.append("STATUS = ? , ");
		}
		if(account.getLastModifiedTime() != 0) {
			queryBuilder.append("LAST_MODIFIED_TIME = ? , ");
		}
		if(account.getLastModifiedBy() != 0) {
			queryBuilder.append("LAST_MODIFIED_BY = ? , ");
		}

		queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
		return queryBuilder.toString();

	}

	private void setValues(PreparedStatement statement, Account account) throws SQLException {

		int index = 1;

		if (account.getUserId() != 0) {
			statement.setObject(index++, account.getUserId());
		}
		if (account.getBranchId() != 0) {
			statement.setObject(index++, account.getBranchId());
		}
		if (account.getType() != null) {
			statement.setObject(index++, account.getType());
		}
		if (account.getBalance() != -1) {
			statement.setObject(index++, account.getBalance());
		}
		if (account.getStatus() != null) {
			statement.setObject(index++, account.getStatus().getStatus());
		}
		if (account.getLastModifiedTime() != 0) {
			statement.setLong(index++, account.getLastModifiedTime());
		}
		if (account.getLastModifiedBy() != 0) {
			statement.setInt(index++, account.getLastModifiedBy());
		}
		if (account.getAccNo() != 0) {
			statement.setInt(index++, account.getAccNo());
		}

	}

	@Override
	public List<Account> getBranchAccount(int accNo, int branchId) throws CustomBankException {

		String getQuery = "SELECT * FROM ACCOUNTS WHERE ACC_NO = ? AND BRANCH_ID = ?" ;

		List<Account> accounts = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery)) {

			statement.setInt(1, accNo);
			statement.setInt(2, branchId);

			ResultSet resultSet = statement.executeQuery();

			Account account;
			
			while (resultSet.next()) {

				account = mapAccount(resultSet);
				accounts.add(account);

			}

			return accounts;
		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}
	}
}
