package uub.persistentinterfaces;

import java.util.List;

import uub.model.Audit;
import uub.staticlayer.CustomBankException;

public interface IAuditDao {

	public List<Audit> getAuditHistory(int userId) throws CustomBankException;

	public void addAudit(List<Audit> audits) throws CustomBankException;

	
	
}
