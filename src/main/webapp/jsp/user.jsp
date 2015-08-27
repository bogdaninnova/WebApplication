<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.sumdu.group73.model.objects.User" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/navigation.css"/>
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
    <script src="js/user.js"></script>

    <title>User cabinet</title>
</head>
<body>
<%@include file="../jspf/user-header.jspf" %>

<div class="container">
  <%@include file="../jspf/user-navigation.jspf"%>

  <% if (request.getAttribute("showContent").equals("information")) { %>
      <%@include file="../jspf/user-information.jspf"%>
   <% } else if (request.getAttribute("showContent").equals("changeUserData")) { %>
      <%@include file="../jspf/user-changeData.jspf"%>
  <% } else if (request.getAttribute("showContent").equals("changePassword")) { %>
      <%@include file="../jspf/user-changePassword.jspf"%>
  <% } else if (request.getAttribute("showContent").equals("changeUser")) { %>
      <%@include file="../jspf/user-changeUser.jspf"%>
  <% } else if (request.getAttribute("showContent").equals("changeEmail")) { %>
      <%@include file="../jspf/user-changeEmail.jspf"%>
  <% } else if (request.getAttribute("showContent").equals("clickAddLotPage")) { %>
    <%@include file="../jspf/user-addLot.jspf"%>
    <% } else if (request.getAttribute("showContent").equals("showLotsPurchased")) { %>
    <%@include file="../jspf/user-showLotsPurchased.jspf"%>
    <% } else if (request.getAttribute("showContent").equals("followingProducts")) { %>
    <%@include file="../jspf/user-showFollowingProducts.jspf"%>
    <% } else if (request.getAttribute("showContent").equals("clickSoldGoodsPage")) { %>
    <%@include file="../jspf/user-soldGoodsPage.jspf"%>
    <% } else if (request.getAttribute("showContent").equals("clickGoodsForSalePage")) { %>
    <%@include file="../jspf/user-goodsForSalePage.jspf"%>
    <% } %>

<%@include file="../jspf/user-footer.jspf"%>
</div>
</body>
</html>
