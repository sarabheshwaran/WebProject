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

import org.json.JSONObject;

/**
 * Servlet Filter implementation class ApiFilter
 */
@WebFilter("/api/*")
public class ApiFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ApiFilter() {
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
		
		String apiKey = httpRequest.getHeader("Authentication");
		
		if(apiKey != null && apiKey.equals("hello")) {
			
			chain.doFilter(request, response);
			
		}
		else {
			
			JSONObject result = new JSONObject();
			
			result.put("result", "failed");
			result.put("response-code", "401");
			result.put("cause", "authentication failed");
			
			httpResponse.getWriter().print(result);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
