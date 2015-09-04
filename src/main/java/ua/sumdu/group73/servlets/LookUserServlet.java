package ua.sumdu.group73.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.sumdu.group73.model.*;

public class LookUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getParameter("id") != null) {
    		int userID = Integer.valueOf(request.getParameter("id"));
    		request.setAttribute("lookingUser", OracleDataBase.getInstance().getUser(userID));
    		request.setAttribute("buying", OracleDataBase.getInstance().getUsersBuying(userID));
    		request.setAttribute("solding", OracleDataBase.getInstance().getUsersProducts(userID));
    		request.setAttribute("users", OracleDataBase.getInstance().getAllUsers());
    		request.setAttribute("id", userID);
       		RequestDispatcher rd = request.getRequestDispatcher("jsp/lookUser.jsp");
            rd.forward(request, response);
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("deleteProductBUY") != null) {
			String[] s = request.getParameterValues("deleteCheckBoxBUY");
			if (s != null) {
				List<Integer> deleteList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					deleteList.add(Integer.valueOf(s[i]));
				OracleDataBase.getInstance().deleteProducts(deleteList);
			}
			response.sendRedirect("lookUser?id=" + request.getParameter("id"));
		} else if (request.getParameter("deleteProductSELL") != null) {
			String[] s = request.getParameterValues("deleteCheckBoxSELL");
			if (s != null) {
				List<Integer> deleteList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					deleteList.add(Integer.valueOf(s[i]));
				OracleDataBase.getInstance().deleteProducts(deleteList);
			}
			response.sendRedirect("lookUser?id=" + request.getParameter("id"));
		}
	}

}
