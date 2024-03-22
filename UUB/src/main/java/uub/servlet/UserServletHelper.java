package uub.servlet;

import java.awt.dnd.DropTargetContext;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uub.enums.UserType;
import uub.logicallayer.CustomerHelper;
import uub.logicallayer.UserHelper;
import uub.model.Account;
import uub.model.Customer;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

public class UserServletHelper {

	
	public static void loginUtils(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {

		int userId = HelperUtils.formatNumber(request.getParameter("userId"));

		String password = request.getParameter("password");


			UserHelper userHelper = new UserHelper();


			UserType type = userHelper.login(userId, password);

			request.getSession().setAttribute("userId", userId);

			request.getSession().setAttribute("type", type);
			
			request.getSession().setMaxInactiveInterval(100);

			switch (type) {
			case CUSTOMER:{

				response.sendRedirect(request.getContextPath()+"/app/user/accounts");
				break;
			}
			case EMPLOYEE:{


				response.sendRedirect(request.getContextPath()+"/app/employee/accounts");
				break;
			}
			default:
				break;
			}


		} catch (CustomBankException e) {
			
			request.setAttribute("error", e.getFullMessage());
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			e.printStackTrace();
		}
	}
	

	public static void accountsPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = (int) request.getSession().getAttribute("userId");

		try {
			CustomerHelper customerHelper = new CustomerHelper();

			Map<Integer,Account> accounts = customerHelper.getAccounts(userId);
			Customer customer = customerHelper.getCustomer(userId);

			request.setAttribute("profile", customer);
			request.setAttribute("accountMap", accounts);
			


			request.getRequestDispatcher("/WEB-INF/user/accounts.jsp").forward(request, response);
		} catch (CustomBankException e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect("app/login");
		}
	}
	
	public static void historyPage(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
}
