$(document).ready(function () {
    $("#change-lang").submit(function (event) {
        var lang = $("input[name='lang']:checked").val();
        $.ajax({
            url: "/change/lang",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {lang: lang},
            success: function (data) {
                window.location.replace('receipt?lang=' + lang);
                window.location.replace('admin?lang=' + lang);

            },
            error: function (e) {

            }
        });
    });
});


