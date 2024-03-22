package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {

	SAVINGS(0), SALARY(1);

	private final int type;
	AccountType(int i) {
		this.type = i;
	}
	public int getType() {
		return type;
	}


	private static final Map<Integer, AccountType> valueMap = new HashMap<>();


	static {
		for (AccountType accountStatus : values()) {
			valueMap.put(accountStatus.getType(), accountStatus);
		}
	}

	public static AccountType valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}
}
