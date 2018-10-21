$(document).ready(function () {
    $('#user-list').change(function () {        
        $('#user-button').prop('disabled', false);
        $('#refresh').prop('disabled', false);
    });
});


