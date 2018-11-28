$(document).ready(function () {
    $("#save-form").submit(function (event) {
        event.preventDefault();
        $("#save-input").prop("disabled", true);
        $("#result").html($("#save-url").val());
        var receipt = {};
        receipt["type"] = $("#save-type").val();
        receipt["name"] = $("#save-name").val();
        receipt["component"] = $("#save-component").val();
        receipt["description"] = $("#receipt-area").val();
        receipt["url"] = $("#save-url").val();
        $.ajax({
            url: "/receipt/save",
            type: 'post',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(receipt),
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


