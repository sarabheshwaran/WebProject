package uub.model;

import uub.enums.EmployeeRole;
import uub.enums.UserStatus;
import uub.enums.UserType;
import uub.staticlayer.CustomBankException;

public class Employee extends User{


	private static final long serialVersionUID = 1L;
	private EmployeeRole role;
	private int branchId;


	public Employee() {
		super();
		super.setUserType(UserType.EMPLOYEE);
	}


	public Employee(int id, String name, String email, String phone, long dOB, String gender, String password,
			UserType userType, UserStatus status,EmployeeRole role, int branchId) {
		super(id, name, email, phone, dOB, gender, password, userType, status);

		this.branchId= branchId;
		this.role = role;
	}


	@Override
	public String toString() {
		return  "Employee ["+ toString(0) +", role=" + role + ", branchId=" + branchId + "]";
	}


	public EmployeeRole getRole() {
		return role;
	}
	public void setRole(int role) throws CustomBankException {
		this.role = EmployeeRole.valueOf(role);
	}
	public void setRole(EmployeeRole role) {
		this.role = role;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}


}
