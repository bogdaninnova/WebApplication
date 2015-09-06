<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Greenberg Dima">
    <link rel="stylesheet" type="text/css" href="css/style-register.css"/>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery-2.1.3.js"></script>
    <script src="js/md5.js"></script>
    <script src="js/register.js"></script>
    <title>Registration form</title>
</head>
<body>
<div class="container">
    <h2 align="center">Please register</h2>
    <table align="center">
        <form>
            <tr>
                <td><label>*</label></td>
                <td>Login:</td>
                <td><input type="text" id="login" size="16" maxlength="30" placeholder="login" autofocus required></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Password:</td>
                <td><input type="password" id="password" size="16" maxlength="128" placeholder="password"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Confirm:</td>
                <td><input type="password" id="confirmPassword" size="16" maxlength="128" placeholder="confirm"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Name:</td>
                <td><input type="text" id="firstName" size="16" maxlength="50" placeholder="name"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Second Name:</td>
                <td><input type="text" id="secondName" size="16" maxlength="50" placeholder="second name"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>Date birthday:</td>
                <td><input type="date" id="age" size="16" maxlength="50" placeholder="birthday"></td>
            </tr>
            <tr>
                <td><label>*</label></td>
                <td>E-mail:</td>
                <td><input type="text" id="email" size="16" maxlength="50" placeholder="e-mail"></td>
            </tr>
            <tr>
                <td></td>
                <td>Phone number:</td>
                <td><input type="text" id="phone" size="16" maxlength="20" placeholder="phone"></td>
            </tr>
            <tr>
	            <td></td>
	            <td>
					<img src="/WebApplication/jcaptcha.jpg" id="captchaImage" onClick="refreshCaptcha();">
	            </td>
	            <td>
	            	Captcha:<br>
					<input type="text" id="captcha" size="16" maxlength="20" placeholder="captcha">
	            </td>
	        </tr>
        </form>

        <tr>
            <td></td>
            <td>
                <button onclick="clickBack();"> Back</button>
            </td>
            <td>
                <button onclick="validData($('#login').val(), $('#password').val(), $('#confirmPassword').val(), $('#firstName').val(), $('#secondName').val(), $('#age').val(), $('#email').val(), $('#phone').val(), $('#captcha').val());">
                    Sign up
                </button>
            </td>
        </tr>
    </table>
    <br><label>&nbsp &nbsp * - Required fields.</label>
</div>
</body>
</html>