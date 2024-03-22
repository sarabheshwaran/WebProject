package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus {
	FAIL(0),SUCCESS(1);

	private final int status;
	TransactionStatus(int i) {
		this.status = i;
	}
	public int getStatus() {
		return status;
	}

	private static final Map<Integer, TransactionStatus> valueMap = new HashMap<>();
	static {
		for (TransactionStatus accountStatus : values()) {
			valueMap.put(accountStatus.getStatus(), accountStatus);
		}
	}

	public static TransactionStatus valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}

}
