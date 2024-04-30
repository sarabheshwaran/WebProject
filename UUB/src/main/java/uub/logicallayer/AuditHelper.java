package uub.logicallayer;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import uub.model.Audit;
import uub.persistentinterfaces.IAuditDao;
import uub.persistentlayer.AuditDao;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class AuditHelper {

	private static IAuditDao auditDao = new AuditDao();

	private static final ExecutorService auditService = Executors.newFixedThreadPool(5);
	private static final CompletionService<Void> completionService = new ExecutorCompletionService<>(auditService);
	private static final ExecutorService completionHandlerExecutor = Executors.newSingleThreadExecutor();

	public static void addAudit(Audit audit) throws CustomBankException {

		HelperUtils.nullCheck(audit);

		completionService.submit(() -> {
			try {
				auditDao.addAudit(List.of(audit));
			} catch (CustomBankException e) {
				throw new ExecutionException("Audit Failed!", e);
			}
			return null;
		});

		completionHandlerExecutor.submit(() -> {
			try {
				Future<Void> future = completionService.take();
				future.get();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});

	}
}
