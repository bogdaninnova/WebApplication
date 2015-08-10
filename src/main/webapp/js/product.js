/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url = "product";

function clickBack() {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "back"
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("index");
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function validData(bet, currentPrice, age) {
    if(isBet(bet) && bet > currentPrice) {
        if (age > 18) {
            clickBet(bet);
        } else {
            alert("Young age to bet.");
        }
    } else {
        alert("Incorrect Bet");
    }
}

function isBet(bet) {
    var re = /^\d+$/;
    return re.test(bet);
}

function clickBet(bet) {
    alert(bet);
}