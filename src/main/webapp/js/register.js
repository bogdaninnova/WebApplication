/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

function validData(login, password, confirmPassword, firstName, secondName, age, email, phone) {
    if (login.length > 0 && password.length > 0 && confirmPassword.length > 0 && firstName.length > 0 && secondName.length > 0 && email.length > 0) {
        sendRegisterData(login, password, firstName, secondName, age, email, phone);

    }
}

function sendRegisterData(login, password, firstName, secondName, age, email, phone) {
    var url = "../register";
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "registerData",
            login: login,
            password: hex_md5(password),
            firstName: firstName,
            secondName: secondName,
            age: age,
            email: email,
            phone: phone
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("../index.jsp");
            }
        }
    });
}