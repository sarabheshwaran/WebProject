package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum AuditAction {

	ACCOUNT_UPDATE("Account Update"),
	ACCOUNT_ADD("Account Creation"),
	USER_ADD("User Creation"),
	USER_UPDATE("User Update"),
	LOGIN("Login"),
	LOGOUT("Logout"),
	STATEMENT_QUERY("Statement Query"),
	WITHDRAW("Withdraw"),
	DEPOSIT("Deposit"),
	TRANSACTION("Transaction"),
	CHANGE_PASSWORD("Change Password"),
	ACCOUNT_STATEMENT("Account statement enquiry")
	;

	private final String action;
	AuditAction(String i) {
		this.action = i;
	}
	public String getAction() {
		return action;
	}


	private static final Map<String, AuditAction> valueMap = new HashMap<>();


	static {
		for (AuditAction auditAction : values()) {
			valueMap.put(auditAction.getAction(), auditAction);
		}
	}

	public static AuditAction setAction(String value) {
		return valueMap.getOrDefault(value, null);
	}
	
}
