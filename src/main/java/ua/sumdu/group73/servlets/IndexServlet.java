package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Category;
import ua.sumdu.group73.model.objects.Picture;
import ua.sumdu.group73.model.objects.Product;
import ua.sumdu.group73.model.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This servlet working with index.jsp.
 * <p/>
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class IndexServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(IndexServlet.class);
    private HttpSession session;
    private List<Category> categoryList;
    private List<Product> products;
    private List<Picture> pictures;
    private List<User> users;
    private int count = 0;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Init UserServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        log.info("Request - " + request.getParameter("action"));

        if ("registerForm".equals(request.getParameter("action"))) {
            log.info("Click register");
            sendResponse(response, "<result>OK</result>");
        } else if ("find".equals(request.getParameter("action"))) {
            log.info("Click find with query - " + request.getParameter("text"));
            if (request.getParameter("text").length() > 2) {
                products = null;
                products = OracleDataBase.getInstance().findProducts(request.getParameter("text"));
                request.setAttribute("products", products);
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Short find request</result>");
            }

        } else if ("login".equals(request.getParameter("action"))) {
            log.info("Click login");
            sendResponse(response, "<result>OK</result>");
        } else if ("logOut".equals(request.getParameter("action"))) {
            if (session.getAttribute("user") != null) {
                session.removeAttribute("user");
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("getProducts".equals(request.getParameter("action"))) {
            log.info("Click getProducts");
            if (Integer.parseInt(request.getParameter("id")) > 0) {
                products = null;
                products = OracleDataBase.getInstance().getProductsByCategory(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("products", products);
            } else {
                count = 0;
            }
            sendResponse(response, "<result>OK</result>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (session != null) {
            log.info("HttpSession isn't null");
        } else {
            session = request.getSession();
            log.info("Create HttpSession in UserServlet");
        }
        if (count == 0) {
            products = null;
            pictures = null;
            products = OracleDataBase.getInstance().getAllProducts();
            log.info("Init products");
            pictures = OracleDataBase.getInstance().getAllPictures();
            log.info("Init pictures");
            count += 1;
        }

        categoryList = null;
        users = null;
        categoryList = OracleDataBase.getInstance().getAllCategories();
        log.info("Init categoryList" + categoryList);
        users = OracleDataBase.getInstance().getAllUsers();
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        request.setAttribute("list", categoryList);
        request.setAttribute("products", products);
        request.setAttribute("pictures", pictures);
        request.setAttribute("users", users);
        rd.forward(request, response);
    }

    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }


}
