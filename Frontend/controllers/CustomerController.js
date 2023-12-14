let baseUrl = "http://192.168.89.174:8081/app/";

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