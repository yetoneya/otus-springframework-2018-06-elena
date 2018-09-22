$(document).ready(function () {
    $('#authors-form').submit(function (event) {
        event.preventDefault();
        $('#start').html($('#find-by-authors').val());
        var author = $('#find-by-authors').val();

        $.ajax({
            url: "/author",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {author: author},
            success: function (data) {
                $('#book-list').empty();
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        $('#book-list').append('<option value="' + data[i].id + '">' + data[i].name + ", " + data[i].genre + ", " + data[i].authors + '</option>');
                    }
                }
            },
            error: function (e) {

            }
        });
    });
});


