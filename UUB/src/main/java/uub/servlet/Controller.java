package uub.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uub.enums.AuditAction;
import uub.enums.AuditResult;
import uub.enums.EmployeeRole;
import uub.enums.TransferType;
import uub.enums.UserType;
import uub.logicallayer.AdminHelper;
import uub.logicallayer.AuditHelper;
import uub.logicallayer.CustomerHelper;
import uub.logicallayer.EmployeeHelper;
import uub.logicallayer.UserHelper;
import uub.model.Account;
import uub.model.Audit;
import uub.model.Branch;
import uub.model.Customer;
import uub.model.Employee;
import uub.model.Transaction;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.DateUtils;
import uub.staticlayer.HelperUtils;

@WebServlet("/app/*")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();

		switch (path) {

		case "/login": {

			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			break;
		}
		case "/logout": {

			try {
				int id = 0;

				if (request.getSession().getAttribute("empId") != null) {
					id = (int) request.getSession().getAttribute("empId");
				} else {

					id = (int) request.getSession().getAttribute("userId");
				}

				Audit audit = new Audit();
				audit.setUserId(id);
				audit.setTime(DateUtils.getTime());
				audit.setAction(AuditAction.LOGOUT);
				audit.setTargetId(id);
				audit.setResult(AuditResult.SUCCESS);
				AuditHelper.addAudit(audit);
			} catch (CustomBankException e) {
				e.printStackTrace();
			}

			request.getSession(false).invalidate();
			request.getRequestDispatcher("/WEB-INF/logout.jsp").forward(request, response);
			break;
		}
		case "/user/account": {

			int userId = (int) request.getSession().getAttribute("userId");

			try {

				CustomerHelper customerHelper = new CustomerHelper();

				if (request.getParameter("accNo") != null) {
					int accNo = HelperUtils.formatNumber(request.getParameter("accNo"));

					Account account = customerHelper.getAccount(accNo);

					Branch branch = customerHelper.getBranch(account.getBranchId());

					request.setAttribute("branch", branch);
					request.setAttribute("account", account);
					request.setAttribute("action", "single");
				}

				else {
					Map<Integer, Account> accounts = customerHelper.getAccounts(userId);

					request.setAttribute("accountMap", accounts);
				}

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				e.printStackTrace();
			} finally {
				request.setAttribute("type", "customer");
				request.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(request, response);

			}
			break;
		}
		case "/user/profile": {

			int userId = (int) request.getSession().getAttribute("userId");

			try {

				if (request.getParameter("edit") != null) {

					request.setAttribute("edit", "true");

					if (request.getParameter("done") != null) {
						request.setAttribute("done", "true");
					} else {

						String error = request.getParameter("error");
						request.setAttribute("error", error);
					}

				} else {
					CustomerHelper customerHelper = new CustomerHelper();

					Customer customer = customerHelper.getCustomer(userId);

					request.setAttribute("profile", customer);
				}

				request.setAttribute("type", "customer");
				request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
			} catch (CustomBankException e) {
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				response.sendRedirect("app/login");
			}

			break;
		}
		case "/employee/manageAccounts": {

			String action = request.getParameter("action");

			EmployeeRole role = (EmployeeRole) request.getSession().getAttribute("role");

			if (role == EmployeeRole.ADMIN) {

				request.setAttribute("role", "admin");

			} else {

				request.setAttribute("role", "employee");
			}

			request.setAttribute("action", action);
			request.setAttribute("type", "employee");

			request.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(request, response);
			break;
		}
		case "/employee/account": {

			try {
				int accNo = HelperUtils.formatNumber(request.getParameter("accNo"));

				EmployeeRole role = (EmployeeRole) request.getSession().getAttribute("role");

				Account account;

				if (role == EmployeeRole.ADMIN) {
					AdminHelper adminHelper = new AdminHelper();

					account = adminHelper.getAccount(accNo);

				} else {

					int branchId = (int) request.getSession().getAttribute("branchId");

					EmployeeHelper employeeHelper = new EmployeeHelper();

					account = employeeHelper.getAccounts(accNo, branchId);

				}
				CustomerHelper customerHelper = new CustomerHelper();
				Branch branch = customerHelper.getBranch(account.getBranchId());

				request.setAttribute("branch", branch);
				request.setAttribute("account", account);

				request.setAttribute("action", "view");

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.setAttribute("action", "search");
			} finally {
				request.setAttribute("type", "employee");
				request.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(request, response);

			}
			break;
		}

		case "/employee/customerAccounts": {

			try {
				int customerId = HelperUtils.formatNumber(request.getParameter("customerId"));

				CustomerHelper customerHelper = new CustomerHelper();

				Map<Integer, Account> accountMap = customerHelper.getAccounts(customerId);

				request.setAttribute("accountMap", accountMap);
				request.setAttribute("type", "employee");
				request.setAttribute("action", "customer");

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
			} finally {
				request.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(request, response);

			}
			break;
		}
		case "/employee/history": {

			request.setAttribute("type", "employee");
			request.getRequestDispatcher("/WEB-INF/history.jsp").forward(request, response);

			break;

		}

		case "/employee/manageCustomers": {

			String action = request.getParameter("action");

			request.setAttribute("action", action);

			request.setAttribute("type", "customer");
			request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
			break;
		}
		case "/employee/customer": {

			String error = request.getParameter("error");
			String edit = request.getParameter("edit");
			try {

				int customerId = HelperUtils.formatNumber(request.getParameter("customerId"));

				EmployeeHelper employeeHelper = new EmployeeHelper();

				Customer customer = employeeHelper.getCustomer(customerId);

				request.setAttribute("profile", customer);
				request.setAttribute("error", error);
				request.setAttribute("action", edit != null ? "edit" : "view");

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.setAttribute("action", "search");
			} finally {
				request.setAttribute("type", "customer");
				request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

			}
			break;
		}
		case "/employee/manageEmployees": {

			checkAccess(request, response);
			String action = request.getParameter("action");

			request.setAttribute("action", action);

			request.setAttribute("type", "employee");
			request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
			break;
		}
		case "/employee/employee": {

			checkAccess(request, response);
			String error = request.getParameter("error");
			String edit = request.getParameter("edit");
			try {

				int empId = HelperUtils.formatNumber(request.getParameter("employeeId"));

				AdminHelper adminHelper = new AdminHelper();

				Employee employee = adminHelper.getEmployee(empId);

				request.setAttribute("profile", employee);
				request.setAttribute("action", "view");
				request.setAttribute("error", error);
				request.setAttribute("action", edit != null ? "edit" : "view");

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.setAttribute("action", "search");
			} finally {
				request.setAttribute("type", "employee");
				request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

			}
			break;
		}
		case "/user/transaction": {

			try {

				int id = (int) request.getSession().getAttribute("userId");

				CustomerHelper customerHelper = new CustomerHelper();

				List<Integer> accNos = new ArrayList<Integer>(customerHelper.getAccounts(id).keySet());

				request.setAttribute("accNos", accNos);

				String type = request.getParameter("type");

				if (type == null) {
					request.setAttribute("type", "interbank");
				} else {
					request.setAttribute("type", type);
				}

				request.getRequestDispatcher("/WEB-INF/transaction.jsp").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}

			break;
		}
		case "/user/history": {

			try {
				int id = (int) request.getSession().getAttribute("userId");

				CustomerHelper customerHelper = new CustomerHelper();

				List<Integer> accNos = new ArrayList<Integer>(customerHelper.getAccounts(id).keySet());

				request.setAttribute("accNos", accNos);
				request.setAttribute("type", "customer");
				request.getRequestDispatcher("/WEB-INF/history.jsp").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}
			break;

		}
		case "/employee/profile": {

			int empId = (int) request.getSession().getAttribute("empId");

			try {

				if (request.getParameter("edit") != null) {

					request.setAttribute("edit", "true");

					if (request.getParameter("done") != null) {
						request.setAttribute("done", "true");
					} else {

						String error = request.getParameter("error");
						request.setAttribute("error", error);
					}

				} else {
					EmployeeHelper employeeHelper = new EmployeeHelper();

					Employee employee = employeeHelper.getEmployee(empId);

					request.setAttribute("profile", employee);
				}

				request.setAttribute("type", "employee");
				request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect("app/login");
			}

			break;
		}
		
		case "/employee/apiPage": {

			int empId = (int) request.getSession().getAttribute("empId");

			try {

				if (request.getParameter("edit") != null) {

					request.setAttribute("edit", "true");

					if (request.getParameter("done") != null) {
						request.setAttribute("done", "true");
					} else {

						String error = request.getParameter("error");
						request.setAttribute("error", error);
					}

				} else {
					EmployeeHelper employeeHelper = new EmployeeHelper();

					Employee employee = employeeHelper.getEmployee(empId);

					request.setAttribute("profile", employee);
				}

				request.setAttribute("type", "employee");
				request.getRequestDispatcher("/WEB-INF/apiPage.jsp").forward(request, response);
			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect("app/login");
			}

			break;
		}

		default: {

			request.getRequestDispatcher("/WEB-INF/404.jsp").forward(request, response);
		}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		Map<String, String[]> paramMap = request.getParameterMap();

		System.out.println("Request Parameters:");
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String paramName = entry.getKey();
			String[] paramValues = entry.getValue();
			System.out.print(paramName + ": ");
			for (String paramValue : paramValues) {
				System.out.print(paramValue + " ");
			}
		}

		switch (pathInfo) {

		case "/": {

		}

		case "/login": {

			Audit audit = new Audit();
			try {

				int userId = HelperUtils.formatNumber(request.getParameter("userId"));
				
				audit.setUserId(userId);
				audit.setTime(DateUtils.getTime());
				audit.setAction(AuditAction.LOGIN);
				audit.setTargetId(userId);
				audit.setResult(AuditResult.SUCCESS);

				String password = request.getParameter("password");

				UserHelper userHelper = new UserHelper();

				UserType type = userHelper.login(userId, password);

				switch (type) {
				case CUSTOMER: {
					request.getSession().setAttribute("userId", userId);
					response.sendRedirect(request.getContextPath() + "/app/user/account");
					break;
				}
				case EMPLOYEE: {

					EmployeeHelper employeeHelper = new EmployeeHelper();

					Employee employee = employeeHelper.getEmployee(userId);

					request.getSession().setAttribute("role", employee.getRole());
					request.getSession().setAttribute("branchId", employee.getBranchId());
					request.getSession().setAttribute("empId", userId);
					response.sendRedirect(request.getContextPath() + "/app/employee/profile");
					break;
				}
				default:
					break;
				}

			} catch (CustomBankException e) {

				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			} finally {

				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
			}
			break;
		}

		case "/logout": {

			doGet(request, response);
		}

		case "/user/transaction": {

			int id = (int) request.getSession().getAttribute("userId");

			String password = request.getParameter("password");
			String type = request.getParameter("type");
			String desc = request.getParameter("desc");

			Audit audit = new Audit();
			Audit audit2 = null;
			audit.setUserId(id);

			try {
				int accNo = HelperUtils.formatNumber(request.getParameter("senderAccount"));
				double amount = HelperUtils.formatDouble(request.getParameter("amount"));

				audit.setTime(DateUtils.getTime());

				Transaction transaction = new Transaction();

				transaction.setUserId(id);
				transaction.setAccNo(accNo);
				transaction.setDesc(desc);
				transaction.setAmount(amount);
				transaction.setLastModifiedBy(id);

				audit.setTargetId(accNo);

				CustomerHelper customerHelper = new CustomerHelper();

				switch (type) {
				case "deposit": {
					audit.setAction(AuditAction.DEPOSIT);
					transaction.setType(TransferType.DEPOSIT);
					break;
				}
				case "withdraw": {

					audit.setAction(AuditAction.WITHDRAW);
					transaction.setType(TransferType.WITHDRAW);
					break;
				}
				case "interbank": {

					audit.setAction(AuditAction.TRANSACTION);
					audit.setDesc("interbank");
					audit2 = new Audit();
					audit2.setUserId(id);
					audit2.setTime(DateUtils.getTime());
					audit2.setAction(AuditAction.TRANSACTION);
					audit2.setDesc("interbank");
					audit2.setResult(AuditResult.SUCCESS);
					int transactionAcc = HelperUtils.formatNumber(request.getParameter("receiverAccount"));
					transaction.setTransactionAcc(transactionAcc);
					transaction.setType(TransferType.INTER_BANK);
					audit2.setTargetId(transactionAcc);
					break;
				}
				case "intrabank": {

					audit.setAction(AuditAction.TRANSACTION);
					audit.setDesc("intrabank");
					audit2 = new Audit();
					audit2.setUserId(id);
					audit2.setTime(DateUtils.getTime());
					audit2.setAction(AuditAction.TRANSACTION);
					audit2.setDesc("intrabank");
					audit2.setResult(AuditResult.SUCCESS);
					int transactionAcc = HelperUtils.formatNumber(request.getParameter("receiverAccount"));
					transaction.setTransactionAcc(transactionAcc);
					transaction.setType(TransferType.INTRA_BANK);
					audit2.setTargetId(transactionAcc);
					break;
				}
				default:
					break;
				}

				customerHelper.makeTransaction(transaction, password);
				audit.setResult(AuditResult.SUCCESS);

				request.setAttribute("type", type);
				request.setAttribute("done", true);
				request.getRequestDispatcher("/WEB-INF/transaction.jsp").forward(request, response);

			} catch (CustomBankException e) {
				e.printStackTrace();
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				request.setAttribute("type", type);
				request.setAttribute("error", e.getFullMessage());
				doGet(request, response);
			} finally {
				try {
					AuditHelper.addAudit(audit);
					if (audit2 != null) {
						AuditHelper.addAudit(audit2);
					}
				} catch (CustomBankException e) {
					e.printStackTrace();
				}

			}

			break;
		}
		case "/user/changePassword": {

			int userId = (int) request.getSession().getAttribute("userId");

			Audit audit = new Audit();
			audit.setUserId(userId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.CHANGE_PASSWORD);
			audit.setTargetId(userId);

			try {
				UserHelper userHelper = new UserHelper();

				String oldPassword = request.getParameter("oldPassword");
				String newPassword = request.getParameter("newPassword");
				String repeatPassword = request.getParameter("repeatPassword");

				userHelper.changePassword(userId, oldPassword, newPassword, repeatPassword);
				audit.setResult(AuditResult.SUCCESS);
				response.sendRedirect(request.getContextPath() + "/app/user/profile?edit=true&done=true");

			} catch (CustomBankException e) {
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				response.sendRedirect(
						request.getContextPath() + "/app/user/profile?edit=true&error=" + e.getFullMessage());
			} finally {

				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
			}

			break;
		}
		case "/employee/changePassword": {

			int userId = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(userId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.CHANGE_PASSWORD);
			audit.setTargetId(userId);
			try {
				UserHelper userHelper = new UserHelper();

				String oldPassword = request.getParameter("oldPassword");
				String newPassword = request.getParameter("newPassword");
				String repeatPassword = request.getParameter("repeatPassword");

				userHelper.changePassword(userId, oldPassword, newPassword, repeatPassword);
				audit.setResult(AuditResult.SUCCESS);

				response.sendRedirect(request.getContextPath() + "/app/employee/profile?edit=true&done=true");
			} catch (CustomBankException e) {
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				response.sendRedirect(
						request.getContextPath() + "/app/employee/profile?edit=true&error=" + e.getFullMessage());
			} finally {

				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
			}

			break;
		}

		case "/user/history": {

			try {
				request.setAttribute("type", "customer");

				historyPage(request);

				request.getRequestDispatcher("/WEB-INF/history.jsp").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect(request.getContextPath() + "/app/login");
			}
			break;
		}
		case "/employee/history": {

			try {
				request.setAttribute("type", "employee");

				historyPage(request);
				
				request.getRequestDispatcher("/WEB-INF/history.jsp").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect(request.getContextPath() + "/app/login");
			}
			break;
		}

		case "/employee/editAccount": {

			int empId = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(empId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.ACCOUNT_UPDATE);
			int accNo = 0;
			try {
				accNo = HelperUtils.formatNumber(request.getParameter("accNo"));

				audit.setTargetId(accNo);
				String action = request.getParameter("action");

				EmployeeHelper employeeHelper = new EmployeeHelper();

				switch (action) {

				case "Activate": {

					employeeHelper.activateAcc(accNo);
					audit.setDesc("Account Activated !");

					break;
				}
				case "Deactivate": {

					employeeHelper.deActivateAcc(accNo);
					audit.setDesc("Account Deactivated !");

					break;
				}

				}

				audit.setResult(AuditResult.SUCCESS);
				request.setAttribute("type", "employee");
				response.sendRedirect(request.getContextPath() + "/app/employee/account?accNo=" + accNo);

			} catch (CustomBankException e) {
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/app/employee/account?accNo=" + accNo).forward(request, response);

			} finally {

				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
			}
			break;
		}

		case "/employee/createAcc": {
			int empId = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(empId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.ACCOUNT_ADD);

			try {
				int userId = HelperUtils.formatNumber(request.getParameter("userId"));

				int branchId = HelperUtils.formatNumber(request.getParameter("branchId"));

				int type = HelperUtils.formatNumber(request.getParameter("type"));

				double balance = HelperUtils.formatDouble(request.getParameter("balance"));

				Account account = new Account();

				account.setUserId(userId);
				account.setBranchId(branchId);
				account.setType(type);
				account.setBalance(balance);
				account.setLastModifiedBy(empId);

				EmployeeHelper employeeHelper = new EmployeeHelper();

				employeeHelper.addAccount(account);
				
				audit.setTargetId(userId);
				audit.setResult(AuditResult.SUCCESS);
				request.setAttribute("success", "true");

			} catch (CustomBankException e) {

				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				request.setAttribute("error", e.getFullMessage());

			} finally {
				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
				request.setAttribute("type", "employee");
				request.setAttribute("action", "create");
				request.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(request, response);

			}

			break;
		}

		case "/employee/editCustomer": {
			

			int empId = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(empId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.USER_UPDATE);

			int customerId = 0;
			try {

				customerId = HelperUtils.formatNumber(request.getParameter("customerId"));

				EmployeeHelper employeeHelper = new EmployeeHelper();

				String status = request.getParameter("status");
				audit.setTargetId(customerId);

				if (status != null) {
					switch (status) {

					case "Activate": {

						employeeHelper.activateUser(customerId,empId);
						audit.setDesc("Customer Activated");

						break;
					}
					case "Deactivate": {

						employeeHelper.deActivateUser(customerId,empId);
						audit.setDesc("Customer Deactivated");

						break;
					}

					}
				} else {

					Customer customer = mapCustomer(request);
					
					customer.setLastModifiedBy(empId);
					employeeHelper.editCustomer(customerId, customer);
					
				}
				
				audit.setResult(AuditResult.SUCCESS);
				response.sendRedirect(request.getContextPath() + "/app/employee/customer?customerId=" + customerId);

			} catch (CustomBankException e) {
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				response.sendRedirect(request.getContextPath() + "/app/employee/customer?edit=true&customerId="
						+ customerId + "&error=" + e.getFullMessage());

			}finally {

				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
			}
			break;
		}

		case "/employee/createCustomer": {

			int empId = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(empId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.USER_ADD);

			
			try {
				Customer customer = mapCustomer(request);
				customer.setLastModifiedBy(empId);
				audit.setDesc("Customer Creation");
				
				
				EmployeeHelper employeeHelper = new EmployeeHelper();

				employeeHelper.addCustomer(customer);

				audit.setResult(AuditResult.SUCCESS);
				request.setAttribute("success", "true");

			} catch (CustomBankException e) {

				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				request.setAttribute("error", e.getFullMessage());
			} finally {
				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
				request.setAttribute("action", "create");
				request.setAttribute("type", "customer");
				request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

			}

			break;
		}


		

		case "/employee/editEmployee": {

			checkAccess(request, response);
			
			int id = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(id);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.USER_UPDATE);

			
			int empId = 0;
			try {

				empId = HelperUtils.formatNumber(request.getParameter("employeeId"));

				audit.setTargetId(empId);
				
				AdminHelper adminHelper = new AdminHelper();

				String status = request.getParameter("status");

				if (status != null) {
					switch (status) {

					case "Activate": {
						
						audit.setDesc("Employee Activated");
						adminHelper.activateUser(empId,id);

						break;
					}
					case "Deactivate": {

						audit.setDesc("Employee Deactivated");
						adminHelper.deActivateUser(empId,id);

						break;
					}

					}
				} else {

					Employee employee = mapEmployee(request);
					employee.setLastModifiedBy(id);
					adminHelper.editEmployee(empId, employee);
				}

				audit.setResult(AuditResult.SUCCESS);
				
				response.sendRedirect(request.getContextPath() + "/app/employee/employee?employeeId=" + empId);

			} catch (CustomBankException e) {
				
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				response.sendRedirect(request.getContextPath() + "/app/employee/employee?edit=true&employeeId=" + empId
						+ "&error=" + e.getFullMessage());

			} finally {
				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
			}
			break;
		}

		case "/employee/createEmployee": {

			checkAccess(request, response);
			
			int empId = (int) request.getSession().getAttribute("empId");

			Audit audit = new Audit();
			audit.setUserId(empId);
			audit.setTime(DateUtils.getTime());
			audit.setAction(AuditAction.USER_ADD);

			
			try {
				Employee employee = mapEmployee(request);
				employee.setLastModifiedBy(empId);
				audit.setDesc("Employee Creation");

				AdminHelper adminHelper = new AdminHelper();

				adminHelper.addEmployee(employee);

				audit.setResult(AuditResult.SUCCESS);
				request.setAttribute("success", "true");

			} catch (CustomBankException e) {
				e.printStackTrace();
				audit.setResult(AuditResult.FAILURE);
				audit.setDesc(e.getFullMessage());
				request.setAttribute("error", e.getFullMessage());
			} finally {
				try {
					AuditHelper.addAudit(audit);
				} catch (CustomBankException e) {
					e.printStackTrace();
				}
				request.setAttribute("action", "create");
				request.setAttribute("type", "employee");
				request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

			}

			break;
		}
		case "/user/createApiAuth":
		case "/employee/createApiAuth":{

			
			
		break;
		}
		
		}
	}

	private void historyPage(HttpServletRequest request) throws CustomBankException {

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
		if(request.getParameter("pageNo") != null) {
			pageNo =  HelperUtils.formatNumber(request.getParameter("pageNo"));
		}
		
		CustomerHelper customerHelper = new CustomerHelper();

		List<Transaction> transactions = customerHelper.getTransaction(accNo, from, to, 10, pageNo);

		int transactionCount = customerHelper.getTransactionCount(accNo, from, to, 10, pageNo);
		int pageCount = (int) Math.ceil(((double) transactionCount) / 10);

		request.setAttribute("transactions", transactions);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNo", pageNo);
		
		try {
			if(pageNo == 0) {
			AuditHelper.addAudit(audit);
			}
		} catch (CustomBankException e) {
			e.printStackTrace();
		}
	}
	

	private void checkAccess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EmployeeRole role = (EmployeeRole) request.getSession().getAttribute("role");

		if (!role.equals(EmployeeRole.ADMIN)) {
			request.getRequestDispatcher("/WEB-INF/401.jsp").forward(request, response);
		}
	}

	private Customer mapCustomer(HttpServletRequest request) throws CustomBankException {

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

	private Employee mapEmployee(HttpServletRequest request) throws CustomBankException {

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
