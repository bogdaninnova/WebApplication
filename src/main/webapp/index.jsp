<%@ page import="ua.sumdu.group73.model.objects.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.sumdu.group73.model.objects.User" %>
<%@ page import="ua.sumdu.group73.model.objects.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ua.sumdu.group73.model.objects.Picture" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/navigation.css"/>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery-2.1.3.js"></script>
    <script src="js/menu.js"></script>
    <script src="js/md5.js"></script>
    <script>
        $(document).ready(function () {
            $(".topnav").accordion({
                accordion: false,
                speed: 100,
                closedSign: '[+]',
                openedSign: '[-]'
            });
        });
    </script>
    <script src="js/index.js"></script>

    <title>Auction</title>
</head>
<body>
<div class="container">
    <div class="form-top" align="right">

        <tr>
            <% User user = (User) session.getAttribute("user");
                String userName = "";
                String userSecondName = "";
                boolean userStatus = false;
                if (user != null) {
                    userName = user.getName();
                    userSecondName = user.getSecondName();
                    userStatus = user.isAdmin();
                }
            %>

            <script>var user_name = '<%= userName %>';
            var user_second_name = '<%= userSecondName %>';
            var userStatus = '<%= userStatus%>'.toString();
            createForm(user_name, user_second_name, userStatus);</script>

        </tr>
    </div>
    <header>
        <div id="block-logo"><img src="images/logo.png" width="80" height="80" alt="Logo"></div>
        <div id="block-contact">
            tel. XXX XXX XX XX<br>
            tel. XXX XXX XX XX<br><br><br>
            group7.3@sumy.ua
        </div>
        <div id="block-name-site">
            <h1 align="center"><strong>AUCTION</strong></h1>
        </div>
    </header>
</div>


<div class="container">

    <section class="container-section-nav">
        <header>
            <div align="center">
                <form>
                    <br>
                    <input type="text" id="find" size="7" maxlength="30" required>
                    <button onclick="clickFind($('#find').val());">find</button>
                </form>
            </div>
        </header>

        <div align="center">
            <%! List<Category> categoryList;%>
            <% categoryList = (List<Category>) request.getAttribute("list"); %>
            <%! private StringBuilder menuHTML;

            %>
            <% List<Category> rootCategories = new ArrayList<Category>();
                menuHTML = new StringBuilder();
                for (Category c : categoryList) {
                    if (c.getParentID() == 0) {
                        rootCategories.add(c);
                    }
                }

                categoryListToHTML(0, rootCategories);
            %>
            <%! private void categoryListToHTML(int level, List<Category> childsList) {
                if (level == 0) {
                    menuHTML.append("<ul class = \"topnav\">");
                    menuHTML.append("<li><a href=\"#\" onClick=\"getProductByCategory('0');\">All products</a></li>");
                } else {
                    menuHTML.append("<ul>");
                }
                for (Category currentCat : childsList) {
                    List<Category> listOfChildrens = new ArrayList<Category>();
                    Iterator<Category> iterCat = categoryList.iterator();
                    int count = 0;
                    while (iterCat.hasNext()) {
                        Category current = iterCat.next();

                        if (currentCat.getId() == current.getParentID()) {
                            if (count == 0) {
                                menuHTML.append("<li>");
                                menuHTML.append("<a href=\"#\">" + currentCat.getName() + "</a>");
                                count += 1;
                            }
                            listOfChildrens.add(current);
                            iterCat.remove();
                        }
                    }
                    if (!listOfChildrens.isEmpty()) {
                        categoryListToHTML(++level, listOfChildrens);
                    } else {
                        menuHTML.append("<li>");
                        menuHTML.append("<a href=\"#\" onClick=\"getProductByCategory('" + currentCat.getId() + "');\">" + currentCat.getName() + "</a>");
                    }
                    menuHTML.append("</li>");
                }
                menuHTML.append("</ul>");
            }%>
            <%= menuHTML.toString() %>
        </div>

        <footer>
            <h4 align="center">Options:</h4>

            <div align="center">
                <form>
                    Price ($):<br>
                    <input type="text" id="min_price" size="3" maxlength="10">
                    - <input type="text" id="max_price" size="3" maxlength="10"><br>

                    <p><b>Condition:</b><Br>
                        <input type="radio" name="radio" value="new"> new<Br>
                        <input type="radio" name="radio" value="uses"> used<Br>
                    </p>
                    <button onclick="">Sort</button>
                </form>
            </div>
        </footer>
    </section>


    <aside class="container-section-lot">

        <% if (user != null) {
        }%>
        <header>
            <div align="center">
                <% List<Product> products = (List<Product>) request.getAttribute("products");
                    if (products != null) { %>
                <% for (Product product : products) { %>
                <div class="container-lots" onclick="clickProduct(<%=product.getId()%>);">

                    <div class="container-lots-image">
                        <% List<Picture> pictures = (List<Picture>) request.getAttribute("pictures"); %>
                        <% String image = "./images/no_image.png"; %>
                        <% if (pictures != null) {
                            for (Picture picture : pictures) {
                                if (picture.getProductID() == product.getId()) {
                                        image = picture.getURL();%>
                            <% }
                            }
                        } %>
                        <img src="<%= image%>" alt="No photo" width="150" height="100">
                    </div>
                    <div class="container-lots-price">
                        <% if (product.getCurrentPrice() != 0) { %>
                        <%=product.getCurrentPrice()%> $
                        <% } else {%>
                        <%=product.getStartPrice()%> $
                        <% } %>
                    </div>
                    <header>
                        <%=product.getName()%>
                    </header>
                    <assaid>
                        <div class="container-lots-description" align="left">
                            <%=product.getDescription()%>
                        </div>
                    </assaid>
                    <footer>
                        <div class="container-lots-time">
                            <% SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");%>
                            Close : <%= dateFormat.format(product.getEndDate()) %>
                        </div>
                        <div class="container-lots-buyer">
                            <% if (product.getCurrentBuyerID() != 0) {
                                List<User> users = (List<User>) request.getAttribute("users");
                                String nameBuyer = null;
                                if (users != null) {%>
                            <% for (User userBuyer : users) {
                                if (product.getCurrentBuyerID() == userBuyer.getId())
                                    nameBuyer = userBuyer.getLogin();
                            }%>
                            <%=nameBuyer%>
                            <% }
                            } %>
                        </div>
                    </footer>
                    <br><br>
                </div>

                <% } %>
                <%
                    }
                %>
            </div>
        </header>
        <footer>
            <div align="center">
                <% if (products != null && products.size() > 10) { %>
                //todo display page numbers
                <% } %>
            </div>
        </footer>
    </aside>
    <%@include file="jspf/user-footer.jspf"%>
</div>


</body>
</html>