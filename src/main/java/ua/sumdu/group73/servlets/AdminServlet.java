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
import java.util.List;

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
		
        if (request.getParameter("backButton") != null) {
        	System.out.println("--------------------------------------");
        	response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
		
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (session != null) {
            log.info("HttpSession isn't null");
        } else {
            session = request.getSession();
            log.info("Create HttpSession in UserServlet");
        }
        
        if (categoryList == null) 
        	categoryList = OracleDataBase.getInstance().getAllCategories();
        if (users == null) 
        	users = OracleDataBase.getInstance().getAllUsers();
        if (products == null) 
        	products = OracleDataBase.getInstance().getAllProducts();
        if (pictures == null) 
        	pictures = OracleDataBase.getInstance().getAllPictures();
        
        request.setAttribute("categories", categoryList);
        request.setAttribute("products", products);
        request.setAttribute("pictures", pictures);
        request.setAttribute("users", users);
        
        RequestDispatcher rd = request.getRequestDispatcher("jsp/admin.jsp");
        rd.forward(request, response);
    }

}
