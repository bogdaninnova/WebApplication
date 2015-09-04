<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.sumdu.group73.model.objects.*" %>
<%@ page import="ua.sumdu.group73.model.*" %>
<%@ page import="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="./css/style-admin.css"/>
	<script src="./js/bootstrap.js"></script>
    <script src="./js/jquery-2.1.3.js"></script>
	<script src="js/admin.js"></script>
	<title>Admin</title>
</head>
<body>

 <%@include file="../jspf/admin-header.jspf" %>
		
	<%
		List<User> users = (ArrayList<User>) session.getAttribute("users");
		List<Category> categories = (ArrayList<Category>) session.getAttribute("categories");
    	List<Product> products = (ArrayList<Product>) session.getAttribute("products");
    	List<Picture> pictures = (ArrayList<Picture>) session.getAttribute("pictures");
	 %>
	
	<%! java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMM yyyy hh:mm");%>

	<%!
		public static String cut(Object o, int lenght) {
			String str = o.toString();
			if (o.toString().length() > lenght) 
				return str.substring(0, lenght) + "...";
			return str;
		}
	 %>
	 
	<%!
		public static User getUserByID(List<User> list, int ID) {
			for (User user : list)
				if (user.getId() == ID)
					return user;
			return null;
	 	}
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
		    <td align="center">ID</td>
		    <td align="center">Login</td>
		    <td align="center">Name</td>		
		    <td align="center">Second Name</td>
		    <td align="center">Age</td>
		    <td align="center">Mail</td>
		    <td align="center">Phone</td>
		    <td align="center">Registration date</td>
		    <td align="center">Ban</td>
		    <td align="center">Activated</td>
		    <td align="center">Admin</td>
		  </tr>
		 <%
			for (User usver : users) {
		 %>
		  <tr>
		  	<%
		  		String phone = (usver.getPhone() == null) ? "----------" : usver.getPhone();
		  	 %>
		    <td align="center"><%=cut(usver.getId(), 6) %></td>
		    <td align="center"><a href="lookUser?id=<%= usver.getId() %>"><%=cut(usver.getLogin(), 15) %></a></td>
		    <td align="center"><%=cut(usver.getName(), 15) %></td>		
		    <td align="center"><%=cut(usver.getSecondName(), 15) %></td>
		    <td align="center"><%=cut(usver.getAge(), 3) %></td>	
		    <td align="center"><%=cut(usver.geteMail(), 30) %></td>	
		    <td align="center"><%=cut(phone, 15) %></td>	
		    <td align="center"><%=dateFormat.format(usver.getRegistrationDate()) %></td>
		    <td align="center"><input type="checkbox" name="ban" <% if (usver.isBanned()) { %> checked <%} %> value=<%=usver.getId() %> ></td>
		    <td align="center"><input type="checkbox" name=<%="activated" %> onclick="return false" <% if (usver.isActivated()) { %> checked <%} %>></td>
		    <td align="center"><input type="checkbox" name=<%="admin" %> onclick="return false" <% if (usver.isAdmin()) { %> checked <%} %>></td>
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
		    <td align="center">ID</td>
		    <td align="center">Name</td>
		    <td align="center">Start<br>Price</td>
		    <td align="center">Buyout<br>Price</td>
		    <td align="center">Current<br>Price</td>
		    <td align="center">Start Date</td>
		    <td align="center">End Date</td>
		    <td align="center">Seller</td>
		    <td align="center">Buyer</td>
		    <td align="center">Status</td>
			<td align="center">Delete</td>
		  </tr>
		 <%
			for (Product product : products) {
		 %>
		  <tr>
		    <td align="center"><%=cut(product.getId(), 6) %></td>
		    <td align="center"><a href="product?id=<%= product.getId() %>"><%=cut(product.getName(), 10) %></a></td>
		    <td align="center"><%=cut(product.getStartPrice(), 6) %></td>
		    <td align="center"><%=cut(product.getBuyoutPrice(), 6) %></td>
		    <td align="center"><%=cut(product.getCurrentPrice(), 6) %></td>
		    <td align="center"><%=dateFormat.format(product.getStartDate()) %></td>
		    <td align="center"><%=dateFormat.format(product.getEndDate()) %></td>
		    <td align="center"><a href="lookUser?id=<%= product.getSellerID() %>"><%=cut(getUserByID(users, product.getSellerID()).getLogin(), 15) %></a></td>

			<% if (product.getCurrentBuyerID() != 0) { %>
			    <td align="center"><a href="lookUser?id=<%= product.getCurrentBuyerID() %>"><%=cut(getUserByID(users, product.getCurrentBuyerID()).getLogin(), 15) %></a></td>
			<% } else { %>
			    <td align="center">-</td>
			<% } %>
		    
		    <td align="center"><% if (product.isActive()) {%> active <% } else { %> deactivated <% } %> </td>
		    <td align="center"><input type="checkbox" name="deleteCheckBox" value=<%=product.getId() %> ></td>
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