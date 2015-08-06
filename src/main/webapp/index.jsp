<%@ page import="ua.sumdu.group73.model.objects.Category" %>
<%@ page import="java.util.List" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/navigation.css"/>
    <script src="js/bootstrap.js"></script>
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
<div class="container">
    <div class="form-top" align="right">

        <tr>
            <script>var user_name = '<%= session.getAttribute("username") %>';
            createForm(user_name);</script>
        </tr>
    </div>
    <header>
        <div id="block-logo"><img src="images/logo.png" width="80" height="80" alt="Logo"></div>
        <div id="block-contact">
            tel. XXX XXX XX XX<br>
            tel. XXX XXX XX XX<br><br><br>
            group7.3@sumy.ua
        </div>
        <div id="block-name-site">
            <h1 align="center"><strong>AUCTION</strong></h1>
        </div>
    </header>
</div>


<div class="container">

    <section class="container-section-nav">
        <header>
            <div align="center">
                <form>
                    <br>
                    <input type="text" id="find" size="7" maxlength="30" required>
                    <button onclick="clickFind($('#find').val());">find</button>
                </form>
            </div>
        </header>

        <%--<ul class="topnav">--%>
        <%--<li><a href="user">Home</a></li>--%>
        <%--<li><a href="#">CATALOG 1</a>--%>
        <%--<ul>--%>
        <%--<li><a href="#">SubCatalog 1</a></li>--%>
        <%--<li><a href="#">SubCatalog 2</a></li>--%>
        <%--<li><a href="#">SubCatalog 3</a></li>--%>
        <%--<li><a href="#">SubCatalog 4</a></li>--%>
        <%--<li><a href="#">SubCatalog 5</a></li>--%>
        <%--<li><a href="#">SubCatalog 6</a>--%>
        <%--<ul>--%>
        <%--<li><a href="#">Directory 1</a></li>--%>
        <%--<li><a href="#">Directory 1</a></li>--%>
        <%--<li><a href="#">Directory 1</a></li>--%>
        <%--</ul>--%>
        <%--</li>--%>
        <%--<li><a href="#">SubCatalog 7</a></li>--%>
        <%--</ul>--%>
        <%--</li>--%>
        <%--</ul>--%>

        <nav id="navigate">

            <div align="center">
                <ul class="topnav">
                    <li><a href="user">Home</a></li>
                    <% List<Category> cat = (List<Category>) request.getAttribute("list");%>
                    <% if (cat != null) { %>
                    <% for (Category category : cat) { %>
                    <% if (category.getParentID() == 0) { %>
                    <li><a href="#"><%= category.getName()%>
                    </a>
                        <% for (Category subCategory : cat) { %>
                        <% if (category.getId() == subCategory.getParentID()) { %>
                        <ul>
                            <li><a href="#"><%= subCategory.getName()%>
                            </a></li>
                        </ul>
                        <% } %>
                        <% } %>
                    </li>
                    <% } %>
                    <% }
                    } %>
                </ul>
            </div>
        </nav>
        <footer>
            <h4 align="center">Options:</h4>

            <div align="center">
                <form>
                    Price ($):<br>
                    <input type="text" id="min_price" size="3" maxlength="10">
                    - <input type="text" id="max_price" size="3" maxlength="10"><br>

                    <p><b>Condition:</b><Br>
                        <input type="radio" name="radio" value="new"> new<Br>
                        <input type="radio" name="radio" value="uses"> used<Br>
                    </p>
                    <button on onclick="">Sort</button>
                </form>
            </div>
        </footer>
    </section>


    <aside class="container-section-lot">
        <hesder>
            <h2 align="center">List of lots</h2>

        </hesder>
        <div align="center">
            <article id="lots">
                <script>getLots();</script>

            </article>

        </div>
    </aside>


</div>

<footer>
    <div class="container" align="right">
        <p>Group 7.3</p>
    </div>
</footer>

</body>
</html>