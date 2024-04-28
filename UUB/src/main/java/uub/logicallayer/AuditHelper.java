package uub.logicallayer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uub.model.Audit;
import uub.persistentinterfaces.IAuditDao;
import uub.persistentlayer.AuditDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class AuditHelper {

	private static IAuditDao auditDao = new AuditDao();

	
	private static ExecutorService auditService = Executors.newCachedThreadPool();
	

	

	public static void addAudit(Audit audit) throws CustomBankException{
		

		HelperUtils.nullCheck(audit);
		
		auditService.execute(() -> {
			
			try {
				auditDao.addAudit(List.of(audit));
			} catch (CustomBankException e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	
	
	
}
