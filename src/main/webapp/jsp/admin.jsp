<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.sumdu.group73.model.objects.*" %>
<%@ page import="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="./js/ladmin.js"></script>
	<title>Admin</title>
</head>
<body>
	<center><h1>ADMIN'S PAGE</h1></center>
	
	<%
		List<User> users = (ArrayList<User>) request.getAttribute("users");
		List<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
    	List<Product> products = (ArrayList<Product>) request.getAttribute("products");
    	List<Picture> pictures = (ArrayList<Picture>) request.getAttribute("pictures");
	 %>
	
	
	<center><h3>USERS</h3></center>
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
	    <td>Ban</td>
	    <td>Activated</td>
	    <td>Admin</td>
	  </tr>
	 <%
		for (User user : users) {
	 %>
	  <tr>
	  	<%
	  		String phone = (user.getPhone() == null) ? "----------" : user.getPhone();
	  	 %>
	    <td><%=user.getId() %></td>
	    <td><%=user.getLogin() %></td>
	    <td><%=user.getName() %></td>		
	    <td><%=user.getSecondName() %></td>
	    <td><%=user.getAge() %></td>	
	    <td><%=user.geteMail() %></td>	
	    <td><%=phone %></td>	
	    <td><%=user.getRegistrationDate() %></td>
	    
	    <td><input type="checkbox" name="ban" <% if (user.isBanned()) { %> checked <%} %> value=<%=user.getId() %> ></td>
	    <td><input type="checkbox" name=<%="activated" %> onclick="return false" <% if (user.isActivated()) { %> checked <%} %>></td>
	    <td><input type="checkbox" name=<%="admin" %> onclick="return false" <% if (user.isAdmin()) { %> checked <%} %>></td>
	  </tr>
		<% } %>
	</table>
	<input type="submit" name="saveUsers" value="Save" />
</form>

<br>

	<center><h3>PRODUCTS</h3></center>
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
	    <td><input type="checkbox" name="deleteCheckBox" value=<%=product.getId() %> ></td>
	  </tr>
		<% } %>
	</table>
	<input type="submit" name="deleteProduct" value="Delete Selected" />
</form>
<br>


	<center><h3>CATEGORIES</h3></center>
<table border="1" style="width:100%">
  <tr>
    <td>ID</td>
    <td>Parent ID</td>
	<td>Name</td>
  </tr>
 <%
	for (Category category : categories) {
 %>
  <tr>
    <td><%=category.getId() %></td>
    <td><%=category.getParentID() %></td>
    <td><%=category.getName() %></td>
  </tr>
	<% } %>
</table>
	
<br>

<form action="admin" method="POST">
	<input type="submit" name="BackButton" value="Back" />
</form>	
	
</body>
</html>