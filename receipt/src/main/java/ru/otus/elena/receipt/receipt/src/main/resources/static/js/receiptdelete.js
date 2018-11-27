$(document).ready(function () {
    $('#delete-button').click(function (event) {
        event.preventDefault();
        var id = $("#receipt-list").val();
        $.ajax({
            url: "/receipt/delete",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                if (data[0] !== "")
                {
                    $("#delete-button").prop("disabled", true);
                    $("#receipt-list option[value='" + data[0] + "']").remove();
                }

            },
            error: function (e) {

            }
        });
    });
});

