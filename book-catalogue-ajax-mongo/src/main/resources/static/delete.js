$(document).ready(function () {
    $('#delete-button').click(function (event) {
        event.preventDefault();
        var id = $("#book-list").val();
        $.ajax({
            url: "/delete",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                if (data[0] !== "")
                {
                    $("#edit-button").prop("disabled", true);
                    $("#delete-button").prop("disabled", true);
                    $("#book-list option[value='" + data[0] + "']").remove();
                }

            },
            error: function (e) {

            }
        });
    });
});


