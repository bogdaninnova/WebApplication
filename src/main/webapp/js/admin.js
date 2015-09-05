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

function saveCategoryID(id) {
	document.getElementById('hiddenCategoryID').value = id;
    if(id != -1) {
    	if (document.getElementById('categoryName').value.length > 0) {
	        document.getElementById('change').disabled = false; 
    	}
        document.getElementById('delete').disabled = false; 
    } else { 
        document.getElementById('change').disabled = true; 
        document.getElementById('delete').disabled = true; 
    }
}

function isEmpty(){
    if (document.getElementById('categoryName').value.length > 0) {
    	document.getElementById('create').disabled = false;
    	if (document.getElementById('hiddenCategoryID').value != -1) {
    		document.getElementById('change').disabled = false; 
    	}
    } else {
        document.getElementById('change').disabled = true; 
        document.getElementById('create').disabled = true; 
    }
}

function checkOnHTML(input) {
	var value = input.value;
	var rep = /[-\/<>&"']/;
	if (rep.test(value)) {
		value = value.replace(rep, '');
		input.value = value;
	}
}