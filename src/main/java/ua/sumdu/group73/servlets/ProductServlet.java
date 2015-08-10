package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Picture;
import ua.sumdu.group73.model.objects.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (session != null) {
            log.info("HttpSession isn't null");
        } else {
            session = request.getSession();
            log.info("Create HttpSession in ProductServlet");
        }
        product = null;
        pictures = null;
        product = OracleDataBase.getInstance().getProduct(Integer.parseInt(session.getAttribute("prodID").toString()));
        pictures = OracleDataBase.getInstance().getAllPictures();
        request.setAttribute("pictures", pictures);
        request.setAttribute("product", product);
        RequestDispatcher rd = request.getRequestDispatcher("jsp/product.jsp");
        rd.forward(request, response);
    }
}
