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
    <title>Looking User</title>
</head>
<body>

	<%@include file="../jspf/looking-user-header.jspf" %>

	<% 
		User user = (User) request.getAttribute("lookingUser");
		List<Product> buying = (ArrayList<Product>) request.getAttribute("buying");
		List<Product> solding = (ArrayList<Product>) request.getAttribute("solding");
		List<User> users = (ArrayList<User>) request.getAttribute("users");
	
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
	
	<%! java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMM yyyy hh:mm");%>

	<%!
		public static String cut(Object o, int lenght) {
			String str = o.toString();
			if (o.toString().length() > lenght) 
				return str.substring(0, lenght) + "...";
			return str;
		}
	 %>
	 
	 <%
		String phone =
			(user.getPhone() == null) ? "----------" : user.getPhone();
	%>
	 
<div class="tableStyle">
	<center><h1>USERS DATA</h1></center>
	<center>
	<table border="1" style="width:50%">
	  <tr>
	    <td align="center">ID</td>
	    <td align="center"><%=cut(user.getId(), 10) %></td>
	  </tr>
	  <tr>
	    <td align="center">LOGIN</td>
	    <td align="center"><%=cut(user.getLogin(), 30) %></td>
	  </tr>
	  <tr>
	    <td align="center">NAME</td>
	    <td align="center"><%=cut(user.getName(), 30) %></td>	
	  </tr>
	  <tr>
	   <td align="center">SECOND NAME</td>
	    <td align="center"><%=cut(user.getSecondName(), 30) %></td>
	  </tr>
	  <tr>
	    <td align="center">AGE</td>
	    <td align="center"><%=cut(user.getAge(), 5) %></td>	
	  </tr>
	  <tr>
	    <td align="center">MAIL</td>
	    <td align="right"><%=cut(user.geteMail(), 50) %></td>	
	  </tr>
	  <tr>
	    <td align="center">PHONE</td>
	    <td align="center"><%=cut(phone, 25) %></td>	
	  </tr>
	  <tr>
	    <td align="center">REGISTRATION DATE</td>
	    <td align="center"><%=dateFormat.format(user.getRegistrationDate()) %></td>
	  </tr>
	  <tr>
	    <td align="center">STATUS</td>
	    <% if (user.isBanned()) { %>
	    	<td align="center">banned</td>
	    <% } else if (user.isAdmin()) { %>
	    	<td align="center">admin</td>
	    <% } else if (!user.isActivated()) { %>
	  	  <td align="center">don't activated</td>
	  	<% } else { %>
	    	<td align="center">user</td>
	    <% } %>
	  </tr>
	</table>
	</center>
	<br>
</div>
	 
<br>

<div class="tableStyle">
	<center><h1>USERS PURCHASES</h1></center>
	<form action="lookUser" method="POST">
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
			for (Product product : buying) {
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
		    <td align="center"><input type="checkbox" name="deleteCheckBoxBUY" value=<%=product.getId() %> ></td>
		  </tr>
			<% } %>
		</table>
		<br>
		<input type="hidden" name="id" value="<%=user.getId()%>" />
		<center><input type="submit" name="deleteProductBUY" value="Delete Selected" /></center>
		<br>
	</form>
</div>

<br>



<div class="tableStyle">
	<center><h1>USERS SALE</h1></center>
	<form action="lookUser" method="POST">
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
			for (Product product : solding) {
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
		    <td align="center"><input type="checkbox" name="deleteCheckBoxSELL" value=<%=product.getId() %> ></td>
		  </tr>
			<% } %>
		</table>
		<br>
		<input type="hidden" name="id" value="<%=user.getId()%>" />
		<center><input type="submit" name="deleteProductSELL" value="Delete Selected" /></center>
		<br>
	</form>
</div>
	

</body>
</html>