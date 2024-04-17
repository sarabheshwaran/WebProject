package uub.logicallayer;

import java.util.List;

import org.json.JSONObject;

import uub.model.Account;
import uub.model.Customer;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
import uub.staticlayer.HelperUtils;

public class JsonConverter {

	public static JSONObject customerJson(Customer customer) throws CustomBankException {

		JSONObject customerJson = new JSONObject();
		HelperUtils.nullCheck(customer);

		customerJson.put("id", customer.getId());
		customerJson.put("name", customer.getName());
		customerJson.put("email", customer.getEmail());
		customerJson.put("phone", customer.getPhone());
		customerJson.put("dob", customer.getDOB());
		customerJson.put("gender", customer.getGender());

		customerJson.put("userType", customer.getUserType());
		customerJson.put("status", customer.getStatus());
		customerJson.put("aadhar", customer.getAadhar());
		customerJson.put("pan", customer.getPAN());
		customerJson.put("address", customer.getAddress());
		customerJson.put("lastModifiedBy", customer.getLastModifiedBy());
		customerJson.put("lastModifiedTime", customer.getLastModifiedTime());

		return customerJson;

	}

	public static JSONObject accountJson(Account account) throws CustomBankException {

		HelperUtils.nullCheck(account);
		
		JSONObject accountJson = new JSONObject();
		
		accountJson.put("accNo", account.getAccNo());
		accountJson.put("userdId", account.getUserId());
		accountJson.put("branchId", account.getBranchId());
		accountJson.put("type", account.getType());
		accountJson.put("balance", account.getBalance());
		accountJson.put("status", account.getStatus());
		accountJson.put("lastModifiedTime", account.getLastModifiedTime());
		accountJson.put("lastModifiedBy", account.getLastModifiedBy());
		
		return accountJson;
		
	}
	
	public static Customer customerPojo(JSONObject customerJson) throws CustomBankException{
		String name = customerJson.getString("name");
		String email = customerJson.getString("email");
		String phone = customerJson.getString("phone");


		long dateOfBirth = customerJson.getLong("DOB");


		DateUtils.isAdult(dateOfBirth);

		String gender = customerJson.getString("gender");
		String aadhar = customerJson.getString("aadhar");
		String pan = customerJson.getString("PAN");
		String address = customerJson.getString("address");
		String password = customerJson.getString("password");

		Customer customer = new Customer();

		customer.setName(name);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setDOB(dateOfBirth);
		customer.setGender(gender);
		customer.setAadhar(aadhar);
		customer.setPAN(pan);
		customer.setAddress(address);
		customer.setPassword(password);
		
		return customer;
	}

}

//		if(includeFields.contains("userType")) {
//			customerJson.put("userType", customer.getUserType());
//			}
//			
//			if(includeFields.contains("userType")) {
//			customerJson.put("status", customer.getStatus());
//			}
//			
//			if(includeFields.contains("userType")) {
//			customerJson.put("aadhar", customer.getAadhar());
//			}
//			
//			if(includeFields.contains("userType")) {
//			customerJson.put("pan", customer.getPAN());
//			}
//			
//			if(includeFields.contains("userType")) {
//			customerJson.put("address", customer.getAddress());
//			}
//			
//			if(includeFields.contains("userType")) {
//			customerJson.put("lastModifiedBy", customer.getLastModifiedBy());
//			}
//			
//			if(includeFields.contains("userType")) {
//			customerJson.put("lastModifiedTime", customer.getLastModifiedTime());
//			}
//		