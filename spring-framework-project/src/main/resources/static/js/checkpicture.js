$(document).ready(function () {
    $('#check-picture').on("click", function (event) {       
        var checked = $("#check-picture").is(':checked');       
        $.ajax({
            url: "/check/picture",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {checked: checked},
            success: function (data) {
                
            },
            error: function (e) {

            }
        });
    });
});


