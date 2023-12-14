let baseUrl = "http://localhost:8080/app/";

function loadAllCustomers() {
    $.ajax({
        url: baseUrl + "customer",
        type: "get",
        dataType: "json",
        success: function (res) {

        },
        error:function (err) {

        }
    });
}

loadAllCustomers();