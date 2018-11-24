$(document).ready(function () {
    $('#find-form').submit(function (event) {
        event.preventDefault();
        var type = $("#find-type").val();
        var name = $("#find-name").val();
        var component = $("#find-component").val();
        $.ajax({
            url: "/receipt/find",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {
                type: type,
                name: name,
                component: component
            },
            success: function (data) {
                $('#receipt-list').empty();
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        $('#receipt-list').append('<option value="' + data[i].id + '">' + ". " + data[i].type + ", " + data[i].name + ", " + data[i].component + '</option>');
                    }
                }
            },
            error: function (e) {

            }
        });
    });
});


