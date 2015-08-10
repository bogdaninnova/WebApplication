<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.sumdu.group73.model.objects.*" %>
<%@ page import="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="./js/login.js"></script>
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
<table border="1" style="width:100%">
  <tr>
    <td>ID</td>
    <td>Login</td>
    <td>Name</td>		
    <td>Second Name</td>
    <td>Age</td>
    <td>Mail</td>
    <td>Login</td>
  </tr>
 <%
	for (User user : users) {
 %>
  <tr>
    <td><%=user.getId() %></td>
    <td><%=user.getLogin() %></td>
    <td><%=user.getName() %></td>		
    <td><%=user.getSecondName() %></td>
    <td><%=user.getAge() %></td>	
    <td><%=user.geteMail() %></td>	
    <td><%=user.getPhone() %></td>	
  </tr>
	<% } %>
</table>

<br>

	<center><h3>PRODUCTS</h3></center>
<table border="1" style="width:100%">
  <tr>
    <td>ID</td>
    <td>Name</td>
    <td>Description</td>
    <td>StartPrice</td>
    <td>getBuyoutPrice</td>
    <td>CurrentPrice</td>
    <td>Start Date</td>
    <td>End Date</td>
    <td>Seller ID</td>
    <td>Current Buyer ID</td>
    <td>is Active</td>

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
    <td><%=product.isActive() %></td>

  </tr>
	<% } %>
</table>

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

<button onclick="clickBack();">Back</button>
	
	
</body>
</html>