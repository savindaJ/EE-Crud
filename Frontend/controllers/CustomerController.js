let baseUrl = "http://192.168.89.174:8081/app/";

function loadAllCustomers() {
    $.ajax({
        url: baseUrl + "customer",
        type: "get",
        dataType: "json",
        success: function (res) {
            $('#body').empty();
            for (let customer in res.data) {
                $('#body').append(`<tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>${customer.address}</td>
                    <td>${customer.salary}</td>
                    <td><button type="button" class="btn btn-primary btn-sm me-2" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal2">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm delete">Delete</button></td>
                </tr>`);
            }
        },
        error:function (err) {
            let parse = JSON.parse(err.responseText);
            alert(parse.message);
        }
    });
}

loadAllCustomers();