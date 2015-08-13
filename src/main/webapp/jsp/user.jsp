<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../jspf/user-header.jspf" %>

<div class="container">
  <%@include file="../jspf/user-navigation.jspf"%>

  <% if (request.getAttribute("showContent").equals("information")) { %>
      <%@include file="../jspf/user-information.jspf"%>
   <% } %>

<%@include file="../jspf/user-footer.jspf"%>
</div>
</body>
</html>
