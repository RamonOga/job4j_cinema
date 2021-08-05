/*
<script>
    function sendGreeting() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/pay',
        data: JSON.stringify({
            name: $('#exampleInputEmail1').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        $('#emailList li:last').append(`<li>${data.name}</li>`)
    }).fail(function (err) {
        console.log(err);
    });
}
</script>

<script>
    $(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/Dream_Job/greet',
        dataType: 'json'
    }).done(function (data) {
        for (var email of data) {
            $('#emailList li:last').append(`<li>${email.name}</li>`)
        }
    }).fail(function (err) {
        console.log(err);
    });
});
</script>*/
