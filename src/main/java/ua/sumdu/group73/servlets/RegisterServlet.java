package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet working with register.jsp.
 * <p/>
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class RegisterServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterServlet.class);
    private HttpSession session;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
//        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        session = request.getSession();

        if ("registerData".equals(request.getParameter("action"))) {
            if (!OracleDataBase.getInstance().isLoginFree(request.getParameter("login"))) {
                sendResponse(response, "<result>This login is busy</result>");
            } else if (!OracleDataBase.getInstance().isEmailFree(request.getParameter("email"))) {
                sendResponse(response, "<result>This email is busy</result>");
            } else {
                if (OracleDataBase.getInstance().addUser(request.getParameter("login"), request.getParameter("password"),
                        request.getParameter("firstName"), request.getParameter("secondName"), Long.parseLong(request.getParameter("age")),
                        request.getParameter("email"), request.getParameter("phone"))) {
                    sendResponse(response, "<result>OK</result>");
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("jsp/register.jsp");
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