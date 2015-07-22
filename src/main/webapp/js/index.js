/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var user_name = null;

function clickReister() {
    var url="user";
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "register"
        }
    });
}

function clickLogin(login, password) {
    var url="user";
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
}

function clickFind(find) {
    var url="user";
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


function createForm() {
    if (user_name != null && user_name !="") {
        document.write("<td>");
        document.write(user_name);
        document.write("</td>")
    } else {
        document.write("<form>");
        document.write("<td><input type=\"text\" id=\"login\" size=\"5\" maxlength=\"15\" autofocus required></td>");
        document.write("<td><input type=\"password\" id=\"password\" size=\"5\" maxlength=\"15\" required></td>");
        document.write("<td><label onclick=\"clickLogin($('#login').val(), $('#password').val());\">[ Login ]</label></td>");
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