package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Category;
import ua.sumdu.group73.model.objects.User;

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
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);
    private HttpSession session;
    private List<Category> categoryList;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Init UserServlet");
        categoryList = OracleDataBase.getInstance().getAllCategories();
        log.info("Init categoryList" + categoryList);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("category", categoryList);
        log.info("Category - " + categoryList.toString());
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (session != null) {
            log.info("HttpSession isn't null");
        } else {
            session = request.getSession();
            log.info("Create HttpSession in UserServlet");
        }

        log.info("Request - " + request.getParameter("action"));

        if ("registerForm".equals(request.getParameter("action"))) {
            log.info("Click register");
            sendResponse(response, "OK", null);
        } else if ("find".equals(request.getParameter("action"))) {
            log.info("Click find with query - " + request.getParameter("text"));
            sendResponse(response, "OK", null);
        } else if ("login".equals(request.getParameter("action"))) {
//            if (user is free) {
                User res = OracleDataBase.getInstance().authorization(request.getParameter("login"), request.getParameter("password"));
                log.info("USER - " + res);
                if (res != null) {
                    session.setAttribute("username", res.getSecondName());
                    sendResponse(response, "OK", null);
                } else {
                    sendResponse(response, "error", "Login incorrect.");
                }
//            } else {
//                sendResponse(response, "Error", "This login is busy.");
//            }
        } else if ("loginEmail".equals(request.getParameter("action"))) {
            User res = OracleDataBase.getInstance().authorizationByEmail(request.getParameter("login"), request.getParameter("password"));
            if (res != null) {
                session.setAttribute("username", res.getSecondName());
                sendResponse(response, "OK", null);
            } else {
                sendResponse(response, "error", "Email incorrect.");
            }
        } else if("logOut".equals(request.getParameter("action"))) {
            if (session.getAttribute("username") != null) {
                session.setAttribute("username", null);
                sendResponse(response, "OK", null);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void sendResponse(HttpServletResponse response, String text, String errorMessage) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if ("error".equals(text)) {
                pw.println("<error>");
                pw.println("<text>" + errorMessage + "</text>");
                pw.println("</error>");
            } else {
                pw.println("<result>" + text + "</result>");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
