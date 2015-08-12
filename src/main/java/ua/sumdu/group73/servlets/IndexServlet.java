package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;

import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.ServerTimerTask;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This servlet working with index.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class IndexServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(IndexServlet.class);
    private List<Category> categoryList;
    private List<Product> products;
    private List<Product> showProduct;
    private List<Picture> pictures;
    private List<User> users;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Init UserServlet");
        products = null;
        products = OracleDataBase.getInstance().getAllActiveProducts();
        log.info("Init products");
        showProduct = new ArrayList<Product>();
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
            if (request.getSession().getAttribute("user") != null || request.getSession().getAttribute("user") != "") {
                request.getSession().setAttribute("user", null);
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("getProducts".equals(request.getParameter("action"))) {
            log.info("Click getProducts");
            if (Integer.parseInt(request.getParameter("id")) > 0) {
                products = null;
                products = OracleDataBase.getInstance().getProductsByCategory(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("products", products);
            } else {
                products = null;
                products = OracleDataBase.getInstance().getAllActiveProducts();
            }
            sendResponse(response, "<result>OK</result>");
        } else if ("product".equals(request.getParameter("action"))) {
            log.info("Click product");
            request.getSession().setAttribute("prodID", Integer.parseInt(request.getParameter("prodID")));
            sendResponse(response, "<result>OK</result>");
        } else if ("admin".equals(request.getParameter("action"))) {
            log.info("Click admin");
            sendResponse(response, "<result>OK</result>");
        } else if ("cabinet".equals(request.getParameter("action"))) {
            log.info("Click cabinet");
            sendResponse(response, "<result>OK</result>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ServerTimerTask.getInstance();
    	showProduct.clear();
    	if (products != null) {
            for (Product product : products) {
                if (OracleDataBase.getInstance().isProductActive(product.getId())) {
                    showProduct.add(OracleDataBase.getInstance().getProduct(product.getId()));
                }
            }
        }

        categoryList = null;
        users = null;
        pictures = null;
        categoryList = OracleDataBase.getInstance().getAllCategories();
        log.info("Init categoryList" + categoryList);
        users = OracleDataBase.getInstance().getAllUsers();
        pictures = OracleDataBase.getInstance().getAllPictures();
        log.info("Init pictures");
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        request.setAttribute("list", categoryList);
        request.setAttribute("products", showProduct);
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
