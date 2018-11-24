$(document).ready(function () {    
    $("#receipt-list").change(function (event) {
        event.preventDefault();
        $("#delete-button").prop("disabled", false);
        var id = $("#receipt-list").val();
        $.ajax({
            url: "receipt/select",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                $("#save-type").val(data.type);
                $("#save-name").val(data.name);
                $("#save-component").val(data.component);
                $("#receipt-area").val(data.description);
            },
            error: function (e) {

            }
        });
    });
});


