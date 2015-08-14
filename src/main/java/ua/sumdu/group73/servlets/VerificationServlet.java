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
		loginToken = loginToken.replaceAll(" ", "+");
		String login = StringCrypter.getInstance().decrypt(loginToken);
		
		String dateToken = (String) request.getParameter("d");
		String emailToken = (String) request.getParameter("e");		
		
		if (dateToken != null) {
			
			dateToken = dateToken.replaceAll(" ", "+");
			String dateString = StringCrypter.getInstance().decrypt(dateToken);
			long regDate = Long.valueOf(dateString);
	
			if ((regDate + 24 * 60 * 60 * 1000) < new Date().getTime())
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			else {
				OracleDataBase.getInstance().activateUser(login);
				response.sendRedirect("login");
			}
		} else if (emailToken != null) {
			emailToken = emailToken.replaceAll(" ", "+");
			String email = StringCrypter.getInstance().decrypt(emailToken);
			OracleDataBase.getInstance().changeEMail(login, email);
			response.sendRedirect("index");
		}
	}
}
