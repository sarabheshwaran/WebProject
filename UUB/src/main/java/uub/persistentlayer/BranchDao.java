package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uub.model.Branch;
import uub.persistentinterfaces.IBranchDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;
import uub.staticlayer.ValidationUtils;

public class BranchDao implements IBranchDao {


	@Override
	public Map<Integer,Branch> getBranches() throws CustomBankException {

		Map<Integer,Branch> branches = new HashMap<>();

		String getQuery = "SELECT * FROM BRANCH ";

		try (Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();) {

			ResultSet resultSet = statement.executeQuery(getQuery);

			while (resultSet.next()) {
				Branch branch = mapBranch(resultSet);
				branches.put(branch.getId(),branch);
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return branches;

	}

	@Override
	public List<Branch> getBranch(int id) throws CustomBankException {

		List<Branch> branches = new ArrayList<>();

		String getQuery = "SELECT * FROM BRANCH WHERE ID = ?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery);) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Branch branch = mapBranch(resultSet);
				branches.add(branch);
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return branches;

	}

	@Override
	public void addBranch(List<Branch> branches) throws CustomBankException {
		HelperUtils.nullCheck(branches);

		String addQuery = "INSERT INTO BRANCH (IFSC,NAME,ADDRESS) VALUES (?,?,?)";

		try (Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(addQuery);) {


			for (Branch branch : branches) {

				statement.setObject( 1, ValidationUtils.sanitate(branch.getiFSC()));
				statement.setObject( 2, ValidationUtils.sanitate(branch.getName()));
				statement.setObject( 3, ValidationUtils.sanitate(branch.getAddress()));

				statement.addBatch();
			}

			statement.executeBatch();
		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	@Override
	public void updateBranches(Branch branch) throws CustomBankException {

		HelperUtils.nullCheck(branch);

		StringBuilder getQuery1 = new StringBuilder("SELECT * FROM BRANCH ");

		getQuery1.append(getFieldList(branch)).append("WHERE ID = ?");

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery1.toString())) {

			setValues(statement, branch);
			statement.executeUpdate();



		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}


	private Branch mapBranch(ResultSet resultSet) throws SQLException {

		Branch branch = new Branch();
		branch.setId(resultSet.getInt("id"));
		branch.setiFSC(resultSet.getString("ifsc"));
		branch.setName(resultSet.getString("name"));
		branch.setAddress(resultSet.getString("address"));

		return branch;
	}

	private String getFieldList(Branch branch) {

		StringBuilder queryBuilder = new StringBuilder("  ");

		if (branch.getName() != null) {
			queryBuilder.append("NAME = ? , ");
		}
		if (branch.getiFSC() != null) {
			queryBuilder.append("IFSC = ? , ");
		}
		if (branch.getAddress() != null) {
			queryBuilder.append("ADDRESS = ? , ");
		}

		queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
		return queryBuilder.toString();

	}

	private void setValues(PreparedStatement statement, Branch branch) throws SQLException {

		int index = 1;

		if (branch.getName() != null) {
			statement.setObject(index++, ValidationUtils.sanitate(branch.getName()));
		}
		if (branch.getiFSC() != null) {
			statement.setObject(index++, ValidationUtils.sanitate(branch.getiFSC()));
		}
		if (branch.getAddress() != null) {
			statement.setObject(index++, ValidationUtils.sanitate(branch.getAddress()));
		}
		if (branch.getId() != 0) {
			statement.setObject(index++, branch.getId());
		}

	}

}
