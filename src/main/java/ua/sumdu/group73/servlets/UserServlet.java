package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet working with user.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);
    private String showContent;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        showContent = "information";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if ("clickBack".equals(request.getParameter("action"))) {
            log.info("Click exit");
            sendResponse(response, "<result>OK</result>");
        } else if ("clickInform".equals(request.getParameter("action"))) {
            log.info("Click Information");
            showContent = "information";
            sendResponse(response, "<result>OK</result>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("showContent", showContent);
        RequestDispatcher rd = request.getRequestDispatcher("jsp/user.jsp");
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
