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
import java.io.PrintWriter;
import java.util.*;

@SuppressWarnings("serial")
public class AdminServlet extends HttpServlet {
    
	private static final Logger log = Logger.getLogger(AdminServlet.class);
    private HttpSession session;
	
    private List<Category> categoryList = null;
    private List<Product> products = null;
    private List<Picture> pictures = null;
    private List<User> users = null;
    

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
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
		}
		
		else if (request.getParameter("deleteProduct") != null) {
			
			String[] s = request.getParameterValues("deleteCheckBox");
			if (s != null) {
				List<Integer> deleteList = new ArrayList<Integer>();
				for (int i = 0; i < s.length; i++)
					deleteList.add(Integer.valueOf(s[i]));
				OracleDataBase.getInstance().deleteProducts(deleteList);
			}
			doGet(request, response);
		}
		
		else if (request.getParameter("BackButton") != null) {
			response.sendRedirect("index");
		}
		
		else if (request.getParameter("categories") != null) {
			
			boolean result = false;
			String categoryName = request.getParameter("catName");
			int categoryID = getCategoryID(request.getParameter("catID"));
			
			if ("create".equals(request.getParameter("categories"))) {
				if (!categoryName.equals("")) {
					if (categoryID != -1) {
						result = OracleDataBase.getInstance().addCategory(categoryID, categoryName);
					} else {
						result = OracleDataBase.getInstance().addCategory(categoryName);
					}
				}
			}
			
			else if ("change".equals(request.getParameter("categories"))) {
				if ((categoryID != -1) && (!categoryName.equals(""))) {
					result = OracleDataBase.getInstance().changeCategory(categoryID, categoryName);
				}
			} else if ("delete".equals(request.getParameter("categories"))) {
				if (categoryID != -1) {
					result = OracleDataBase.getInstance().deleteCategory(categoryID, categoryList);
				}
			}
			if (result)
				sendResponse(response, "<result>OK</result>");
			else
				sendResponse(response, "<result>ERROR</result>");
		}
	}
	
    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }
	
	private static int getCategoryID(String link) {
		if (!link.contains("#"))
			return -1;
		int position = link.lastIndexOf('#');
		String id = link.substring(position + 1);
		if (id.equals(""))
			return -1;
		return Integer.valueOf(id);
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	
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
