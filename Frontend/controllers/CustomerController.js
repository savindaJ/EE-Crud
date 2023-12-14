let baseUrl = "http://192.168.89.174:8081/app/";
$('.delete').on('click',function (){
    $(`#tblCustomer tr`).on('click',function () {
        $(this).closest().remove();
    });
});
function bindEvent() {
    $('.delete').on('click',function (){
        $(`#tblCustomer tr`).on('click',function () {
            $(this).closest("#tblCustomer tr").remove();
        });
    });

    $(`#tblCustomer tr`).click(function () {

        var $row = $(this).closest("tr");        // Finds the closest row <tr>
        $tds = $row.find("td:nth-child(1)");
        $ts = $row.find("td:nth-child(2)");
        $tt = $row.find("td:nth-child(3)");
        $tf = $row.find("td:nth-child(4)");
        // let td_list =  $();

        $(`#upCID`).val($tds.text());
        $(`#upCName`).val($ts.text());
        $(`#upCAddress`).val($tt.text());
        $(`#upCTp`).val($tf.text());
    });
}

function loadAllCustomers() {
    $.ajax({
        url: baseUrl+"customer",
        type: "get",
        dataType:"json",
        success:function (resp) {
            $(`#body`).empty();

            for (const customer of resp.data) {

                $(`#body`).append(`<tr>
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

               bindEvent();
            }
        },
        error:function (err) {
            let parse = JSON.parse(err.responseText);
            alert(parse.message);
        }
    })
}


$('#btnSaveCustomer').on('click',function () {
   $.ajax({
       url: baseUrl+"customer",
       type: "post",
       dataType: "json",
       data:{
           id: $('#customer-id').val(),
           name: $('#customer-name').val(),
           address: $('#customer-address').val(),
           salary: $('#customer-salary').val()
       },
       success:function (res) {
           loadAllCustomers();
           alert(res.message);
       },
       error:function (err) {
           let parse = JSON.parse(err.responseText);
           alert(parse.message);
       }
   })
});

loadAllCustomers();