package uub.model;


import uub.enums.UserStatus;
import uub.enums.UserType;

public class Customer extends User{

	private static final long serialVersionUID = 1L;
	private String aadhar;
	private String pan;
	private String address;



	public Customer() {
		super.setUserType(UserType.CUSTOMER);

	}

	public Customer(int id, String name, String email, String phone, long dOB, String gender, String password,
			UserType userType, UserStatus status, String aadhar, String pan, String address) {
		super(id, name, email, phone, dOB, gender, password, userType, status);
		this.aadhar = aadhar;
		this.pan = pan;
		this.address = address;

	}

	@Override
	public String toString() {
		return "\nCustomer ["+ toString(0) +", aadhar=" + aadhar + ", pAN=" + pan + ", address=" + address + "]\n";
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getPAN() {
		return pan;
	}
	public void setPAN(String pAN) {
		this.pan = pAN;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}




}
