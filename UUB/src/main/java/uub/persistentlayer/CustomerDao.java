package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import uub.model.Customer;
import uub.persistentinterfaces.ICustomerDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class CustomerDao implements ICustomerDao {

	@Override
	public void addCustomer(List<Customer> customers) throws CustomBankException {


		Connection connection = null;
		try {

			connection = ConnectionManager.getConnection();
		connection.setAutoCommit(false);

		String addQuery1 = "INSERT INTO USER (NAME,EMAIL,PHONE,DOB,GENDER,PASSWORD,USER_TYPE,STATUS) VALUES (?,?,?,?,?,?,?,?)";
		String addQuery2 = "INSERT INTO CUSTOMER VALUES (?,?,?,?)";

		try (
				PreparedStatement statement = connection.prepareStatement(addQuery1,
				Statement.RETURN_GENERATED_KEYS);
				PreparedStatement statement2 = connection.prepareStatement(addQuery2)) {


			for (Customer customer : customers) {
				statement.setObject( 1, customer.getName());
				statement.setObject( 2, customer.getEmail());
				statement.setObject( 3, customer.getPhone());
				statement.setObject( 4, customer.getDOB());
				statement.setObject( 5, customer.getGender());
				statement.setObject( 6, customer.getPassword());
				statement.setObject( 7, customer.getUserType().getType());
				statement.setObject( 8, customer.getStatus().getStatus());
				statement.addBatch();
			}
			statement.executeBatch();

			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				int index = 0;
				while (resultSet.next()) {

					int id = resultSet.getInt(1);
					Customer customer = customers.get(index);
					statement2.setObject( 1, id);
					statement2.setObject( 2, customer.getAadhar());
					statement2.setObject( 3, customer.getPAN());
					statement2.setObject( 4, customer.getAddress());

					statement2.addBatch();
					index++;
				}

				statement2.executeBatch();

			}

		}

			connection.commit();

		}  catch (SQLException e) {


			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException rbException) {
				e.addSuppressed(rbException);

			}
			throw new CustomBankException(e.getMessage());


		} finally {
			try {
				if (connection != null) {
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch (SQLException e1) {
				throw new CustomBankException(e1.getMessage());

			}
		}

	}

	@Override
	public List<Customer> getCustomers(int id) throws CustomBankException {

		String getQuery = "SELECT * FROM CUSTOMER JOIN USER ON CUSTOMER.ID = USER.ID WHERE CUSTOMER.ID = ?";

		List<Customer> customers = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery)) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Customer resultCustomer = mapCustomer(resultSet);
				customers.add(resultCustomer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomBankException(e.getMessage());
		}

		return customers;

	}


	@Override
	public void updateCustomer(Customer customer) throws CustomBankException {

		HelperUtils.nullCheck(customer);

		StringBuilder updateQuery = new StringBuilder("UPDATE CUSTOMER JOIN USER ON USER.ID = CUSTOMER.ID SET  ");

		updateQuery.append(getFieldList(customer)).append("WHERE ID = ");

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(updateQuery.toString())) {

			setValues(statement, customer);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}


	private String getFieldList(Customer customer) {

		StringBuilder queryBuilder = new StringBuilder("  ");

		if (customer.getId() != 0) {
			queryBuilder.append("CUSTOMER.ID = ? , ");
		}
		if (customer.getName() != null) {
			queryBuilder.append("NAME = ? , ");
		}
		if (customer.getEmail() != null) {
			queryBuilder.append("EMAIL = ? , ");
		}
		if (customer.getPhone() != null) {
			queryBuilder.append("PHONE = ? , ");
		}
		if (customer.getDOB() != 0) {
			queryBuilder.append("DOB = ? , ");
		}
		if (customer.getGender() != null) {
			queryBuilder.append("GENDER = ? , ");
		}
		if (customer.getPassword() != null) {
			queryBuilder.append("PASSWORD = ?, ");
		}
		if (customer.getUserType() != null) {
			queryBuilder.append("USER_TYPE = ? , ");
		}
		if (customer.getStatus() != null) {
			queryBuilder.append("STATUS = ? , ");
		}
		if (customer.getAadhar() != null) {
			queryBuilder.append("AADHAR_NO = ? , ");
		}
		if (customer.getPAN() != null) {
			queryBuilder.append("PAN = ? , ");
		}
		if (customer.getAddress() != null) {
			queryBuilder.append("ADDRESS = ? , ");
		}

		queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
		return queryBuilder.toString();

	}

	private void setValues(PreparedStatement statement, Customer customer) throws SQLException {

		int index = 1;

		if (customer.getName() != null) {
			statement.setObject(index++, customer.getName());
		}
		if (customer.getEmail() != null) {
			statement.setObject(index++, customer.getEmail());
		}
		if (customer.getPhone() != null) {
			statement.setObject(index++, customer.getPhone());
		}
		if (customer.getDOB() != 0) {
			statement.setLong(index++, customer.getDOB());
		}
		if (customer.getGender() != null) {
			statement.setObject(index++, customer.getGender());
		}
		if (customer.getPassword() != null) {
			statement.setObject(index++, customer.getPassword());
		}
		if (customer.getUserType() != null) {
			statement.setObject(index++, customer.getUserType());
		}
		if (customer.getStatus() != null) {
			statement.setObject(index++, customer.getStatus().getStatus());
		}
		if (customer.getAadhar() != null) {
			statement.setObject(index++, customer.getAadhar());
		}
		if (customer.getPAN() != null) {
			statement.setObject(index++, customer.getPAN());
		}
		if (customer.getAddress() != null) {
			statement.setObject(index++, customer.getAddress());
		}

		if (customer.getId() != 0) {
			statement.setObject(index++, customer.getId());
		}

	}

	private Customer mapCustomer(ResultSet resultSet) throws SQLException {

		Customer customer = new Customer();

		customer.setId(resultSet.getInt("CUSTOMER.ID"));
		customer.setName(resultSet.getString("USER.NAME"));
		customer.setEmail(resultSet.getString("USER.EMAIL"));
		customer.setPhone(resultSet.getString("USER.PHONE"));
		customer.setDOB(resultSet.getLong("USER.DOB"));
		customer.setGender(resultSet.getString("USER.GENDER"));
		customer.setPassword(resultSet.getString("USER.PASSWORD"));
		customer.setUserType(resultSet.getInt("USER.USER_TYPE"));
		customer.setStatus(resultSet.getInt("USER.STATUS"));
		customer.setAadhar(resultSet.getString("CUSTOMER.AADHAR_NO"));
		customer.setPAN(resultSet.getString("CUSTOMER.PAN"));
		customer.setAddress(resultSet.getString("CUSTOMER.ADDRESS"));

		return customer;

	}

}
