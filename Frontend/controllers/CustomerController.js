let baseUrl = "http://192.168.45.174:8081/app/";

$('#getAllCustomer').on('click', function () {
    loadAllCustomers();
});

function bindEvent() {
    $('.delete').on('click', function () {
        var $row = $(this).closest("tr");
        $tds = $row.find("td:nth-child(1)");

        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: baseUrl + "customer?id=" + $tds.text(),
                    type: "delete",
                    dataType: "json",
                    success: function (res) {
                        loadAllCustomers();
                        Swal.fire({
                            title: "Deleted!",
                            text: res.message,
                            icon: "success"
                        });
                    },
                    error: function (err) {
                        let parse = JSON.parse(err.responseText);
                        alert(parse.message);
                    }
                });
            }
        });

    });

    $(`.btnEdit`).on('click', function () {
        var $row = $(this).closest("tr");
        $tds = $row.find("td:nth-child(1)");
        $ts = $row.find("td:nth-child(2)");
        $tt = $row.find("td:nth-child(3)");
        $tf = $row.find("td:nth-child(4)");
        $(`#upCID`).val($tds.text());
        $(`#upCID`).prop('disabled', true);
        $(`#upCName`).val($ts.text());
        $(`#upCAddress`).val($tt.text());
        $(`#upCTp`).val($tf.text());

    });
}

function loadAllCustomers() {
    $.ajax({
        url: baseUrl + "customer",
        type: "get",
        dataType: "json",
        success: function (resp) {
            $(`#body`).empty();

            for (const customer of resp.data) {

                $(`#body`).append(`<tr>
                                <td>${customer.id}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                                <td>${customer.salary}</td>
                                <td><button type="button" class="btn btn-primary btn-sm me-2 btnEdit" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal2">
                                    Edit
                                </button>
                                <button class="btn btn-danger me-3 btn-sm delete">Delete</button></td>
                   
                             </tr>`);


            }
            // alert(resp.message);
            bindEvent();
        },
        error: function (err) {
            let parse = JSON.parse(err.responseText);
            alert(parse.message);
        }
    })
}

$('#btnSaveCustomer').on('click', function () {
    $.ajax({
        url: baseUrl + "customer",
        type: "post",
        dataType: "json",
        data: {
            id: $('#customer-id').val(),
            name: $('#customer-name').val(),
            address: $('#customer-address').val(),
            salary: $('#customer-salary').val()
        },
        success: function (res) {
            loadAllCustomers();
            // alert(res.message);
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: res.message,
                showConfirmButton: false,
                timer: 1500
            });
        },
        error: function (err) {
            let parse = JSON.parse(err.responseText);
            alert(parse.message);
        }
    });
});
$('#btnUpdate').on('click', function () {

    const customer = {
        id: $(`#upCID`).val(),
        name: $(`#upCName`).val(),
        address: $(`#upCAddress`).val(),
        salary: $(`#upCTp`).val()
    }


    $.ajax({
        url: baseUrl + "customer",
        type: "put",
        dataType: "json",
        data: JSON.stringify(customer),
        success: function (res) {
            // alert(res.message)
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: res.message,
                showConfirmButton: false,
                timer: 1500
            });
            loadAllCustomers();
        },
        error: function (err) {
            alert("Bad Request !")
        }
    })
});

loadAllCustomers();