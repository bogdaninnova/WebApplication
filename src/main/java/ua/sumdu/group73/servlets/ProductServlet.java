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
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        if ("back".equals(request.getParameter("action"))) {
            request.getSession().setAttribute("prodID", null);
            sendResponse(response, "<result>OK</result>");
        } else if ("clickBet".equals(request.getParameter("action"))) {
            if (OracleDataBase.getInstance().makeBet(Integer.parseInt(request.getParameter("productID")),
                    Integer.parseInt(request.getParameter("buyerID")),
                    Integer.parseInt(request.getParameter("bet")))) {
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Error: DataBase.</result>");
            }
        } else if ("clickBuy".equals(request.getParameter("action"))) {
            request.getSession().setAttribute("buy", "ok");
            sendResponse(response, "<result>OK</result>");
        } else if ("break".equals(request.getParameter("action"))) {
            request.getSession().setAttribute("buy", null);
            sendResponse(response, "<result>OK</result>");
        } else if ("realBuy".equals(request.getParameter("action"))) {
            if (OracleDataBase.getInstance().buyout(Integer.parseInt(request.getParameter("productID")),
                    Integer.parseInt(request.getParameter("userID")))) {
                request.getSession().setAttribute("buy", null);
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Can not buy</result>");
            }
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
                && session.getAttribute("user") != null && session.getAttribute("user") != ""
                && OracleDataBase.getInstance().isProductActive(Integer.parseInt(session.getAttribute("prodID").toString()))) {
            product = null;
            pictures = null;
            users = null;
            product = OracleDataBase.getInstance().getProduct(Integer.parseInt(session.getAttribute("prodID").toString()));
            pictures = OracleDataBase.getInstance().getAllPictures();
            users = OracleDataBase.getInstance().getAllUsers();
            request.setAttribute("pictures", pictures);
            request.setAttribute("product", product);
            request.setAttribute("users", users);
            RequestDispatcher rd;
            if (session.getAttribute("buy") != null && session.getAttribute("buy") != "") {
                rd = request.getRequestDispatcher("jsp/buy.jsp");
            } else {
               rd = request.getRequestDispatcher("jsp/product.jsp");
            }

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
