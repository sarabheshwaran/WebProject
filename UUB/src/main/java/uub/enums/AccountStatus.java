package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {

	INACTIVE(0), ACTIVE(1);

	private final int status;

	AccountStatus(int i) {
		this.status = i;
	}

	public int getStatus() {
		return status;
	}

	private static final Map<Integer, AccountStatus> valueMap = new HashMap<>();
	static {
		for (AccountStatus accountStatus : values()) {
			valueMap.put(accountStatus.getStatus(), accountStatus);
		}
	}

	public static AccountStatus valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}

}
