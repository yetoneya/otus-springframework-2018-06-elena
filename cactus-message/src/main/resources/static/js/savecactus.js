$(document).ready(function () {
    $('#save-button').click(function (event) {
        event.preventDefault();
        var cactusdto = {};
        cactusdto["cactusname"] = $("#cactusname").val();
        cactusdto["url"] = $("#url").val();
        $.ajax({
            url: "/save",
            type: 'post',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(cactusdto),
            success: function (data) {
                $('#result').html(data[0]);

            },
            error: function (e) {

            }
        });
    });
});


