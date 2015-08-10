<%@ page import="ua.sumdu.group73.model.objects.Product" %>
<%@ page import="ua.sumdu.group73.model.objects.Picture" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.sumdu.group73.model.objects.User" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style-product.css"/>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery-2.1.3.js"></script>
    <script src="js/product.js"></script>
    <title>Product</title>
</head>
<body>
<div class="container-product">
    <% Product product = (Product) request.getAttribute("product");
        List<Picture> pictures = (List<Picture>) request.getAttribute("pictures");
    %>
    <% if (product != null) { %>
    <header>
        <div class="product-header">
            <duv class="product-img">
                <% if (pictures != null) {
                    String image = "";
                    int count = 0;
                    for (Picture picture : pictures) {
                        if (picture.getProductID() == product.getId()) {
                            if (count == 0) {
                                image = picture.getURL();
                                count += 1;
                            }
                %>
                <img src="<%= image%>" alt="No photo" width="400" height="300">
                <% } else { %>
                <img src="./images/no_image.png" alt="No photo" width="400" height="300">
                <% }
                } %>
            </duv>
            <h1><%= product.getName()%></h1>
            <div class="product-buy">
                <div align="center">
                    <div class="product-bet-container">
                        <div class="product-bet-price">
                            Max bet
                            <% if (product.getCurrentPrice() > 0) {%>
                            <h3><%= product.getCurrentPrice()%> $</h3>
                            <% } else { %>
                            <h3><%= product.getStartPrice()%> $</h3>
                            <% } %>
                        </div>
                        <div class="product-bet-yourprice">
                            Your bet
                            <form>
                                <br>
                                <input type="text" id="yourPrice" size="5" maxlength="30" autofocus> $
                            </form>
                        </div>
                        <div class="product-bet">
                            <button onclick="">Bet</button>
                        </div>
                    </div>
                    <div class="product-buy-container">
                        <div class="product-buy-price">
                            Buy it now
                            <h3><%= product.getBuyoutPrice()%> $
                            </h3>
                        </div>
                        <div class="product-buy-button">
                            <button onclick="">Buy</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="product-status">
                <div class="product-status-lider">
                    <br>
                    <% if (product.getCurrentBuyerID() > 0) { %>
                    <% List<User> users = (List<User>) request.getAttribute("users");
                    String nameBuyer = null;
                    if (users != null) {%>
                    <% for (User userBuyer : users) {
                        if (product.getCurrentBuyerID() == userBuyer.getId())
                            nameBuyer = userBuyer.getName();
                    }%>
                        Leader : <%= nameBuyer %>
                    <% }} else { %>
                        Leader : Not yet.
                    <% } %>
                </div>
                <div class="product-status-closeTime">
                    <br>
                    Close time : <%= product.getEndDate() %>
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
            <div class="product-back">
                <label onclick="clickBack();"><b>[ Back ]</b></label>
            </div>
            <br><br>
        </div>
    </footer>
    <% }
    } %>
</div>
</body>
</html>
