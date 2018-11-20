$(document).ready(function () {
    $('#edit-form').submit(function (event) {
        event.preventDefault();
        $("#edit-input").prop("disabled", true);
        var bookdto = {};
        bookdto["id"] = $("#edit-id").val();
        bookdto["name"] = $("#edit-name").val();
        bookdto["genre"] = $("#edit-genre").val();
        bookdto["authors"] = $("#edit-authors").val();
        $.ajax({
            url: "/edit",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(bookdto),
            success: function (data) {
                if (data.id !== "")
                {
                    $("#book-list option[value='" + data.id + "']").remove();
                    var option = '<option value="' + data.id + '">'+ data.name + ", " + data.genre + ", " + data.authors + '</option>';
                    $("#book-list").append(option);
                }
            },
            error: function (e) {

            }
        });
    });
});


