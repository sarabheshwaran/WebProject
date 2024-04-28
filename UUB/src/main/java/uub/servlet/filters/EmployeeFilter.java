package uub.servlet.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uub.logicallayer.EmployeeHelper;
import uub.model.Employee;
import uub.staticlayer.CustomBankException;
import uub.staticlayer.HelperUtils;

/**
 * Servlet Filter implementation class EmployeeFilter
 */
//@WebFilter("/app/employee/*")
public class EmployeeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EmployeeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {

			EmployeeHelper employeeHelper = new EmployeeHelper();

			HttpSession session = httpRequest.getSession();

			HelperUtils.nullCheck(session);

			Object id = session.getAttribute("empId");
			
			HelperUtils.nullCheck(id);
			
			int empId = (int) id;

			Employee employee = employeeHelper.getEmployee(empId);

			request.setAttribute("myProfile", employee);
			request.setAttribute("access", employee.getRole().getRole());

			chain.doFilter(request, response);
			

		} catch (CustomBankException e) {
			e.printStackTrace();
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/app/login");
			
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
