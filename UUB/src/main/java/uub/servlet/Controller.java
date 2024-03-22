package uub.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		System.out.println(path);

		switch (path) {

		case "/login": {

			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			break;
		}
		case "/logout": {
			request.getSession().setAttribute("userId", null);
			request.getSession().setAttribute("type", null);
			break;
		}
		case "/user/accounts": {

			UserServletHelper.accountsPage(request, response);
			break;
		}
		case "/user/profile": {

			ProfileHelper.profilePage(request, response);
			break;
		}
		case "/employee/accounts": {

			request.getRequestDispatcher("/WEB-INF/employee/accounts.jsp").forward(request, response);
			break;
		}
//		case "/": {
//
//			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//			break;
//		}
//		case "/login": {
//
//			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//			break;
//		}
//		case "/login": {
//
//			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//			break;
//		}

		default: {

			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		switch (pathInfo) {

		case "/": {

		}

		case "/login": {

			UserServletHelper.loginUtils(request, response);
			break;
		}
		case "/logout": {
			request.getSession().setAttribute("userId", null);
			request.getSession().setAttribute("type", null);
			break;
		}
		case "/user/accounts": {

			request.getRequestDispatcher("/WEB-INF/userAccounts.jsp").forward(request, response);
			break;
		}
		case "/user/profile": {

			ProfileHelper.changePassword(request, response);
		}
		case "/employee/accounts": {

			request.getRequestDispatcher("/WEB-INF/employeeAccounts.jsp").forward(request, response);
			break;
		}

		}
	}
}
