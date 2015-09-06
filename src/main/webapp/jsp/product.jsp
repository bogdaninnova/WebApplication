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
    <% Product product = (Product) request.getAttribute("product"); %>
    <% if (product != null) { %>
        <meta name="description" content="<%=product.getName()%>">
        <meta name="keywords" content="<%=product.getDescription()%>">
    <% } %>
    <meta name="author" content="Greenberg Dima">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/style-product.css"/>
    <script src="js/jquery-2.1.3.js"></script>
    <script src="js/product.js"></script>
    <title>Product</title>
    <!--[if lte IE 8]>
    <script type="text/javascript">alert("Your web browser is outdated Please upgrade your browser!");
        window.location.href="http://www.google.com/intl/ru/chrome"</script>
    <![endif]-->
</head>
<body>
<%@include file="../jspf/product-header.jspf" %>
<div class="container-product">
        <% List<Picture> pictures = (List<Picture>) request.getAttribute("pictures");%>
        <% String image = "./images/no_image.png"; %>
    <% if (product != null) { %>
    <header>
        <div class="product-header">
            <div class="product-img" align="center">

                <% if (pictures != null) {
                    for (Picture picture : pictures) {
                        if (picture.getProductID() == product.getId()) {
                            image = picture.getURL();%>
                <% }
                }
                } %>
                <img src="<%= image%>" alt="No photo" height="100%">
            </div>
            <div class="product-title">
                <% if (product.getName().length() >= 100) { %>
                    <p style="font-size: 14pt;"><b><%= product.getName()%></b></p>
                <% } else { %>
                    <p style="font-size: 16pt;"><b><%= product.getName()%></b></p>
                    <br>
                <% } %>
            </div>
            <% if (product.isActive()) { %>
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
                            <button onclick="validData($('#yourPrice').val(), <%= currentPrice%>, <%=product.getBuyoutPrice()%>,<%= age%>,
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
                    <% SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm");%>
                    Close time : <%= dateFormat.format(product.getEndDate()) %>
                </div>
            </div>
            <% } else { %>
            <div class="product-status">
                <div class="product-status-leader">
                    <% if (product.getCurrentBuyerID() > 0) { %>
                    <% List<User> users = (List<User>) request.getAttribute("users");
                        String nickBuyer = null;
                        if (users != null) {%>
                    <% for (User userBuyer : users) {
                        if (product.getCurrentBuyerID() == userBuyer.getId())
                            nickBuyer = userBuyer.getLogin();
                    }%>
                    <p>Buyer : <%= nickBuyer %></p>
                    <p>Price : <%= product.getBuyoutPrice()%> $</p>
                    <p>Lot status : closed</p>
                    <% }} %>

                </div>
            </div>
            <% } %>
        </div>
    </header>
    <footer>
        <br>
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
