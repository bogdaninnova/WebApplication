package ua.sumdu.group73.servlets.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import ua.sumdu.group73.model.objects.User;

public class AdminFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //String path = ((HttpServletRequest) request).getRequestURI();
        
		HttpSession session = req.getSession();
		if (
			(session == null) ||
			(session.getAttribute("user") == null) ||
			!((User) session.getAttribute("user")).isAdmin())
				;//res.sendError(403);

		chain.doFilter(request, response);
	}
}
