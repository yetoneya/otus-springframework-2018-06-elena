$(document).ready(function () {
    $('#user-button').click(function (event) {
        event.preventDefault();
        var id = $("#user-list").val();
        $.ajax({
            url: "/user/delete",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                if (data[0] !== "")
                {
                    $("#user-button").prop("disabled", true);
                    $("#user-list option[value='" + data[0] + "']").remove();
                }

            },
            error: function (e) {

            }
        });
    });
});


