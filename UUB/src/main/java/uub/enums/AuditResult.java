package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum AuditResult {
	FAILURE(0), SUCCESS(1);

	private final int status;

	AuditResult(int i) {
		this.status = i;
	}

	public int getResult() {
		return status;
	}

	private static final Map<Integer, AuditResult> valueMap = new HashMap<>();
	static {
		for (AuditResult accountStatus : values()) {
			valueMap.put(accountStatus.getResult(), accountStatus);
		}
	}

	public static AuditResult valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}

}
