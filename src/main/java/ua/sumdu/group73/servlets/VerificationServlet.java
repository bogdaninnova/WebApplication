package ua.sumdu.group73.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.StringCrypter;

/**
 * Servlet implementation class VerificationServlet
 */
public class VerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginToken = (String) request.getParameter("l");
		String dateToken = (String) request.getParameter("d");
		
		loginToken = loginToken.replaceAll(" ", "+");
		dateToken = dateToken.replaceAll(" ", "+");
		String login = StringCrypter.getInstance().decrypt(loginToken);
		String dateString = StringCrypter.getInstance().decrypt(dateToken);
		
		long regDate = Long.valueOf(dateString);

		if ((regDate + 24 * 60 * 60 * 1000) < new Date().getTime())
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		else {
			OracleDataBase.getInstance().activateUser(login);
			response.sendRedirect("login");
		}
		
	}

}
