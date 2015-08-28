<%@ page import="ua.sumdu.group73.model.objects.Product" %>
<%@ page import="ua.sumdu.group73.model.objects.Picture" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.sumdu.group73.model.objects.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/style-product.css"/>
    <script src="js/jquery-2.1.3.js"></script>
    <script src="js/product.js"></script>
    <title>Product</title>
</head>
<body>
<%@include file="../jspf/product-header.jspf" %>
<div class="container-product">
    <% Product product = (Product) request.getAttribute("product");
        List<Picture> pictures = (List<Picture>) request.getAttribute("pictures");%>
        <% String image = "./images/no_image.png"; %>
    <% if (product != null) { %>
    <header>
        <div class="product-header">
            <duv class="product-img">

                <% if (pictures != null) {
                    for (Picture picture : pictures) {
                        if (picture.getProductID() == product.getId()) {
                            image = picture.getURL();%>
                <% }
                }
                } %>

                <img src="<%= image%>" alt="No photo" width="400" height="300">
            </duv>
            <h1><%= product.getName()%></h1>
            <div class="product-buy">
                <div align="center">
                    <div class="product-bet-container">
                        <div class="product-bet-price">
                            Max bet
                            <% int currentPrice;
                                int age = user.getAge();
                            %>
                            <% if (product.getCurrentPrice() > 0) {
                                currentPrice = product.getCurrentPrice(); %>
                            <h3><%= product.getCurrentPrice()%> $</h3>
                            <% } else {
                                currentPrice = product.getStartPrice(); %>
                            <h3><%= product.getStartPrice()%> $</h3>
                            <% } %>
                        </div>

                        <div class="product-bet-yourprice">
                            Your bet
                            <form onsubmit="return false">
                                <br>
                                <input type="text" id="yourPrice" size="5" maxlength="30" autofocus> $

                            </form>
                        </div>
                        <div class="product-bet">
                            <button onclick="validData($('#yourPrice').val(), <%= currentPrice%>, <%= age%>,
                                <%= product.getId()%>, <%= user.getId()%>);">Bet</button>
                        </div>

                    </div>
                    <div class="product-buy-container">
                        <div class="product-buy-price">
                            Buy it now
                            <h3><%= product.getBuyoutPrice()%> $
                            </h3>
                        </div>
                        <div class="product-buy-button">
                            <button onClick='location.href="product?id=" + <%=product.getId()%> + "&page=buy"'>Buy</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="product-status">
                <div class="product-status-leader">
                    <br>
                    <% if (product.getCurrentBuyerID() > 0) { %>
                    <% List<User> users = (List<User>) request.getAttribute("users");
                    String nickBuyer = null;
                    if (users != null) {%>
                    <% for (User userBuyer : users) {
                        if (product.getCurrentBuyerID() == userBuyer.getId())
                            nickBuyer = userBuyer.getLogin();
                    }%>
                        Leader : <%= nickBuyer %>
                    <% }} else { %>
                        Leader : Not yet.
                    <% } %>
                </div>
                <div class="product-status-closeTime">
                    <br>

                    <% SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");%>
                    Close time : <%= dateFormat.format(product.getEndDate()) %>
                </div>
            </div>
        </div>
    </header>
    <footer>
        <div class="product-footer">
            <h2 align="center">Description</h2>
            <div class="product-description">
                <%= product.getDescription()%>
            </div>
            <br>
            <br><br>
        </div>
    </footer>
    <% } %>
<%@include file="../jspf/user-footer.jspf"%>
</div>
</body>
</html>
