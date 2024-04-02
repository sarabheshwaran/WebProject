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

import uub.enums.TransferType;
import uub.enums.UserType;
import uub.logicallayer.AdminHelper;
import uub.logicallayer.CustomerHelper;
import uub.logicallayer.EmployeeHelper;
import uub.logicallayer.UserHelper;
import uub.model.Account;
import uub.model.Customer;
import uub.model.Employee;
import uub.model.Transaction;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

@WebServlet("/app/*")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();

		switch (path) {

		case "/login": {

			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			break;
		}
		case "/logout": {

			request.getSession().setAttribute("empId", null);
			request.getSession().setAttribute("userId", null);
			request.getSession().setAttribute("type", null);
			request.getRequestDispatcher("/WEB-INF/logout.jsp").forward(request, response);
			break;
		}
		case "/user/accounts": {

			int userId = (int) request.getSession().getAttribute("userId");

			try {
				CustomerHelper customerHelper = new CustomerHelper();

				Map<Integer, Account> accounts = customerHelper.getAccounts(userId);
				Customer customer = customerHelper.getCustomer(userId);

				request.setAttribute("profile", customer);
				request.setAttribute("accountMap", accounts);

				request.getRequestDispatcher("/WEB-INF/user/accounts.jsp").forward(request, response);
			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect("app/login");
			}
			break;
		}
		case "/user/profile": {

			int userId = (int) request.getSession().getAttribute("userId");

			try {
				CustomerHelper customerHelper = new CustomerHelper();

				Customer customer = customerHelper.getCustomer(userId);

				request.setAttribute("profile", customer);

				request.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(request, response);
			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect("app/login");
			}

			break;
		}
		case "/employee/accounts": {

			request.getRequestDispatcher("/WEB-INF/employee/accounts.jsp").forward(request, response);

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

				request.getRequestDispatcher("/WEB-INF/user/transaction.jsp").forward(request, response);

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

				request.getRequestDispatcher("/WEB-INF/user/history.jsp").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}
			break;

		}
		case "/employee/profile": {

			int empId = (int) request.getSession().getAttribute("empId");

			try {
				EmployeeHelper employeeHelper = new EmployeeHelper();

				Employee employee = employeeHelper.getEmployee(empId);

				request.setAttribute("profile", employee);

				request.getRequestDispatcher("/WEB-INF/employee/profile.jsp").forward(request, response);
			} catch (CustomBankException e) {
				request.setAttribute("error", e.getMessage());
				response.sendRedirect("app/login");
			}

			break;
		}

		default: {

			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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

			try {

				int userId = HelperUtils.formatNumber(request.getParameter("userId"));

				String password = request.getParameter("password");

				UserHelper userHelper = new UserHelper();

				UserType type = userHelper.login(userId, password);

//					request.getSession().setMaxInactiveInterval(100);

				switch (type) {
				case CUSTOMER: {

					request.getSession().setAttribute("userId", userId);
					response.sendRedirect(request.getContextPath() + "/app/user/accounts");
					break;
				}
				case EMPLOYEE: {

					request.getSession().setAttribute("empId", userId);
					response.sendRedirect(request.getContextPath() + "/app/employee/profile");
					break;
				}
				default:
					break;
				}

			} catch (CustomBankException e) {

				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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

			try {
				int accNo = HelperUtils.formatNumber(request.getParameter("senderAccount"));
				double amount = HelperUtils.formatDouble(request.getParameter("amount"));

				Transaction transaction = new Transaction();

				transaction.setUserId(id);
				transaction.setAccNo(accNo);
				transaction.setDesc(desc);
				transaction.setAmount(amount);

				CustomerHelper customerHelper = new CustomerHelper();

				switch (type) {
				case "deposit": {

					transaction.setType(TransferType.DEPOSIT);
					break;
				}
				case "withdraw": {

					transaction.setType(TransferType.WITHDRAW);
					break;
				}
				case "interbank": {

					int transactionAcc = HelperUtils.formatNumber(request.getParameter("receiverAccount"));
					transaction.setTransactionAcc(transactionAcc);
					transaction.setType(TransferType.INTER_BANK);
					break;
				}
				case "intrabank": {

					int transactionAcc = HelperUtils.formatNumber(request.getParameter("receiverAccount"));
					transaction.setTransactionAcc(transactionAcc);
					transaction.setType(TransferType.INTRA_BANK);
					break;
				}
				default:
					break;
				}

				customerHelper.makeTransaction(transaction, password);

				request.setAttribute("type", type);
				request.setAttribute("done", true);
				request.getRequestDispatcher("/WEB-INF/user/transaction.jsp").forward(request, response);

			} catch (CustomBankException e) {

				request.setAttribute("type", type);
				request.setAttribute("error", e.getFullMessage());
				doGet(request, response);
			}

			break;
		}
		case "/user/changePassword":
		case "/employee/changePassword": {

			int userId = (int) request.getSession().getAttribute("userId");

			try {
				UserHelper userHelper = new UserHelper();

				String oldPassword = request.getParameter("oldPassword");
				String newPassword = request.getParameter("newPassword");
				String repeatPassword = request.getParameter("repeatPassword");

				userHelper.changePassword(userId, oldPassword, newPassword, repeatPassword);

				request.setAttribute("edit", true);
				request.setAttribute("done", true);

				request.getRequestDispatcher("/app/user/profile").forward(request, response);
			} catch (CustomBankException e) {
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				response.sendRedirect(request.getContextPath() + "/app/login");
			}

			break;
		}
		case "/user/history": {

			try {

				int accNo = HelperUtils.formatNumber(request.getParameter("accNo"));

				String from = request.getParameter("startDate");
				String to = request.getParameter("endDate");

				int pageNo = HelperUtils.formatNumber(request.getParameter("pageNo"));

				CustomerHelper customerHelper = new CustomerHelper();

				List<Transaction> transactions = customerHelper.getTransaction(accNo, from, to, 10, pageNo);

				int transactionCount = customerHelper.getTransactionCount(accNo, from, to, 10, pageNo);
				int pageCount = (int) Math.ceil(((double) transactionCount) / 10);

				request.setAttribute("transactions", transactions);
				request.setAttribute("pageCount", pageCount);
				request.setAttribute("pageNo", pageNo);

				request.getRequestDispatcher("/WEB-INF/user/history.jsp").forward(request, response);

			} catch (CustomBankException e) {
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				response.sendRedirect(request.getContextPath() + "/app/login");
			}
			break;
		}
		case "/employee/accounts": {
			try {

				if (request.getParameter("accNo") != null) {

					System.out.println("this this = " + request.getParameter("accNo"));

					int accNo = HelperUtils.formatNumber(request.getParameter("accNo"));

					EmployeeHelper employeeHelper = new EmployeeHelper();
					Account account = employeeHelper.getAccount(accNo);

					request.setAttribute("account", account);
				}

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
			}
			request.getRequestDispatcher("/WEB-INF/employee/accounts.jsp").forward(request, response);

			break;
		}
		case "/employee/accountSearch": {

			try {

				String branch_Id = request.getParameter("branchId");
				String acc_No = request.getParameter("accNo");
				String customer_Id = request.getParameter("customerId");

				if (branch_Id != null) {

					int branchId = HelperUtils.formatNumber(branch_Id);

					EmployeeHelper employeeHelper = new EmployeeHelper();

					if (acc_No == null) {

						int customerId = HelperUtils.formatNumber(customer_Id);

						Map<Integer, Account> accounts = employeeHelper.getAccounts(customerId);

						List<Account> accountList = new ArrayList<Account>(accounts.values());

						if (accountList.size() == 1) {
							request.getRequestDispatcher("accounts?accNo=" + accountList.get(0).getAccNo())
									.forward(request, response);

						}

						request.setAttribute("accounts", accountList);

					} else {

						int accNo = HelperUtils.formatNumber(acc_No);

						Account account = employeeHelper.getAccount(accNo);

						request.getRequestDispatcher("accounts?accNo=" + account.getAccNo()).forward(request, response);

					}

				} else {

					EmployeeHelper employeeHelper = new EmployeeHelper();

					if (acc_No == null) {

						int customerId = HelperUtils.formatNumber(customer_Id);

						Map<Integer, Account> accounts = employeeHelper.getAccounts(customerId);

						List<Account> accountList = new ArrayList<Account>(accounts.values());

						if (accountList.size() == 1) {
							request.getRequestDispatcher("accounts?accNo=" + accountList.get(0).getAccNo())
									.forward(request, response);

						}

						request.setAttribute("accounts", accountList);

					} else {

						int accNo = HelperUtils.formatNumber(acc_No);

						Account account = employeeHelper.getAccount(accNo);

						request.getRequestDispatcher("accounts?accNo=" + account.getAccNo()).forward(request, response);

					}

				}

				request.getRequestDispatcher("/WEB-INF/employee/accounts.jsp").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("/WEB-INF/employee/accounts.jsp").forward(request, response);

			}

			break;
		}
		case "/employee/editAcc": {

			break;
		}

		case "/employee/createAcc": {

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

				EmployeeHelper employeeHelper = new EmployeeHelper();

				employeeHelper.addAccount(account);

				request.setAttribute("success", "true");

				request.getRequestDispatcher("").forward(request, response);

			} catch (CustomBankException e) {
				request.setAttribute("error", e.getFullMessage());
				request.getRequestDispatcher("").forward(request, response);
			}

			break;
		}

		}
	}
}
