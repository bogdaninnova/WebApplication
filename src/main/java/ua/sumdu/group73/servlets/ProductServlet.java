package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Picture;
import ua.sumdu.group73.model.objects.Product;
import ua.sumdu.group73.model.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This servlet working with product.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class ProductServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(IndexServlet.class);
    private HttpSession session;
    private Product product;
    private List<Picture> pictures;
    private List<User> users;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("back".equals(request.getParameter("action"))) {
            session.setAttribute("prodID", "");
            sendResponse(response, "<result>OK</result>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (session != null) {
            log.info("HttpSession isn't null");
        } else {
            session = request.getSession();
            log.info("Create HttpSession in ProductServlet");
        }

        if (session.getAttribute("prodID") != null && session.getAttribute("prodID") != ""
                && session.getAttribute("user") != null && session.getAttribute("user") != "") {
            product = null;
            pictures = null;
            users = null;
            product = OracleDataBase.getInstance().getProduct(Integer.parseInt(session.getAttribute("prodID").toString()));
            pictures = OracleDataBase.getInstance().getAllPictures();
            users = OracleDataBase.getInstance().getAllUsers();
            request.setAttribute("pictures", pictures);
            request.setAttribute("product", product);
            request.setAttribute("users", users);
            RequestDispatcher rd = request.getRequestDispatcher("jsp/product.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("index");
            rd.forward(request, response);
        }
    }

    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
