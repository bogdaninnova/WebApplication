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
    private Product product;
    private List<Picture> pictures;
    private List<User> users;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        if ("clickBet".equals(request.getParameter("action"))) {
            if (OracleDataBase.getInstance().makeBet(Integer.parseInt(request.getParameter("productID")),
                    Integer.parseInt(request.getParameter("buyerID")),
                    Integer.parseInt(request.getParameter("bet")))) {
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Error: DataBase.</result>");
            }
        } else if ("realBuy".equals(request.getParameter("action"))) {
            if (OracleDataBase.getInstance().buyout(Integer.parseInt(request.getParameter("productID")),
                    Integer.parseInt(request.getParameter("userID")))) {
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Can not buy</result>");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher rd;
            if (request.getParameter("id") != null && !request.getParameter("id").equals("") &&
                    OracleDataBase.getInstance().isProductActive(Integer.parseInt(request.getParameter("id")))) {
                product = OracleDataBase.getInstance().getProduct(Integer.parseInt(request.getParameter("id")));
                pictures = OracleDataBase.getInstance().getPictures(Integer.parseInt(request.getParameter("id")));
                users = OracleDataBase.getInstance().getAllUsers();
                request.setAttribute("pictures", pictures);
                request.setAttribute("product", product);
                request.setAttribute("users", users);
                if (request.getParameter("page") != null && request.getParameter("page") != "") {
                    if ("buy".equals(request.getParameter("page"))) {
                        rd = request.getRequestDispatcher("jsp/buy.jsp");
                    } else {
                        rd = request.getRequestDispatcher("jsp/product.jsp");
                    }
                }else {
                    rd = request.getRequestDispatcher("jsp/product.jsp");
                }
                rd.forward(request, response);
            } else {
                rd = request.getRequestDispatcher("index");
                rd.forward(request, response);
            }

    }

    /**
     * This method send response.
     *
     * @param response - HttpServletResponse.
     * @param text     - message.
     */
    private void sendResponse(HttpServletResponse response, String text) {
        try (PrintWriter pw = response.getWriter()) {
            pw.println(text);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
