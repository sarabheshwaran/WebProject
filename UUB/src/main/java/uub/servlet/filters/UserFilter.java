package uub.servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uub.enums.Exceptions;
import uub.enums.UserStatus;
import uub.logicallayer.CustomerHelper;
import uub.model.Customer;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

/**
 * Servlet Filter implementation class SessionFilter
 */
//@WebFilter("/app/user/*")
public class UserFilter implements Filter {


	public UserFilter() {
		// TODO Auto-generated constructor stub
	}


	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {

			CustomerHelper customerHelper = new CustomerHelper();

			HttpSession session = httpRequest.getSession();

			HelperUtils.nullCheck(session);

			Object id = session.getAttribute("userId");
			
			HelperUtils.nullCheck(id);
			
			int userId = (int) id;
			Customer customer = customerHelper.getCustomer(userId);
			
			if(customer.getStatus()==UserStatus.INACTIVE) {
				session.invalidate();
				throw new CustomBankException(Exceptions.DEACTIVATED_USER);
			}
			
			request.setAttribute("myProfile", customer);

			chain.doFilter(request, response);
			

		} catch (CustomBankException e) {
			e.printStackTrace();
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/app/login");
			
		}

	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
