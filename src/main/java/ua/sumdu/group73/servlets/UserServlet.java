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
//        request.setAttribute("category", categoryList);
//        log.info("Category - " + categoryList.toString());
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
            sendResponse(response, "<result>OK</result>", null);
        } else if ("find".equals(request.getParameter("action"))) {
            log.info("Click find with query - " + request.getParameter("text"));
            sendResponse(response, "<result>OK</result>", null);
        } else if ("login".equals(request.getParameter("action"))) {
//            if (user is free) {
            User res = OracleDataBase.getInstance().authorization(request.getParameter("login"), request.getParameter("password"));
            log.info("USER - " + res);
            if (res != null) {
                session.setAttribute("username", res.getSecondName());
                sendResponse(response, "<result>OK</result>", null);
            } else {
                sendResponse(response, "message", "Login incorrect.");
            }
//            } else {
//                sendResponse(response, "Error", "This login is busy.");
//            }
        } else if ("getCatalog".equals(request.getParameter("action"))) {
            sendResponse(response, createHtmlList(), null);
        } else if ("loginEmail".equals(request.getParameter("action"))) {
            User res = OracleDataBase.getInstance().authorizationByEmail(request.getParameter("login"), request.getParameter("password"));
            if (res != null) {
                session.setAttribute("username", res.getSecondName());
                sendResponse(response, "<result>OK</result>", null);
            } else {
                sendResponse(response, "error", "Email incorrect.");
            }
        } else if("logOut".equals(request.getParameter("action"))) {
            if (session.getAttribute("username") != null) {
                session.setAttribute("username", null);
                sendResponse(response, "<result>OK</result>", null);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void sendResponse(HttpServletResponse response, String text, String message) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if ("message".equals(text)) {
                pw.println("<result>");
                pw.println("<text>" + message + "</text>");
                pw.println("</result>");
            } else {
                pw.println(text);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method created html.
     *
     * @return String
     */
    private String createHtmlList() {
        StringBuilder htmlList = new StringBuilder();
        for (Category cat : categoryList) {
            if (cat.getParentID() == 0) {
                htmlList.append("<ul class=\"navigation\">");
                int id = cat.getId();
                htmlList.append("<a class=\"main\" href=\"#url\">");
                htmlList.append(cat.getName());
                htmlList.append("\"</a>\"");
                for (Category subDirectory : categoryList) {
                    int count = 0;
                    if (subDirectory.getId() == id) {
                        count += 1;
                        htmlList.append("<li class=\"n");
                        htmlList.append(count);
                        htmlList.append("\"><a href=\"#\">");
                        htmlList.append(subDirectory);
                        htmlList.append("</a></li>");
                    }
                }
                htmlList.append("</ul>");
            }
        }
        for (int i = 1; i < 5; i++) {
            htmlList.append("<ul class=\"navigation\">");
            htmlList.append("<a class=\"main\" href=\"#url\">Catalog " + i + "</a>");
            htmlList.append("<li class=\"n1\"><a href=\"#\">SubDirectory #1</a></li>");
            htmlList.append("<li class=\"n2\"><a href=\"#\">SubDirectory #2</a></li>");
            htmlList.append("<li class=\"n3\"><a href=\"#\">SubDirectory #3</a></li>");
            htmlList.append("</ul>");
        }
        log.info("HTML List - " + htmlList.toString());
        return htmlList.toString();
    }
}
