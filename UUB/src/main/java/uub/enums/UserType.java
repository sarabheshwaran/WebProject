package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
	CUSTOMER(0),EMPLOYEE(1);

	private final int type;
	UserType(int i) {
		this.type = i;
	}
	public int getType() {
		return type;
	}


	private static final Map<Integer, UserType> valueMap = new HashMap<>();


	static {
		for (UserType accountStatus : values()) {
			valueMap.put(accountStatus.getType(), accountStatus);
		}
	}

	public static UserType valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}
}
