/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url="user";
var loginAction;


function getLots() {
    $.ajax({
        dataType: "html",
        url: url,
        type: "POST",
        data: {
            action: "getLots"
        },
        success: function (html) {
            $('#lots').append(html).html();
        },
        error: function () {
            alert("Error can not get lots");
        }
    });
}

function checkLoginForm(login, password) {
    if (login.length > 0) {
         if (password.length > 0) {
            if (isEmail(login)) {
                loginAction = "loginEmail";
            } else {
                loginAction = "login";
            }
            clickLogin(login, hex_md5(password), loginAction);
        } else alert("Please enter password");
    } else alert("Please enter login");
}

function isEmail(login) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(login);
}

function clickRegister() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "registerForm"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("register")
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
}

function clickLogin(login, password, loginAction) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: loginAction,
            login: login,
            password: password
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#login").val("");
                $("#password").val("");
                window.location.replace("user")
            } else {
                $(data).find('error');
                var text = $(data).find("text").text();
                $("#password").val("");
                alert(text);
            }
        },
        error: function () {
            alert("Error while send login data.");
        }
    });
}

function logOut() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "logOut"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("user")
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
}


function clickFind(find) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "find",
            text: find
        }
    });
}


function createForm(user_name) {
    if (user_name != null && user_name !="" && user_name != "null") {
        document.write("<td>");
        document.write("Welcome ,");
        document.write(user_name);
        document.write("<label onclick=\"\">[ <b>Cabinet</b> ]</Label>");
        document.write("<label onclick=\"logOut();\">[ <b>LogOut</b> ]</Label>");
        document.write("")
        document.write("</td>")
    } else {
        document.write("<form>");
        document.write("<td><input type=\"text\" id=\"login\" size=\"10\" maxlength=\"15\" autofocus required placeholder='login or email'></td>");
        document.write("<td><input type=\"password\" id=\"password\" size=\"10\" maxlength=\"15\" required placeholder='password'></td>");
        document.write("<td><label onclick=\"checkLoginForm($('#login').val(), $('#password').val());\">[ <b>Login</b> ]</label></td>");
        document.write("<td><label onclick=\"clickRegister();\">[ <b>Register</b> ]</Label></td>");
        document.write("</form>");
    }
}

function getCategoryList() {
    $.ajax({
        dataType: "html",
        url: url,
        type: "POST",
        data: {
            action: "getCategoryList"
        },
        success: function (html) {
            $('#navigate').append(html).html();
        },
        error: function () {
            alert("Error can not get lots");
        }
    });
}
