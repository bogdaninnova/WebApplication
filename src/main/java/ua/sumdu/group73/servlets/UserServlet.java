package ua.sumdu.group73.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ua.sumdu.group73.model.Messager;
import ua.sumdu.group73.model.OracleDataBase;
import ua.sumdu.group73.model.objects.Category;
import ua.sumdu.group73.model.objects.Picture;
import ua.sumdu.group73.model.objects.Product;
import ua.sumdu.group73.model.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This servlet working with user.jsp.
 *
 * Created by Greenberg Dima <gdvdima2008@yandex.ru>
 */
public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);
    private String showContent;
    private List<Category> categoryList;
    private List<Product> purchasedList;
    private List<Product> followingList;
    private List<Picture> pictures;
    private List<User> users;
    private List<Product> goods;
    private Product product;
    private List<Integer> categoryID = new ArrayList<>();
    private List<String> productURL = new ArrayList<>();
    private boolean step2;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        showContent = "information";
        categoryList = OracleDataBase.getInstance().getAllCategories();
        purchasedList = null;
        followingList = null;
        pictures = null;
        users = null;
        goods = null;
        product = null;
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
        } else if ("sendNewUserData".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                if (OracleDataBase.getInstance().changeData(user.getId(), request.getParameter("name"),
                        request.getParameter("secondName"), request.getParameter("phone"))) {
                        request.getSession().setAttribute("user", null);
                    sendResponse(response, "<result>OK</result>");
                }
            } else {
                sendResponse(response, "<result>Please Login</result>");
            }
        } else if ("clickChangeEmail".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                if (Messager.changeMail(user.getLogin(), request.getParameter("checkEmail"))) {
                    request.getSession().setAttribute("user", null);
                    sendResponse(response, "<result>OK</result>");
                }
            } else {
                sendResponse(response, "<result>Please Login</result>");
            }
        } else if ("clickAddLotPage".equals(request.getParameter("action"))) {
            log.info("Click clickAddLotPage");
            showContent = "clickAddLotPage";
            sendResponse(response, "<result>OK</result>");
        } else if ("clickAddLot".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                if (Integer.parseInt(request.getParameter("startPrice")) > 0 &&
                        Integer.parseInt(request.getParameter("buyOutPrice")) > 0) {
                    if (Integer.parseInt(request.getParameter("buyOutPrice")) > Integer.parseInt(request.getParameter("startPrice"))) {
                        int productID = OracleDataBase.getInstance().addProduct(user.getId(), request.getParameter("title"),
                                request.getParameter("description"), Long.parseLong(request.getParameter("endDate")),
                                Integer.parseInt(request.getParameter("startPrice")), Integer.parseInt(request.getParameter("buyOutPrice")));
                        if (productID != -1 && productID != 0) {
                            product = null;
                            product = OracleDataBase.getInstance().getProduct(productID);
                            sendResponse(response, "<result>OK</result>");
                        } else {
                            sendResponse(response, "<result>Incorrect product number</result>");
                        }
                    } else {
                        sendResponse(response, "<result>By it now less than start price</result>");
                    }
                } else {
                    sendResponse(response, "<result>Incorrect price.</result>");
                }
            }
        } else if ("showLotsPurchased".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                purchasedList = OracleDataBase.getInstance().getUsersBuying(user.getId());
                showContent = "showLotsPurchased";
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("followingProducts".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                followingList = OracleDataBase.getInstance().getFollowingProducts(user.getId());
                showContent = "followingProducts";
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("clickSoldGoodsPage".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                goods = OracleDataBase.getInstance().getUsersProducts(user.getId());
                showContent = "clickSoldGoodsPage";
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("clickGoodsForSalePage".equals(request.getParameter("action"))) {
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                goods = OracleDataBase.getInstance().getUsersProducts(user.getId());
                showContent = "clickGoodsForSalePage";
                sendResponse(response, "<result>OK</result>");
            }
        } else if ("clickGoodsForSale".equals(request.getParameter("action"))) {
            request.getSession().setAttribute("prodID", Integer.parseInt(request.getParameter("prodID")));
            sendResponse(response, "<result>OK</result>");
        } else if ("addCategories".equals(request.getParameter("action"))) {
           categoryID.add(Integer.parseInt(request.getParameter("categoryID")));
            sendResponse(response, "<result>OK</result>");
        } else if ("clickAddCategories".equals(request.getParameter("action"))) {
            if(OracleDataBase.getInstance().addCategoriesToProduct(Integer.parseInt(request.getParameter("productID")), categoryID)) {
                categoryList = null;
                step2 = true;
                sendResponse(response, "<result>OK</result>");
            }

        } else if ("clickNewLot".equals(request.getParameter("action"))) {
            product = null;
            step2 = false;
            sendResponse(response, "<result>OK</result>");
        } else {
            String ajaxUpdateResult = "";
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) {
                    String fileName = item.getName();
                    InputStream content = item.getInputStream();
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    if (uploadImage(fileName, content)) {
                        if (OracleDataBase.getInstance().addPicturesToProduct(product.getId(), productURL)) {
                            ajaxUpdateResult = "File " + fileName + " is successfully uploaded\n\r";
                        }
                    }
                }
            } catch (FileUploadException e) {
                log.error("Parsing file upload failed.", e);
            }
            response.getWriter().print(ajaxUpdateResult);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryList = OracleDataBase.getInstance().getAllCategories();
        pictures = OracleDataBase.getInstance().getAllPictures();
        users = OracleDataBase.getInstance().getAllUsers();

        request.setAttribute("showContent", showContent);
        request.setAttribute("categories", categoryList);
        request.setAttribute("purchasedList", purchasedList);
        request.setAttribute("followingList", followingList);
        request.setAttribute("pictures", pictures);
        request.setAttribute("users", users);
        request.setAttribute("goods", goods);
        request.setAttribute("product", product);
        request.setAttribute("step2", step2);
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

    private synchronized boolean uploadImage(String fileName, InputStream content) {
        OutputStream fos = null;
        File file;
        try {
            file = new File("D:/NetCracker/GitHub/WebApplication/target/WebApplication/images/product-images/" + fileName);
            productURL.clear();
            productURL.add("../WebApplication/images/product-images/"+ fileName);
            if (!file.exists()) {
                file.createNewFile();
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[1000];
                while (content.available() > 0) {
                    int count = content.read(buffer);
                    fos.write(buffer, 0, count);
                }
            }
            return true;
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
        return false;
    }
}
