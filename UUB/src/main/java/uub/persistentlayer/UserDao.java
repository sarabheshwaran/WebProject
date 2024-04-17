package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uub.enums.UserStatus;
import uub.model.User;
import uub.persistentinterfaces.IUserDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class UserDao implements IUserDao {


	@Override
	public int updateUser(User user) throws CustomBankException {

		HelperUtils.nullCheck(user);


		StringBuilder updateQuery = new StringBuilder("UPDATE USER SET  ");

		updateQuery.append(getFieldList(user)).append("WHERE ID = ?");

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(updateQuery.toString())) {

			setValues(statement, user);
			return statement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}


	}

	@Override
	public List<User> getUser(int userId, UserStatus status) throws CustomBankException {

		String getQuery = "SELECT * FROM USER WHERE ID = '" + userId + "' AND STATUS = "+ status.getStatus();


		List<User> users = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(getQuery);) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				User user = mapUser(resultSet);
				users.add(user);
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return users;

	}

	private String getFieldList(User user) {

		StringBuilder queryBuilder = new StringBuilder("  ");

		if (user.getName() != null) {
			queryBuilder.append("NAME = ? , ");
		}
		if (user.getEmail() != null) {
			queryBuilder.append("EMAIL = ? , ");
		}
		if (user.getPhone() != null) {
			queryBuilder.append("PHONE = ? , ");
		}
		if (user.getDOB() != 0) {
			queryBuilder.append("DOB = ? , ");
		}
		if (user.getGender() != null) {
			queryBuilder.append("GENDER = ? , ");
		}
		if (user.getPassword() != null) {
			queryBuilder.append("PASSWORD = ?, ");
		}
		if (user.getUserType() != null) {
			queryBuilder.append("USER_TYPE = ? , ");
		}
		if (user.getStatus() != null) {
			queryBuilder.append("STATUS = ? , ");
		}
		if(user.getLastModifiedTime() != 0) {
			queryBuilder.append("LAST_MODIFIED_TIME = ? , ");
		}
		if(user.getLastModifiedBy() != 0) {
			queryBuilder.append("LAST_MODIFIED_BY = ? , ");
		}

		queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
		return queryBuilder.toString();

	}

	private void setValues(PreparedStatement statement, User user) throws SQLException {

		int index = 1;

		if (user.getName() != null) {
			statement.setObject(index++, user.getName());
		}
		if (user.getEmail() != null) {
			statement.setObject(index++, user.getEmail());
		}
		if (user.getPhone() != null) {
			statement.setObject(index++, user.getPhone());
		}
		if (user.getDOB() != 0) {
			statement.setLong(index++, user.getDOB());
		}
		if (user.getGender() != null) {
			statement.setObject(index++, user.getGender());
		}
		if (user.getPassword() != null) {
			statement.setObject(index++, user.getPassword());
		}
		if (user.getUserType() != null) {
			statement.setObject(index++, user.getUserType().getType());
		}
		if (user.getStatus() != null) {
			statement.setObject(index++, user.getStatus().getStatus());
		}
		if (user.getLastModifiedTime() != 0) {
			statement.setLong(index++, user.getLastModifiedTime());
		}
		if (user.getLastModifiedBy() != 0) {
			statement.setInt(index++, user.getLastModifiedBy());
		}
		if (user.getId() != 0) {
			statement.setObject(index++, user.getId());
		}

	}

	private User mapUser(ResultSet resultSet) throws SQLException {

		User user = new User();

		user.setId(resultSet.getInt("USER.ID"));
		user.setName(resultSet.getString("USER.NAME"));
		user.setEmail(resultSet.getString("USER.EMAIL"));
		user.setPhone(resultSet.getString("USER.PHONE"));
		user.setDOB(resultSet.getLong("USER.DOB"));
		user.setGender(resultSet.getString("USER.GENDER"));
		user.setPassword(resultSet.getString("USER.PASSWORD"));
		user.setUserType(resultSet.getInt("USER.USER_TYPE"));
		user.setStatus(resultSet.getInt("USER.STATUS"));

		return user;

	}

}
