$(document).ready(function () {
    $('#user-list').change(function (event) {
        event.preventDefault();
        $('#user-button').prop('disabled', false);
    });
});


