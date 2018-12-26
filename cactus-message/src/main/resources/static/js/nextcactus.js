$(document).ready(function () {
    $('#next-button').click(function (event) {
        event.preventDefault();
        $.ajax({
            url: "/next",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            success: function (data) {
                $('#url').val(data.url);
                $('#name').html(data.cactusname);
                $('#cactusname').val(data.cactusname);
                $('#photo').attr('src', data.url);
            },
            error: function (e) {

            }
        });
    });
});


