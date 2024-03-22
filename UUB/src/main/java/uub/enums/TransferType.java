package uub.enums;

import java.util.HashMap;
import java.util.Map;

public enum TransferType {
    DEPOSIT(0), WITHDRAW(1) , INTER_BANK(2), INTRA_BANK(3);

	private final int type;
	TransferType(int i) {
		this.type = i;
	}
	public int getType() {
		return type;
	}


	private static final Map<Integer, TransferType> valueMap = new HashMap<>();


	static {
		for (TransferType accountStatus : values()) {
			valueMap.put(accountStatus.getType(), accountStatus);
		}
	}

	public static TransferType valueOf(int value) {
		return valueMap.getOrDefault(value, null);
	}



}
