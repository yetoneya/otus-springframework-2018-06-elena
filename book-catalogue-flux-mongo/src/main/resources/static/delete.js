$(document).ready(function () {
    $('#delete-button').click(function (event) {
        event.preventDefault();
        var id = $("#book-list").val();
        $("#book-list option[value='" + id +"']").remove();
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
                    
                }

            },
            error: function (e) {

            }
        });
    });
});


