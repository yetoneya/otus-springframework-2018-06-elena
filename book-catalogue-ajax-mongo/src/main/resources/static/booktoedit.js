$(document).ready(function () {
    $('#all-books').submit(function (event) {
        event.preventDefault();
        var id = $("#book-list").val();
        $.ajax({
            url: "/byid",
            type: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: {
                id: id
            },
            success: function (data) {             
                    $("#edit-id").val(data.id);
                    $("#edit-name").val(data.name);
                    $("#edit-genre").val(data.genre);
                    $("#edit-authors").val(data.authors);
                    $("#edit-input").prop("disabled", false);
                
            },
            error: function (e) {

            }
        });
    });
});


