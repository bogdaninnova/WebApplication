package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet working with index.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);
    private HttpSession session;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Init UserServlet");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
//        response.setContentType("text/html; charset=UTF-8");
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
                int res = OracleDataBase.getInstance().authorization(request.getParameter("login"), request.getParameter("password"));
                if (res != -1) {
                    session.setAttribute("username", OracleDataBase.getInstance().getUser(res).getName());
                    sendResponse(response, "OK", null);
//                }
//            } else {
//                sendResponse(response, "Error", "This login is busy.");
            }
        } else if ("loginEmail".equals(request.getParameter("action"))) {
            int res = OracleDataBase.getInstance().authorizationByEmail(request.getParameter("login"), request.getParameter("password"));
            if (res != -1) {
                session.setAttribute("username", OracleDataBase.getInstance().getUser(res).getName());
                sendResponse(response, "OK", null);
            }
        } else if("outLogin".equals(request.getParameter("action"))) {
            if (session.getAttribute("username") != null) {
                session.setAttribute("username", null);
                sendResponse(response, "OK", null);
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void sendResponse(HttpServletResponse response, String text, String error) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if ("error".equals(text)) {
                pw.print("<result>" + text + "</result>");
                pw.println("error>" + error.toUpperCase() + "</error>");
            } else {
                pw.println("<result>" + text + "</result>");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
