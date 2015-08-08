package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Category;
import ua.sumdu.group73.model.objects.Product;

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
            sendResponse(response, "<result>OK</result>", null);
        } else if ("find".equals(request.getParameter("action"))) {
            log.info("Click find with query - " + request.getParameter("text"));
            sendResponse(response, "<result>OK</result>", null);
        } else if ("login".equals(request.getParameter("action"))) {
            log.info("Click login");
            sendResponse(response, "<result>OK</result>", null);
        } else if ("logOut".equals(request.getParameter("action"))) {
            if (session.getAttribute("user") != null) {
                session.removeAttribute("user");
                sendResponse(response, "<result>OK</result>", null);
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
            sendResponse(response, "<result>OK</result>", null);
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
            products = OracleDataBase.getInstance().getAllProducts();
            log.info("Init products");
            count += 1;
        }

        categoryList = null;
        categoryList = OracleDataBase.getInstance().getAllCategories();
        log.info("Init categoryList" + categoryList);
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        request.setAttribute("list", categoryList);
        request.setAttribute("products", products);
        rd.forward(request, response);
    }

    private void sendResponse(HttpServletResponse response, String text, String message) {
        try (PrintWriter pw = response.getWriter()) {
            if ("message".equals(text)) {
                pw.println("<result>");
                pw.println("<text>" + message + "</text>");
                pw.println("</result>");
            } else {
                pw.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
