$(document).ready(function () {
    $('#edit-form').submit(function (event) {
        event.preventDefault();
        var id = $("#edit-id").val();
        var bookdto = {};
        bookdto["id"] = id;
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
                if (data.id !== 0)
                {
                    $("#book-list option[value='" + id + "']").remove();
                    var option = '<option value="' + data.id + '">' + data.id + ". " + data.name + ", " + data.genre + ", " + data.authors + '</option>';
                    $("#book-list").append(option);
                }
            },
            error: function (e) {

            }
        });
    });
});


