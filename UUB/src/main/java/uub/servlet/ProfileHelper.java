package uub.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uub.logicallayer.CustomerHelper;
import uub.model.Customer;
import uub.staticlayer.CustomBankException;

public class ProfileHelper {

	public static void profilePage(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		
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
	}
	
	public static void changePassword(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		
	
	}
	
}
