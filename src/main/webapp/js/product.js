/**
 * Created by Created by Greenberg Dima <gdvdima2008@yandex.ru>.
 */

var url = "product";

function validData(bet, currentPrice, buyOutPrice, age, productID, buyerID) {
    if(isBet(bet) && bet > currentPrice) {
        if (bet >= buyOutPrice) {
            location.href="product?id=" + productID + "&page=buy"
        } else {
            if (age > 18) {
                if (productID != null && productID != "" && buyerID != null && buyerID != "") {
                    clickBet(productID, buyerID, bet);
                } else {
                    alert("Can not get data.")
                }
            } else {
                alert("Young age to bet.");
            }
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
                window.location.replace("product?id=" + productID);
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function realBuy(productID, userID) {
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            action : "realBuy",
            productID : productID,
            userID : userID
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("product?id=" + productID);
            } else {
                alert($(data).find("result").text());
                window.location.replace("product?id=" + productID);
            }
        },
        error: function () {
            alert("Error while send data.");
        }
    });
}

function checkOnHTML(input) {
    var value = input.value;
    var rep = /[-\/<>&"']/;
    if (rep.test(value)) {
        value = value.replace(rep, '');
        input.value = value;
    }
}