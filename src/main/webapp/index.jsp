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
<%@include file="jspf/index-header.jspf"%>
<div class="container">
    <%@include file="jspf/index-navigation.jspf"%>
    <%@include file="jspf/index-products.jspf"%>
    <%@include file="jspf/user-footer.jspf"%>
</div>
</body>
</html>