<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.sumdu.group73.model.objects.*" %>
<%@ page import="ua.sumdu.group73.model.*" %>
<%@ page import="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/style-admin.css"/>
	<script src="./js/bootstrap.js"></script>
    <script src="./js/jquery-2.1.3.js"></script>
	<script src="js/admin.js"></script>
	<title>Admin</title>
</head>
<body>

<%@include file="../jspf/user-header.jspf" %>
		
	<%
		List<User> users = (ArrayList<User>) request.getAttribute("users");
		List<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
    	List<Product> products = (ArrayList<Product>) request.getAttribute("products");
    	List<Picture> pictures = (ArrayList<Picture>) request.getAttribute("pictures");
	 %>
	
<br>

<div class="tableStyle">
	<center>
		<h1>CATEGORIES</h1>
		<br>
		<button onclick="sendCategoryData('create', $('#categoryName').val(), window.location.href);"> Create Child Category </button>
		<button onclick="sendCategoryData('createRoot', $('#categoryName').val(), window.location.href);"> Create Root Category  </button>
		<br><br>
		<button onclick="sendCategoryData('change', $('#categoryName').val(), window.location.href);"> Change </button>
		<button onclick="sendCategoryData('delete', $('#categoryName').val(), window.location.href);"> Delete </button>
		<br><br>
		Categories name: <input type="text" id="categoryName" size="10" maxlength="30">
		<br><br><br>
		<%= CategoriesTree.printCategories(categories) %>
		<br><br>
	</center>
</div>

<br>

<div class="tableStyle">
	<center><h1>USERS</h1></center>
	<form action="admin" method="POST">
		<table border="1" style="width:100%">
		  <tr>
		    <td>ID</td>
		    <td>Login</td>
		    <td>Name</td>		
		    <td>Second Name</td>
		    <td>Age</td>
		    <td>Mail</td>
		    <td>Phone</td>
		    <td>Registration date</td>
		    <td>View</td>
		    <td>Ban</td>
		    <td>Activated</td>
		    <td>Admin</td>
		  </tr>
		 <%
			for (User usver : users) {
		 %>
		  <tr>
		  	<%
		  		String phone = (usver.getPhone() == null) ? "----------" : usver.getPhone();
		  	 %>
		    <td><%=usver.getId() %></td>
		    <td><%=usver.getLogin() %></td>
		    <td><%=usver.getName() %></td>		
		    <td><%=usver.getSecondName() %></td>
		    <td><%=usver.getAge() %></td>	
		    <td><%=usver.geteMail() %></td>	
		    <td><%=phone %></td>	
		    <td><%=usver.getRegistrationDate() %></td>
		    <td><a href="user?id=<%= usver.getId() %>">View</a></td>
		    <td><input type="checkbox" name="ban" <% if (usver.isBanned()) { %> checked <%} %> value=<%=usver.getId() %> ></td>
		    <td><input type="checkbox" name=<%="activated" %> onclick="return false" <% if (usver.isActivated()) { %> checked <%} %>></td>
		    <td><input type="checkbox" name=<%="admin" %> onclick="return false" <% if (usver.isAdmin()) { %> checked <%} %>></td>
		  </tr>
			<% } %>
		</table>
		<br>
		<center><input type="submit" name="saveUsers" value="Ban Selected" /></center>
		<br>
	</form>
</div>
<br>

<div class="tableStyle">
	<center><h1>PRODUCTS</h1></center>
	<form action="admin" method="POST">
		<table border="1" style="width:100%">
		  <tr>
		    <td>ID</td>
		    <td>Name</td>
		    <td>Description</td>
		    <td>StartPrice</td>
		    <td>BuyoutPrice</td>
		    <td>CurrentPrice</td>
		    <td>Start Date</td>
		    <td>End Date</td>
		    <td>Seller ID</td>
		    <td>Buyer ID</td>
		    <td>Status</td>
		    <td>View</td>
			<td>Delete</td>
		  </tr>
		 <%
			for (Product product : products) {
		 %>
		  <tr>
		    <td><%=product.getId() %></td>
		    <td><%=product.getName() %></td>
		    <td><%=product.getDescription() %></td>
		    <td><%=product.getStartPrice() %></td>
		    <td><%=product.getBuyoutPrice() %></td>
		    <td><%=product.getCurrentPrice() %></td>
		    <td><%=product.getStartDate() %></td>
		    <td><%=product.getEndDate() %></td>
		    <td><%=product.getSellerID() %></td>
		    <td><%=product.getCurrentBuyerID() %></td>
		    <td><% if (product.isActive()) {%> active <% } else { %> disactive <% } %> </td>
		    <td><a href="product?id=<%= product.getId() %>">View</a></td>
		    <td><input type="checkbox" name="deleteCheckBox" value=<%=product.getId() %> ></td>
		  </tr>
			<% } %>
		</table>
		<br>
		<center><input type="submit" name="deleteProduct" value="Delete Selected" /></center>
		<br>
	</form>
</div>
<br>

<script>
$(document).ready(function () {
$('#multi-derevo li:has("ul")').find('a:first').prepend('<em class="marker"></em>');
$('#multi-derevo li span').click(function () {
  $('a.current').removeClass('current'); 
  var a = $('a:first',this.parentNode);
  a.toggleClass('current');
  var li=$(this.parentNode);
  if (!li.next().length) {
    li.find('ul:first > li').addClass('last');
  } 
  var ul=$('ul:first',this.parentNode);
  if (ul.length) {
   ul.slideToggle(300);
   var em=$('em:first',this.parentNode);
   em.toggleClass('open');
 }
});
});
</script>
	
</body>
</html>
