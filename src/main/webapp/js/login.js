/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url="login";
var loginOrEmail;

function checkLoginForm(login, password) {
    if (login.length > 0) {
        if (password.length > 0) {
            if (isEmail(login)) {
                loginOrEmail = "loginEmail";
            } else {
                loginOrEmail = "login";
            }
            clickLogin(login, hex_md5(password), loginOrEmail);
        } else alert("Please enter password");
    } else alert("Please enter login");
}

function isEmail(login) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(login);
}

function clickLogin(login, password, loginOrEmail) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: loginOrEmail,
            login: login,
            password: password
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("index");
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send login data.");
        }
    });
}

function clickBack() {
    window.location.replace("index");
}