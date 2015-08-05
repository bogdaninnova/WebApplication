/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

function validData(login, password, confirmPassword, firstName, secondName, age, email, phone) {
    if (login.length > 0 && password.length > 0 && confirmPassword.length > 0 && firstName.length > 0 && secondName.length > 0 && age.length > 0 && email.length > 0) {
        if (Boolean(login.length <= 3)) {
            alert("Short login.");
        } else if(Boolean(password.length <= 3)) {
            alert("Short password.");
        } else if(password != confirmPassword) {
            alert("Check the input password confirmation.");
        } else if(firstName.length <= 3) {
            alert("Check the input password confirmation.");
        } else if (!(isEmail(email))) {
            alert("Incorrect Email address.");
        } else {
            sendRegisterData(login, password, firstName, secondName, age, email, phone);
        }
    } else alert("Fill in all required fields.");
}

function isEmail(checkEmail) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(checkEmail);
}



function sendRegisterData(login, password, firstName, secondName, age, email, phone) {
    var url = "register";
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
            age: Date.parse(age),
            email: email,
            phone: phone
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("user");
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
}