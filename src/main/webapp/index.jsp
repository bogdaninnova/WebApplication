<%@ page import="ua.sumdu.group73.model.objects.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.sumdu.group73.model.objects.User" %>
<%@ page import="ua.sumdu.group73.model.objects.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
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
            <% User user = (User)session.getAttribute("user");
                String userName = "";
                if (user != null) {
                    userName = user.getName();
                }
            %>

            <script>var user_name = '<%= userName %>'; createForm(user_name);</script>

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

        <ul class="topnav">
            <li><a href="user">Home</a></li>
            <li><a href="#">CATALOG 1</a>
                <ul>
                    <li><a href="#">SubCatalog 1</a></li>
                    <li><a href="#">SubCatalog 2</a></li>
                    <li><a href="#">SubCatalog 3</a></li>
                    <li><a href="#">SubCatalog 4</a></li>
                    <li><a href="#">SubCatalog 5</a></li>
                    <li><a href="#">SubCatalog 6</a>
                        <ul>
                            <li><a href="#">Directory 1</a></li>
                            <li><a href="#">Directory 1</a></li>
                            <li><a href="#">Directory 1</a></li>
                        </ul>
                    </li>
                    <li><a href="#">SubCatalog 7</a></li>
                </ul>
            </li>
        </ul>
        <%--<script>getCategoryList();</script>--%>
        <nav id="navigate">

                <%--<ul class="topnav">--%>



                 <%--<% List<Category> allCategories = (List<Category>) request.getAttribute("list");%>--%>
                 <%--<% List<Category> rootCategories = new ArrayList<Category>(); %>--%>
                 <%--<% StringBuilder menu = new StringBuilder(); %>--%>
                 <%--<% if (allCategories != null) {--%>
                    <%--for(Category c : allCategories) {--%>
                       <%--if(c.getParentID() == 0) {--%>
                            <%--rootCategories.add(c);--%>
                        <%--}--%>
                    <%--}--%>
                    <%--int level =0;--%>
                    <%--List<Category> childsList = rootCategories; %>--%>
                    <%--<% if (level == 0) {--%>
                        <%--menu.append("<ul class=\"topnav\">");--%>
                        <%--menu.append("<li><a href=\"user\">Home</a></li>");--%>
                    <%--} else {--%>
                        <%--menu.append("<ul>");--%>
                    <%--}--%>
                     <%--for (Category currentCat : childsList) {--%>
                         <%--menu.append("<li>");--%>
                         <%--menu.append("<a href=\"#\">" + currentCat.getName() + "</a>");--%>
                         <%--List<Category> listOfChildrens = new ArrayList<Category>();--%>
                         <%--Iterator<Category> iterCat = childsList.iterator();--%>
                         <%--while (iterCat.hasNext()) {--%>
                             <%--Category current = iterCat.next();--%>
                             <%--if (currentCat.getId() == current.getParentID()) {--%>
                                 <%--listOfChildrens.add(current);--%>
                                 <%--iterCat.remove();--%>
                             <%--}--%>
                         <%--}--%>
                         <%--if(!listOfChildrens.isEmpty()) {--%>
                             <%--++level;--%>
                             <%--childsList = listOfChildrens;--%>
                             <%--// Sub Menu--%>

                            <%----%>


                         <%--}--%>
                         <%--menu.append("</li>");--%>
                     <%--}--%>
                     <%--menu.append("</ul>");--%>
                     <%--} %>--%>
                    <%--<%=menu.toString()%>--%>



                <%--<ul>--%>


                    <% List<Category> cat = (List<Category>) request.getAttribute("list");
                        StringBuilder menu = new StringBuilder();
                        List<Category> rootCategory = new ArrayList<Category>();
                        int level = 0;
                        for (Category c : cat) {
                            if (c.getParentID() == 0){
                                rootCategory.add(c);
                            }
                        }
                    %>
                    <%if (level == 0) {
                        menu.append("<ul class = \"topnav\">");
                        menu.append("<li><a href=\"user\">Home</a></li>");
                    } else {
                        menu.append("<ul>");
                    }
                        for (Category currentCat : rootCategory) {
                            menu.append("<li>");
                            menu.append("<a href=\"#\">" + currentCat.getName() + "</a>");
                            List<Category> listOfChildrens = new ArrayList<Category>();
                            Iterator<Category> iterCat = rootCategory.iterator();
                            while (iterCat.hasNext()) {
                                Category current = iterCat.next();
                                if(currentCat.getId() == current.getParentID()) {
                                    listOfChildrens.add(current);
                                    iterCat.remove();
                                }
                            }
                            if(!listOfChildrens.isEmpty()) {
                                ++level;
                               // categoryListToHTML(++level, listOfChildrens);
                                //level 1

                    %>


                           <% }
                            menu.append("</li>");
                        }
                        menu.append("</ul>");
                    %>
                    <%=menu.toString()%>

                    <%--<% List<Category> cat = (List<Category>) request.getAttribute("list");--%>
                       <%--List<Category> rootCategory = new ArrayList<Category>();--%>
                     <%--StringBuilder menu = new StringBuilder();--%>
                     <%--if (cat != null) {--%>
                         <%--int level = 0;--%>
                         <%--if (level == 0) {--%>
                             <%--menu.append("<ul class = \"topnav\">");--%>
                             <%--menu.append("<li><a href=\"user\">Home</a></li>");--%>
                             <%--++level;--%>
                         <%--} else {--%>
                             <%--menu.append("<ul>");--%>
                         <%--}--%>
                         <%--// 1--%>
                         <%--for (Category category : cat) {--%>
                             <%--if (category.getParentID() == 0) {--%>
                                 <%--rootCategory.add(category);--%>
                                 <%--menu.append("<li><a href=\"#\">" + category.getName() + "</a>");--%>
                                 <%--// 2--%>

                             <%--}menu.append("</li>");--%>

                         <%--}menu.append("</ul>");--%>

                      <%--}--%>
                    <%--%>--%>

                    <%--<%= menu.toString()%>--%>

        </nav>
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
                    <button on onclick="">Sort</button>
                </form>
            </div>
        </footer>
    </section>


    <aside class="container-section-lot">
        <hesder>
            <div align="center">
                <% List<Product> products = (List<Product>)request.getAttribute("products");
                    if (products != null) { %>
                        <% for (Product product : products) { %>
                            <div class="container-lots">
                                <div class ="container-lots-image">

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
                                        Close: <%=product.getEndDate()%>
                                    </div>
                                    <div class="container-lots-buyer">
                                        <% if (product.getCurrentBuyerID() != 0) { %>
                                            <%=product.getCurrentBuyerID()%> //todo get userName
                                        <% } %>
                                    </div>
                                </footer>
                                <br><br>
                            </div>

                        <% } %>
                    <%}
                %>
            </div>
        </hesder>
        <footer>
            <div align="center">
                <% if (products != null && products.size() > 10) { %>
                    //todo display page numbers
                <% } %>
            </div>
        </footer>
    </aside>
</div>

<footer>
    <div class="container" align="right">
        <p>Group 7.3</p>
    </div>
</footer>

</body>
</html>