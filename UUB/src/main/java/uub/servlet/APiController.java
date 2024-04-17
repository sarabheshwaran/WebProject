package uub.servlet;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import uub.logicallayer.CustomerHelper;
import uub.logicallayer.EmployeeHelper;
import uub.logicallayer.JsonConverter;
import uub.model.Account;
import uub.model.Customer;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

/**
 * Servlet implementation class APiController
 */
@WebServlet("/api/*")
public class APiController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public APiController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		JSONObject result = new JSONObject();
		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			String path = request.getPathInfo();
			System.out.println(path + " GET");
			if (path.startsWith("/customers/")) {

				CustomerHelper customerHelper = new CustomerHelper();

				String userPath = path.replace("/customers/", "");

				String[] segments = userPath.split("/");

				int userId = HelperUtils.formatNumber(segments[0]);

				JSONObject customer = new JSONObject(customerHelper.getCustomer(userId));

				if (segments.length == 1) {


					result.put("id", customer.get("id"));
					result.put("name", customer.get("name"));
					result.put("email", customer.get("email"));
					result.put("phone", customer.get("phone"));
					result.put("DOB", customer.get("DOB"));

					String includes = request.getParameter("include");

					includeParams(customer, result, includes);

					response.getWriter().print(result);

				} else if (segments.length == 2 && segments[1].equals("accounts")) {

					Map<Integer, Account> accounts = customerHelper.getAccounts(userId);
					try {
						JSONObject accountsJson = new JSONObject();
						accountsJson.put("data", accounts);
						response.getWriter().print(accountsJson);
					} catch (Exception e) {

						e.printStackTrace();
					}

				} else if (segments.length == 3 && segments[1].equals("accounts")) {

					int accNo = HelperUtils.formatNumber(segments[2]);

					JSONObject account = new JSONObject(customerHelper.getAccount(accNo));


					result.put("accNo", account.get("accNo"));
					result.put("userId", account.get("userId"));
					result.put("branchId", account.get("branchId"));
					result.put("type", account.get("type"));
					result.put("balance", account.get("balance"));
					result.put("status", account.get("status"));

					String includes = request.getParameter("include");

					includeParams(account, result, includes);

					response.getWriter().print(account);
				} else {
				}

			}
			
			

		} catch (CustomBankException e) {

			result.put("result", "fail");

			result.put("cause", e.getFullMessage());

		} finally {

			response.getWriter().print(result);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		try {

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			String path = request.getPathInfo();

			if (path.startsWith("/customers")) {

				String userPath = path.replace("/customers", "");

				JSONObject customerJson = getJson(request);

				System.out.println(customerJson);

				Customer customer = JsonConverter.customerPojo(customerJson);

				EmployeeHelper employeeHelper = new EmployeeHelper();

				employeeHelper.addCustomer(customer);

				result.put("result", "success");
			}

		} catch (CustomBankException e) {

			result.put("result", "fail");

			result.put("cause", e.getFullMessage());

		} finally {

			response.getWriter().print(result);
		}

	}

	private JSONObject getJson(HttpServletRequest request) throws IOException {
		StringBuilder jsonString = new StringBuilder();
		String line;
		try (BufferedReader reader = request.getReader()) {
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
		}

		return new JSONObject(jsonString.toString());
	}

	private void includeParams(JSONObject from, JSONObject to, String includes) throws CustomBankException {
		if (includes != null) {

			HelperUtils.nullCheck(from);
			HelperUtils.nullCheck(to);

			List<String> includeFields = Arrays.asList(includes.split(","));

			for (String field : includeFields) {

				try {
					to.put(field, from.get(field));
				} catch (Exception e) {

				}
			}

		}
	}
}
