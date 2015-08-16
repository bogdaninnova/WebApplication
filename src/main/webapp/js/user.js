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
                window.location.replace(url);
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
                window.location.replace(url);
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
                    window.location.replace(url);
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
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

var oldName;
var oldSecondName;
var oldPhone;


function validUserData(newName, newSecondName, newPhone) {
    var name;
    var secondName;
    var phone;

    if (newName != null && newName != "") {
        if (newName != oldName) {
            if (newName.length >=3) {
                name = newName;
            } else {
                name = null;
                alert("Short name.");
            }
        } else {
            name = oldName;
        }
    } else {
        name = oldName;
    }

    if (newSecondName != null && newSecondName != "") {
        if (newSecondName != oldSecondName) {
            if (newSecondName >= 2) {
                secondName = newSecondName;
            } else {
                secondName = null;
                alert("Short second name.");
            }
        } else {
            secondName = oldSecondName;
        }
    } else {
        secondName = oldSecondName;
    }

    if (newPhone != null && newPhone != "") {
        if (newPhone != oldPhone) {
            phone = newPhone;
        } else {
            phone = oldPhone;
        }
    } else {
        phone = oldPhone;
    }

    if (name != null && secondName != null) {
        clickChangeUser(name, secondName, phone);
    }
}

function clickChangeUser(name, secondName, phone) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "sendNewUserData",
            name: name,
            secondName: secondName,
            phone: phone
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#changeName").val("");
                $("#changeSecondName").val("");
                $("#changePhone").val("");
                alert("Your user data is changed. Please re login");
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function validEmail(email) {
    if(isEmail(email)) {
        clickChangeEmail(email);
    } else {
        alert("Incorrect Email address.");
    }
}

function isEmail(checkEmail) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(checkEmail);
}

function clickChangeEmail(checkEmail) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickChangeEmail",
            checkEmail: checkEmail
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                $("#checkEmail").val("");
                alert("On an email sent your letter of confirmation.");
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickAddLotPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickAddLotPage"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function validLot(title, description, endDate, startPrice, buyOutPrice) {
    if (title.length > 0 && description.length > 0 && endDate.length > 0 &&
            startPrice.length > 0 && buyOutPrice.length > 0) {
        if (title.length < 3) {
            alert("Short title.");
        } else if(description.length < 3) {
            alert("Short description.");
        } else {
            clickAddLot(title, description, Date.parse(endDate), startPrice, buyOutPrice);
        }
    } else {
        alert("Please fill in all required fields.");
    }
}

function clickAddLot(title, description, endDate, startPrice, buyOutPrice) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickAddLot",
            title: title,
            description: description,
            endDate: endDate,
            startPrice: startPrice,
            buyOutPrice: buyOutPrice
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                alert("Your lot is added.");
                window.location.replace(url);
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickShowLotsPurchasedPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "showLotsPurchased"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function clickFollowingProductsPage() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "followingProducts"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace(url);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}