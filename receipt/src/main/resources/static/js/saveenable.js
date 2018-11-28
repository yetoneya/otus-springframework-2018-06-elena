$(document).ready(function () {
    $('#receipt-area').on("focus", function (event) {
        event.preventDefault();
        $('#save-input').prop('disabled', false);
    });
});

