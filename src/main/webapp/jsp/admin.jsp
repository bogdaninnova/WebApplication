<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.sumdu.group73.model.objects.*" %>
<%@ page import="java.util.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="css/style-admin.css"/>
	<title>Admin</title>
</head>
<body>
	<center><h1>ADMIN'S PAGE</h1></center>
	
	<%
		List<User> users = (ArrayList<User>) request.getAttribute("users");
		List<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
    	List<Product> products = (ArrayList<Product>) request.getAttribute("products");
    	List<Picture> pictures = (ArrayList<Picture>) request.getAttribute("pictures");
	
	List<Category> list = new ArrayList<Category>();
		
		list.add(new Category(1, "1"));
		list.add(new Category(2, "2"));
		list.add(new Category(3, "3"));
		list.add(new Category(31, 3, "31"));
		list.add(new Category(32, 3, "32"));
		list.add(new Category(33, 3, "33"));
		list.add(new Category(4, "4"));
		list.add(new Category(5, "5"));
		list.add(new Category(51, 5, "51"));
		list.add(new Category(52, 5, "52"));
		list.add(new Category(521, 52, "521"));
		list.add(new Category(522, 52, "522"));
		list.add(new Category(523, 52, "523"));
		list.add(new Category(524, 52, "524"));
		list.add(new Category(5241, 524, "5241"));
		list.add(new Category(53, 5, "53"));
		list.add(new Category(54, 5, "54"));
		list.add(new Category(6, "6"));
	
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





<%=printCategories(categories) %>
<br>


<%!
	 public static String printCategories(List<Category> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"multi-derevo\">\n");
		sb.append("<a href=\"#\">CATEGORIES</a>\n");
		sb.append("<ul>\n");
		Map<Category, Boolean> map = new HashMap<Category, Boolean>();
		for (Category category : list)
			map.put(category, true);

		for (Category category : map.keySet())
			if (map.get(category) && (category.getParentID() == 0))
				printSubcategories(map, category, sb);
		sb.append("</ul>\n");
		sb.append("</div>\n");
		System.out.println(sb);
		return sb.toString();
	}
%>
	
<%!
	private static void printSubcategories(Map<Category, Boolean> map, Category category, StringBuilder sb) {
		
		map.put(category, false);
		boolean hasSubcategory = getSubcategory(map, category) != null;
		
		sb.append("<li>\n");
		
		sb.append("<span><a href=\"#" + category.getId() + "\">" + category.getName() + "</a></span>\n");
		
		if (hasSubcategory)
			sb.append("<ul>\n");
		
		while (getSubcategory(map, category) != null)
			printSubcategories(map, getSubcategory(map, category), sb);

		if (hasSubcategory)
			sb.append("</ul>\n");
			
		sb.append("</li>\n");
		
	}
%>
	
<%!
	private static Category getSubcategory(Map<Category, Boolean> map, Category category) {
		for (Category cat : map.keySet())
			if ((map.get(cat)) && cat.getParentID() == category.getId())
				return cat;
		return null;
	}
%>



<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js" type="text/javascript"></script>
<script>
/*<![CDATA[*/
/*
Построение дерева по готовому HTML списку.

Выделяем узлы имющие поддеревья и добавляем у ним метку.
Функция определяет поведение узлов дерева при клике на них. 
 - Изменяет состояние маркера раскрытия (открыт/закрыт).
 - Узлы содержащие в себе другие узлы, по клику разворачиваются 
  или сворачиваются, в зависимости от текущего состояния. 
 - При переходе с одного узла на другой снимается выделение (.current)
  и пеходит на выбранный узел.
 - Определяет последний узел с поддеревом и скрывает соединительную 
  линию до следующего узла этого уровня.
*/
$(document).ready(function () {
/* Расставляем маркеры на узлах, имющих внутри себя поддерево.
  Выбираем элементы 'li' которые имеют вложенные 'ul', ставим для них 
  маркер, т.е. находим в этом 'li' вложенный тег 'a' 
  и в него дописываем маркер '<em class="marker"></em>'.
  a:first используется, чтобы узлам ниже 1го уровня вложенности 
  маркеры не добавлялись повторно. 
*/
$('#multi-derevo li:has("ul")').find('a:first').prepend('<em class="marker"></em>');
// вешаем событие на клик по ссылке
$('#multi-derevo li span').click(function () {
  // снимаем выделение предыдущего узла
  $('a.current').removeClass('current'); 
  var a = $('a:first',this.parentNode);
  // Выделяем выбранный узел
  //было a.hasClass('current')?a.removeClass('current'):a.addClass('current');
  a.toggleClass('current');
  var li=$(this.parentNode);
  /* если это последний узел уровня, то соединительную линию к следующему
    рисовать не нужно */  
  if (!li.next().length) {
    /* берем корень разветвления <li>, в нем находим поддерево <ul>,
     выбираем прямых потомков ul > li, назначаем им класс 'last' */
    li.find('ul:first > li').addClass('last');
  } 
  // анимация раскрытия узла и изменение состояния маркера
  var ul=$('ul:first',this.parentNode);// Находим поддерево
  if (ul.length) {// поддерево есть
   ul.slideToggle(300); //свернуть или развернуть
   // Меняем сосотояние маркера на закрыто/открыто
   var em=$('em:first',this.parentNode);// this = 'li span'
   // было em.hasClass('open')?em.removeClass('open'):em.addClass('open');
   em.toggleClass('open');
 }
});
})
/*]]>*/
</script>



<form action="admin" method="POST">
	<input type="submit" name="BackButton" value="Back" />
</form>	
	
</body>
</html>