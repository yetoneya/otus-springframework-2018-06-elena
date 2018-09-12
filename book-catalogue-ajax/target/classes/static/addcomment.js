$(document).ready(function () {
    $('#comment-form').submit(function (event) {
        event.preventDefault();
        var comment = $("#comment-text").val();
        var id = $("#book-list").val();
        $("#comment-text").val("");
        $.ajax({
            url: "/addcomment",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {
                id: id,
                comment: comment
            },
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


