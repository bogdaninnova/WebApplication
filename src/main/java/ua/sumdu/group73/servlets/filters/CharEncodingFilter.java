package ua.sumdu.group73.servlets.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CharEncodingFilter implements Filter {

	public void destroy() {	}
	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//response.setContentType("text/html;charset=Windows-1251");
		//request.setCharacterEncoding("Cp1251"); 
        response.setContentType("text/xml");
        ((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}
}
