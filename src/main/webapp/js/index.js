/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */



var url="user";

function validData(login, password) {
    if (login.length > 0 && password.length > 0) {
        clickLogin(login, hex_md5(password));
    }
}

function clickReister() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "registerForm"
        }
    });
    window.location.replace("jsp/register.jsp")
}

function clickLogin(login, password) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "login",
            login: login,
            password: password
        }
    });
    $("#login").val("");
    $("#password").val("");
    window.location.replace("index.jsp")
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