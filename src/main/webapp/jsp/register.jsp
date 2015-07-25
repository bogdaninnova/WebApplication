<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/style-register.css"/>
    <script src="../js/bootstrap.js"></script>
    <script src="../js/jquery-2.1.3.js"></script>
    <script src="../js/md5.js"></script>
    <script src="../js/register.js"></script>
    <title>Registration form</title>
</head>
<body>
<div class="container">
    <h2 align="center">Please register</h2>
    <form>
        <table align="center">
            <tr>
                <td><label>*</label></td>
                <td>Login:</td>
                <td><input type="text" id="login" size="10" maxlength="30" autofocus required></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Password:</td>
                <td><input type="password" id="password" size="10" maxlength="128"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Confirm:</td>
                <td><input type="password" id="confirmPassword" size="10" maxlength="128"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Name:</td>
                <td><input type="text" id="firstName" size="10" maxlength="50"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Second Name:</td>
                <td><input type="text" id="secondName" size="10" maxlength="50"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Date birthday:</td>
                <td><input type="date" id="age" size="10" maxlength="50"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>E-mail:</td>
                <td><input type="text" id="email" size="10" maxlength="50"></td>
            </tr>
            <tr>
                <td></td>
                <td>Phone number:</td>
                <td><input type="text" id="phone" size="10" maxlength="20"></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>
                    <button onclick="validData($('#login').val(), $('#password').val(), $('#confirmPassword').val(), $('#firstName').val(), $('#secondName').val(), $('#age').val(), $('#email').val(), $('#phone').val());">Sign up</button>
                </td>
            </tr>

        </table>
    </form>
    <br><label>* - Required fields.</label>
</div>
</body>
</html>