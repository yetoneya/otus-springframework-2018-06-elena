$(document).ready(function () {
    $('#delete-form').submit(function (event) {
        event.preventDefault();
        var id = $("#delete-id").val();
        $.ajax({
            url: "/delete",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                if (data !== -1)
                {
                    $("#book-list option[value='" + data + "']").remove();
                } else {
                    $("#error").html("error");
                }
            },
            error: function (e) {

            }
        });
    });
});


