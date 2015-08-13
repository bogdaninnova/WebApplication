package ua.sumdu.group73.servlets;

import org.apache.log4j.Logger;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.User;

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
        } else if ("clickUserChangeData".equals(request.getParameter("action"))) {
            log.info("Click UserChangeData");
            showContent = "changeUserData";
            sendResponse(response, "<result>OK</result>");
        } else if ("changePassword".equals(request.getParameter("action"))) {
            showContent = "changePassword";
            sendResponse(response, "<result>OK</result>");
        } else if ("changeUser".equals(request.getParameter("action"))) {
            showContent = "changeUser";
            sendResponse(response, "<result>OK</result>");
        } else if ("changeEmail".equals(request.getParameter("action"))) {
            showContent = "changeEmail";
            sendResponse(response, "<result>OK</result>");
        } else if ("clickChangePassword".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                if (OracleDataBase.getInstance().changePassword(user.getId(), request.getParameter("oldPassword"), request.getParameter("newPassword"))) {
                    sendResponse(response, "<result>OK</result>");
                }
            } else {
                sendResponse(response, "<result>Please Login</result>");
            }
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
