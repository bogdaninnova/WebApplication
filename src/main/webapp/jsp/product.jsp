<%@ page import="ua.sumdu.group73.model.objects.Product" %>
<%@ page import="ua.sumdu.group73.model.objects.Picture" %>
<%@ page import="java.util.List" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style-product.css"/>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery-2.1.3.js"></script>
    <title>Product</title>
</head>
<body>
  <div class="container-product">
      <% Product product = (Product) request.getAttribute("product");
          List<Picture> pictures = (List<Picture>) request.getAttribute("pictures");
      %>
      <% if (product != null) { %>
      <header>
          <div class="poduct-header">
            <duv class="product-img">
                <% if (pictures != null) {
                    String image = "";
                for(Picture picture : pictures) {
                    if(picture.getProductID() == product.getId()) {
                        image = picture.getURL();
                %>
                <img src="<%= image%>" alt="No photo" width="400" height="300">
                <% } } %>
            </duv>
            <h1><%= product.getName()%></h1>
          </div>
      </header>
      <footer>
        <div class="product-footer">
            <h2 align="center">Description</h2>
            <%= product.getDescription()%>
            <div align="right">
                <label onclick=""><H2>[ Back ] </H2></label>
            </div>
        </div>
      </footer>
      <% } } %>
  </div>
</body>
</html>
