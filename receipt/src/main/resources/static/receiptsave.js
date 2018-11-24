$(document).ready(function () {
    $("#save-form").submit(function (event) {
        event.preventDefault();
        $("#save-input").prop("disabled", true);
        var type = $("#save-type").val();
        var name = $("#save-name").val();
        var component = $("#save-component").val();
        var description = $("#receipt-area").val();
        $.ajax({
            url: "/receipt/save",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {
                type: type,
                name: name,
                component: component,
                description: description
            },
            success: function (data) {
                $("#result").html(data[0]);
            },
            error: function (e) {
                $("#result").html(data[0]);
                $("#save-input").prop("disabled", false);
            }
        });
    });
});


