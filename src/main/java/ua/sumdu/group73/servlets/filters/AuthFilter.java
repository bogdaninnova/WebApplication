package ua.sumdu.group73.servlets.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class AuthFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //String path = ((HttpServletRequest) request).getRequestURI();

		HttpSession session = req.getSession();
		if ((session == null) ||
			(session.getAttribute("user") == null)||
			(session.getAttribute("user") == "")) {
				res.sendRedirect("login");
				return;
		}
		
		chain.doFilter(request, response);
	}
}
