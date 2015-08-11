package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet working with login.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        if ("login".equals(request.getParameter("action"))) {
            log.info("Click login");
            User user = OracleDataBase.getInstance().authorization(request.getParameter("login"), request.getParameter("password"));
            log.info("USER - " + user);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Login incorrect.</result>");
            }
        } else if ("loginEmail".equals(request.getParameter("action"))) {
            User res = OracleDataBase.getInstance().authorizationByEmail(request.getParameter("login"), request.getParameter("password"));
            if (res != null) {
                request.getSession().setAttribute("user", OracleDataBase.getInstance().getUser(res.getId()));
                sendResponse(response, "<result>OK</result>");
            } else {
                sendResponse(response, "<result>Email incorrect.</result>");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("jsp/login.jsp");
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
