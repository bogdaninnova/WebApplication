/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url = "index";

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
                window.location.replace("register");
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
}

function clickLogin() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "login"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("login");
            }
        },
        error: function () {
            alert("Error while send login data.");
        }
    });
}

function clickAdmin() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "admin"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("admin");
            }
        },
        error: function () {
            alert("Error while send admin data.");
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
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send logout data.");
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
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send find data.");
        }
    });
}

function clickCabinet() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "cabinet"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("user");
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function createForm(user_name, user_second_name, userStatus) {
    if (user_name != null && user_name != "" && user_name != "null"
        && user_second_name != null && user_second_name != "" && user_second_name != "null") {
        document.write("<td>");
        document.write("Welcome ,");
        document.write(user_second_name);
        document.write(" ");
        document.write(user_name);
        document.write("<a href=\"javascript:clickCabinet()\" style=\"text-decoration: none;color: white;\" onmouseover=\"this.style.color='yellow';\" onmouseout=\"this.style.color='white';\">[<b>Cabinet</b>]</a>");
        if (userStatus == "true") {
            document.write("<a href=\"javascript:clickAdmin()\" style=\"text-decoration: none;color: white;\" onmouseover=\"this.style.color='yellow';\" onmouseout=\"this.style.color='white';\">[<b>Admin</b>]</a>");
        }
        document.write("<a href=\"javascript:logOut()\" style=\"text-decoration: none;color: white;\" onmouseover=\"this.style.color='yellow';\" onmouseout=\"this.style.color='white';\">[<b>LogOut</b>]</a>");
        document.write(" ");
        document.write("</td>");
    } else {
        document.write("<td>Please </td>");
        document.write("<td><a href=\"javascript:clickLogin()\" style=\"text-decoration: none;color: white;\" onmouseover=\"this.style.color='yellow';\" onmouseout=\"this.style.color='white';\">[<b>Login</b>]</a></td>");
        document.write("<td><a href=\"javascript:clickRegister()\" style=\"text-decoration: none;color: white;\" onmouseover=\"this.style.color='yellow';\" onmouseout=\"this.style.color='white';\">[<b>Register</b>]</a></td>");
    }
}

function getProductByCategory(categoryID) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "getProducts",
            id: parseInt(categoryID)
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
}