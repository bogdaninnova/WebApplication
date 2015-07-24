package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
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
        log.info("INIT METHOD");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Request - " + request.getParameter("action"));

        if ("registerForm".equals(request.getParameter("action"))) {
            log.info("Click register");
        } else if ("find".equals(request.getParameter("action"))) {
            log.info("Click find with query - " + request.getParameter("text"));
        } else if ("login".equals(request.getParameter("action"))) {

            log.info("Click find with query - " + request.getParameter("login"));
            log.info("Click find with query - " + request.getParameter("password"));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
