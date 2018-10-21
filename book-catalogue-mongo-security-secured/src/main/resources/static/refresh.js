$(document).ready(function () {
    $('#refresh').click(function (event) {
        event.preventDefault();
        var id = $("#user-list").val();
        $.ajax({
            url: "/user/refresh",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {id: id},
            success: function (data) {      
                    $("#refresh").prop("disabled", true);        
            },
            error: function (e) {

            }
        });
    });
});


