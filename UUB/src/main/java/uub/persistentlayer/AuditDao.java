package uub.persistentlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uub.model.Audit;
import uub.persistentinterfaces.IAuditDao;
import uub.staticlayer.ConnectionManager;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class AuditDao implements IAuditDao {

	@Override
	public void addAudit(List<Audit> audits) throws CustomBankException {
		HelperUtils.nullCheck(audits);

		String addQuery = "INSERT INTO AUDIT_LOGS (USER_ID, TIME, ACTION, TARGET_ID, RESULT, DESCRIPTION ) VALUES (?,?,?,?,?,?)";

		try (Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(addQuery);) {


			for (Audit audit : audits) {
				
				statement.setObject(1, audit.getUserId());
				statement.setObject(2, audit.getTime());
				statement.setObject(3, (audit.getAction()).getAction());
				statement.setObject(4, audit.getTargetId());
				statement.setObject(5, audit.getResult().getResult());
				statement.setObject(6, audit.getDesc());
				statement.addBatch();
			}

			statement.executeBatch();
		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

	}

	@Override
	public List<Audit> getAuditHistory(int userId) throws CustomBankException {

		List<Audit> audits = new ArrayList<Audit>();

		String getQuery = "SELECT * FROM AUDIT WHERE USER_ID = ?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(getQuery);) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Audit audit = mapAudit(resultSet);
				audits.add(audit);
			}

		} catch (SQLException e) {
			throw new CustomBankException(e.getMessage());
		}

		return audits;

	}
	
	private Audit mapAudit(ResultSet resultSet) throws SQLException{
		
		Audit audit = new Audit();
		
		audit.setId(resultSet.getLong("ID"));
		audit.setUserId(resultSet.getInt("USER_ID"));
		audit.setTime(resultSet.getLong("TIME"));
		audit.setAction(resultSet.getString("ACTION"));
		audit.setTargetId(resultSet.getInt("TARGET_ID"));
		audit.setResult(resultSet.getInt("RESULT"));
		audit.setDesc(resultSet.getString("DESC"));
		return audit;
		
	}
	
}
