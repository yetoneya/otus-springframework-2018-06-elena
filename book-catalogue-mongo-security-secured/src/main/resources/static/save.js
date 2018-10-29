$(document).ready(function () {
    $('#add-form').submit(function (event) {
        event.preventDefault();
        $.ajax({
            url: "/save",
            type: "get",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {
                name: $("#name").val(),
                genre: $("#genre").val(),
                authors: $("#authors").val(),
                adult: $("#adult").val()
            },
            success: function (data) {
                var option = '<option value="' + data.id + '">' + data.name + ", " + data.genre + ", " + data.authors + '</option>';
                $("#book-list").append(option);
            },
            error: function (e) {

            }
        });
    });
});



        