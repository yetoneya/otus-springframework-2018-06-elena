$(document).ready(function () {
    $("#book-list").change(function () {
        $("#edit-button").prop("disabled", false);
        $("#delete-button").prop("disabled", false);
        var id = $("#book-list").val();
        $.ajax({
            url: "/comments",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                var result = "";
                for (var i = 0; i < data.length; i++) {
                    result = result + (i + 1) + ". " + data[i] + "\n";
                }
                $("#all-comments").val(result);

            },
            error: function (e) {

            }
        });
    });
});


