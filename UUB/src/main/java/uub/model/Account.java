package uub.model;

import java.io.Serializable;

import uub.enums.AccountStatus;
import uub.enums.AccountType;
import uub.enums.Exceptions;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private int accNo;
	private int userId;
	private int branchId;
	private AccountType type;
	private double balance = -1;
	private AccountStatus status;



	@Override
	public String toString() {
		return "\nAccount [accNo=" + accNo + ", userId=" + userId + ", branchId=" + branchId + ", type=" + type
				+ ", balance=" + balance + ", status=" + status  +"]";
	}


	public int getAccNo() {
		return accNo;
	}
	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}

	public void setType(int type) {
		this.type =AccountType.valueOf(type);
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) throws CustomBankException {
		if(balance < 0) {
			throw new CustomBankException(Exceptions.NEGATIVE_BALANCE);
		}
		
		this.balance = HelperUtils.doubleFormat(balance);
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus( AccountStatus status) {
		this.status = status;
	}

	public void setStatus(int status) {
		this.status =AccountStatus.valueOf(status);
	}



}
