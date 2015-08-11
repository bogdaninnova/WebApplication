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

function validData(bet, currentPrice, age, productID, buyerID) {
    if(isBet(bet) && bet > currentPrice) {
        if (age > 18) {
            if (productID != null && productID != "" && buyerID != null && buyerID != "") {
                clickBet(productID, buyerID, bet);
            } else {
                alert("Can not get data.")
            }
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

function clickBet(productID, buyerID, bet) {
    alert("ProductID - " + productID + " ,BuyerID - " + buyerID + " ,Bet - " + bet);
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action: "clickBet",
            productID: productID,
            buyerID: buyerID,
            bet: bet
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("product");
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}