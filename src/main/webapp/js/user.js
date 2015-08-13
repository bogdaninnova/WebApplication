/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url = "user";

function clickBack() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickBack"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("index");
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickInformation() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickInform"
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

function clickChangeUserData() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickUserChangeData"
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

function clickChange(changeSelect) {
    var select = null;
    for (var i = 0 ; i < changeSelect.length; i++) {
        if (changeSelect[i].checked) {
            select = changeSelect[i].value;
            break;
        }
    }
    if (select != null && select != "") {
        $.ajax({
            dataType: "xml",
            url: url,
            type: "POST",
            data: {
                action: select
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
    } else {
        alert("Make your choice.");
    }
}

function validPassword(oldPassword, newPassword, confirmPassword) {
    if (oldPassword.length > 0) {
        if (newPassword.length > 0) {
            if (confirmPassword.length > 0) {
                if (newPassword === confirmPassword) {
                    clickChangePassword(hex_md5(oldPassword), hex_md5(newPassword));
                } else {
                    alert("NewPassword not equal confirmPassword");
                }
            } else {
                alert("Please enter confirm password");
            }
        } else {
            alert("Please enter new password");
        }
    } else {
        alert("Please enter password");
    }
}

function clickChangePassword(oldPassword, newPassword) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickChangePassword",
            oldPassword: oldPassword,
            newPassword: newPassword
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#password").val("");
                $("#newPassword").val("");
                $("#confirmPassword").val("");
                alert("Your password is changed");
                window.location.replace("user");
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}