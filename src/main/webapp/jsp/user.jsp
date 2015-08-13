<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../jspf/user-header.jspf" %>

<div class="container">
  <%@include file="../jspf/user-navigation.jspf"%>

  <%--information about users--%>
  <h2> Information about user</h2>
  <% if (user != null) { %>
  <table>
    <tr>
      <td>Login/nick :</td>
      <td><%= user.getLogin()%></td>
    </tr>
    <tr>
      <td>Name :</td>
      <td><%= user.getName()%></td>
    </tr>
    <tr>
      <td>Second name :</td>
      <td><%= user.getSecondName()%></td>
    </tr>
    <tr>
      <td>Age :</td>
      <td><%= user.getAge()%></td>
    </tr>
    <tr>
      <td>E-mail :</td>
      <td><%= user.geteMail()%></td>
    </tr>
    <tr>
      <td>Phone :</td>
      <% if (user.getPhone() != null && user.getPhone() != "") { %>
      <td><%= user.getPhone()%></td>
      <% } else { %>
      <td>not yet</td>
      <% } %>
    </tr>
    <tr>
      <td>Registration Date :</td>
      <td><%= user.getRegistrationDate()%></td>
    </tr>
  </table>
  <% } %>

<%@include file="../jspf/user-footer.jspf"%>
</div>
</body>
</html>
