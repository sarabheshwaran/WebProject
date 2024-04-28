package uub.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uub.enums.AuditAction;
import uub.enums.AuditResult;
import uub.enums.EmployeeRole;
import uub.logicallayer.AuditHelper;
import uub.logicallayer.CustomerHelper;
import uub.model.Audit;
import uub.model.Customer;
import uub.model.Employee;
import uub.model.Transaction;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
import uub.staticlayer.HelperUtils;

public class ServletHelper {


	public static  void historyPage(HttpServletRequest request) throws CustomBankException {

		int id = 0;

		if (request.getSession().getAttribute("empId") != null) {
			id = (int) request.getSession().getAttribute("empId");
		} else {

			id = (int) request.getSession().getAttribute("userId");
		}

		Audit audit = new Audit();
		audit.setAction(AuditAction.ACCOUNT_STATEMENT);
		audit.setUserId(id);
		audit.setTime(DateUtils.getTime());
		audit.setResult(AuditResult.SUCCESS);

		int accNo = HelperUtils.formatNumber(request.getParameter("accNo"));

		audit.setTargetId(accNo);

		String from = request.getParameter("startDate");
		String to = request.getParameter("endDate");

		int pageNo = 0;
		if (request.getParameter("pageNo") != null) {
			pageNo = HelperUtils.formatNumber(request.getParameter("pageNo"));
		}

		CustomerHelper customerHelper = new CustomerHelper();

		List<Transaction> transactions = customerHelper.getTransaction(accNo, from, to, 10, pageNo);

		int transactionCount = customerHelper.getTransactionCount(accNo, from, to, 10, pageNo);
		int pageCount = (int) Math.ceil(((double) transactionCount) / 10);

		request.setAttribute("transactions", transactions);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNo", pageNo);

		try {
			if (pageNo == 0) {
				AuditHelper.addAudit(audit);
			}
		} catch (CustomBankException e) {
			e.printStackTrace();
		}
	}

	public static  boolean checkAccess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EmployeeRole role = (EmployeeRole) request.getSession().getAttribute("role");

		return role.equals(EmployeeRole.ADMIN);
	}

	public static  Customer mapCustomer(HttpServletRequest request) throws CustomBankException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		String dob = request.getParameter("dob");

		long dateOfBirth = 0l;
		if (dob != null) {
			dateOfBirth = DateUtils.formatDate(DateUtils.formatDateString(dob));
		}

		DateUtils.isAdult(dateOfBirth);

		String gender = request.getParameter("gender");
		String aadhar = request.getParameter("aadhar");
		String pan = request.getParameter("pan");
		String address = request.getParameter("address");
		String password = request.getParameter("password");

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

	public static  Employee mapEmployee(HttpServletRequest request) throws CustomBankException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		String dob = request.getParameter("dob");

		long dateOfBirth = 0l;
		if (dob != null) {
			dateOfBirth = DateUtils.formatDate(DateUtils.formatDateString(dob));
		}

		DateUtils.isAdult(dateOfBirth);

		String gender = request.getParameter("gender");
		String password = request.getParameter("password");

		String roleString = request.getParameter("role");

		int role = 0;
		if (roleString != null) {
			role = HelperUtils.formatNumber(roleString);
		}

		String branch = request.getParameter("branchId");

		int branchId = 0;
		if (branch != null) {
			branchId = HelperUtils.formatNumber(branch);
		}

		Employee employee = new Employee();

		employee.setName(name);
		employee.setEmail(email);
		employee.setPhone(phone);
		employee.setDOB(dateOfBirth);
		employee.setGender(gender);
		employee.setRole(role);
		employee.setBranchId(branchId);
		employee.setPassword(password);

		return employee;
	}
	
}
