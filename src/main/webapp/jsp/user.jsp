<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
  <% } %>

<%@include file="../jspf/user-footer.jspf"%>
</div>
</body>
</html>
