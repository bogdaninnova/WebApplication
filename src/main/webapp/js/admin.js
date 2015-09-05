function sendCategoryData(action, name, id) {
    var url = "admin";
    $.ajax({
        dataType: "xml",
        url: url,
        type: "POST",
        data: {
            categories: action,
            catName: name,
            catID: id
        },
        success: function (data) {
            if ("ok" === $(data).find("result").text().toLowerCase()) {
                window.location.replace("admin");
            } else {
                alert($(data).find("result").text());
            }
        },
        error: function () {
            alert("Error while send register data.");
        }
    });
};

function checkOnHTML(input) {
	var value = input.value;
	var rep = /[-\/<>&"']/;
	if (rep.test(value)) {
		value = value.replace(rep, '');
		input.value = value;
	}
}