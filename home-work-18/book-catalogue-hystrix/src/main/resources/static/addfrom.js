$(document).ready(function () {
    $('#add-from-button').click(function (event) {
        event.preventDefault();              
        $.ajax({
            url: "/addfrom",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $('#books-from').html(data[0]);
            },
            error: function (e) {
                
            }
        });
    });
});


