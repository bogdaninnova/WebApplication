package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet working with register.jsp.
 *
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
            if (OracleDataBase.getInstance().addUser(request.getParameter("login"), request.getParameter("password"),
                    request.getParameter("firstName"), request.getParameter("secondName"), request.getParameter("age"),
                    request.getParameter("email"), request.getParameter("phone"))) {
                session.setAttribute("username", request.getParameter("secondName"));
                PrintWriter pw = response.getWriter();
                pw.println("<result>OK</result>");
                pw.close();
            }
        } else {
            log.info("DON'T REGISTER");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}