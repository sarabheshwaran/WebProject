package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeRole {

	STAFF(0),ADMIN(1);

	private final int role;
	EmployeeRole(int i) {
		this.role = i;
	}
	public int getRole() {
		return role;
	}


	private static final Map<Integer, EmployeeRole> valueMap = new HashMap<>();


	static {
		for (EmployeeRole accountStatus : values()) {
			valueMap.put(accountStatus.getRole(), accountStatus);
		}
	}

	public static EmployeeRole valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}
}

