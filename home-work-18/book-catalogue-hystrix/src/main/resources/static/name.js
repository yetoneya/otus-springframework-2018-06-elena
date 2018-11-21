$(document).ready(function () {
    $('#name-form').submit(function (event) {
        event.preventDefault();
        var name = $("#find-by-name").val();
        $.ajax({
            url: "/name",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {name: name},
            success: function (data) {
                $('#book-list').empty();
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        $('#book-list').append('<option value="' + data[i].id + '">' +  data[i].name + ", " + data[i].genre + ", " + data[i].authors + '</option>');
                    }
                }
            },
            error: function (e) {

            }
        });
    });
});


