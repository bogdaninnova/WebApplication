/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url="user";
var loginAction;

function validData(login, password) {
    if (login.length > 0 && password.length > 0) {
        if (validLogin(login)) {
            loginAction = "loginEmail";
        } else {
            loginAction = "login";
        }
        clickLogin(login, hex_md5(password), loginAction);
    }
}

function validLogin(login) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(login);
}

function clickReister() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "registerForm"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("jsp/register.jsp")
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
                window.location.replace("index.jsp")
            } else if ("error" === $(data).find("result").text().toLowerCase()) {
                $("#password").val("");
                alert("Error: This login is busy.");
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
}

function clickOutLogin() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "outLogin"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("index.jsp")
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
        document.write(user_name);
        document.write("<label onclick=\"\">[ Cabinet ]</Label>");
        document.write("<label onclick=\"clickOutLogin();\">[ Out ]</Label>");
        document.write("")
        document.write("</td>")
    } else {
        document.write("<form>");
        document.write("<td><input type=\"text\" id=\"login\" size=\"5\" maxlength=\"15\" autofocus required></td>");
        document.write("<td><input type=\"password\" id=\"password\" size=\"5\" maxlength=\"15\" required></td>");
        document.write("<td><label onclick=\"validData($('#login').val(), $('#password').val());\">[ Login ]</label></td>");
        document.write("<td><label onclick=\"clickReister();\">[ Register ]</Label></td>");
        document.write("</form>");
    }
}


function createCatalog() {
    // TODO нужно сделать получение массива каталогов
    document.write("<ul class=\"navigation\">");
    document.write("<a class=\"main\" href=\"#url\">Каталог #1</a>");
    document.write("<li class=\"n1\"><a href=\"#\">item #1</a></li>");
    document.write("<li class=\"n2\"><a href=\"#\">item #2</a></li>");
    document.write("<li class=\"n3\"><a href=\"#\">item #3</a></li>");
    document.write("<li class=\"n4\"><a href=\"#\">item #4</a></li>");
    document.write("<li class=\"n5\"><a href=\"#\">item #5</a></li>")
    document.write("</ul>");

    document.write("<ul class=\"navigation\">");
    document.write("<a class=\"main\" href=\"#url\">Каталог #2</a>");
    document.write("<li class=\"n1\"><a href=\"#\">item #1</a></li>");
    document.write("<li class=\"n2\"><a href=\"#\">item #2</a></li>");
    document.write("</ul>");

    document.write("<ul class=\"navigation\">");
    document.write("<a class=\"main\" href=\"#url\">Каталог #3</a>");
    document.write("<li class=\"n1\"><a href=\"#\">item #1</a></li>");
    document.write("<li class=\"n2\"><a href=\"#\">item #2</a></li>");
    document.write("<li class=\"n3\"><a href=\"#\">item #3</a></li>");
    document.write("<li class=\"n4\"><a href=\"#\">item #4</a></li>");
    document.write("<li class=\"n5\"><a href=\"#\">item #5</a></li>")
    document.write("</ul>");

    document.write("<ul class=\"navigation\">");
    document.write("<a class=\"main\" href=\"#url\">Каталог #4</a>");
    document.write("</ul>");
}

function alertMessage(text) {
    alert(text);
}