$(document).ready(function () {
    $('#type-select').submit(function (event) {
        event.preventDefault();
        var selection = $('input[name=typeselect]:checked', '#type-select').val();
        $.ajax({
            url: "/select",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {
                selection: selection
            },
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


