package ua.sumdu.group73.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Category;
import ua.sumdu.group73.model.objects.Picture;
import ua.sumdu.group73.model.objects.Product;
import ua.sumdu.group73.model.objects.User;

import java.io.IOException;
import java.util.*;

/**
 * This servlet working with admin.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class AdminServlet extends HttpServlet {
    
    private static final Logger log = Logger.getLogger(AdminServlet.class);
    private HttpSession session;
	
    private List<Category> categoryList = null;
    private List<Product> products = null;
    private List<Picture> pictures = null;
    private List<User> users = null;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("saveUsers") != null) {
			OracleDataBase.getInstance().unBanAllUsers();
			String[] s = request.getParameterValues("ban");
			if (s != null) {
				List<Integer> banList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					banList.add(Integer.valueOf(s[i]));
				OracleDataBase.getInstance().setUserBan(banList);
			}
			doGet(request, response);
		} else if (request.getParameter("deleteProduct") != null) {
			System.out.println("deleteProduct");
			doGet(request, response);
		}
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (session != null) {
            log.info("HttpSession isn't null");
        } else {
            session = request.getSession();
            log.info("Create HttpSession in UserServlet");
        }
        
       	categoryList = OracleDataBase.getInstance().getAllCategories();
       	users = OracleDataBase.getInstance().getAllUsers();
       	products = OracleDataBase.getInstance().getAllProducts();
       	pictures = OracleDataBase.getInstance().getAllPictures();
        
        request.setAttribute("categories", categoryList);
        request.setAttribute("products", products);
        request.setAttribute("pictures", pictures);
        request.setAttribute("users", users);
        
        RequestDispatcher rd = request.getRequestDispatcher("jsp/admin.jsp");
        rd.forward(request, response);
    }

}
