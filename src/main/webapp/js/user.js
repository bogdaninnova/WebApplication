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