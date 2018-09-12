$(document).ready(function () {
    $('#find-all-form').submit(function (event) {
        event.preventDefault();              
        $.ajax({
            url: "/findall",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $("#result").html("done");
                $('#book-list').empty();
                for (var i = 0; i < data.length; i++) {
                    $('#book-list').append('<option value="' + data[i].id + '">' + data[i].id + ". " + data[i].name + ", " + data[i].genre + ", " + data[i].authors + '</option>');
                }
            },
            error: function (e) {
                
            }
        });
    });
});


