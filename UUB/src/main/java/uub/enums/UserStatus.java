package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
	INACTIVE(0),ACTIVE(1);

	private final int status;
	UserStatus(int i) {
		this.status = i;
	}
	public int getStatus() {
		return status;
	}


	private static final Map<Integer, UserStatus> valueMap = new HashMap<>();


	static {
		for (UserStatus accountStatus : values()) {
			valueMap.put(accountStatus.getStatus(), accountStatus);
		}
	}

	public static UserStatus valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}
}
