package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uub.model.ApiAuth;
import uub.persistentinterfaces.IApiAuthDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class ApiAuthDao implements IApiAuthDao {

	@Override
	public void addAPIAuth(List<ApiAuth> ApiAuths) throws CustomBankException {
		HelperUtils.nullCheck(ApiAuths);

		String addQuery = "INSERT INTO API_AUTH ( API_KEY, USER_ID, SCOPE, CREATED_TIME, VALIDITY ) VALUES (?,?,?,?,?)";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(addQuery);) {
			for (ApiAuth apiAuth : ApiAuths) {

				statement.setObject(1, apiAuth.getApiKey());
				statement.setObject(2, apiAuth.getUserId());
				statement.setObject(3, apiAuth.getScope());
				statement.setObject(4, apiAuth.getCreatedTime());
				statement.setObject(5, apiAuth.getValidity());
				statement.addBatch();
			}

			System.out.println(statement);
			statement.executeBatch();
		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	@Override
	public List<ApiAuth> getApiKeyOfUsers(int userId) throws CustomBankException {

		List<ApiAuth> apiAuths = new ArrayList<ApiAuth>();

		String getQuery = "SELECT * FROM API_AUTH WHERE USER_ID = ?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery);) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ApiAuth apiAuth = mapApiAuth(resultSet);
				apiAuths.add(apiAuth);
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return apiAuths;

	}

	private ApiAuth mapApiAuth(ResultSet resultSet) throws SQLException {

		ApiAuth apiAuth = new ApiAuth();

		apiAuth.setApiKey(resultSet.getString("API_KEY"));
		apiAuth.setUserId(resultSet.getInt("USER_ID"));
		apiAuth.setScope(resultSet.getInt("SCOPE"));
		apiAuth.setCreatedTime(resultSet.getLong("CREATED_TIME"));
		apiAuth.setValidity(resultSet.getInt("VALIDITY"));
		return apiAuth;

	}

	@Override
	public ApiAuth getApiAuth(String apiKey) throws CustomBankException {
		String getQuery = "SELECT * FROM API_AUTH WHERE API_KEY = ?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery);) {

			statement.setString(1, apiKey);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return mapApiAuth(resultSet);
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	@Override
	public void removeApiKey(String apiKey) throws CustomBankException {

		String getQuery = "DELETE FROM API_AUTH WHERE API_KEY = ?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery);) {

			statement.setString(1, apiKey);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}
}
