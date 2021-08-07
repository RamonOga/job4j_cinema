function goPayment() {
    let valueRadio = $('input[name="place"]:checked').val();
    if (valueRadio === null || valueRadio === undefined) {
        alert("Select a place")
    } else {
        let row = valueRadio.split("")[0];
        let place = valueRadio.split("")[1] ;
        window.location.href = '/job4j_cinema/payment.html?row=' + row + '&' + 'place=' + place;
    }

}


function sendPay() {
    let valueRadio = $('input[name="place"]:checked').val();
    if (valueRadio === null || valueRadio === undefined) {
        alert("Select a place")
    } else {
        postReq(valueRadio)

    }
}

function postReq(value) {

    $(document).ready(function()
    {
        $.ajax({
            url: "http://localhost:8081/job4j_cinema/pay",
            dataType: "json",
            method: "POST",
            data: {value},
        }).done(function () {

        }).fail(function (err) {
            console.log(err)
        });
    });
    window.location.href = '/job4j_cinema/payment.html?place=' + value
}

 function printHeader(row, place) {
    $(function () {
        $('h3').append("<p>Вы выбрали " + row + " ряд " + place + " место, сумма : 500 рублей.</p>");
    })

 }


/*

$(document).ready(function()
{

    $.ajax({
      url: "ajax_quest.php",
        dataType: "json",
       	method: "POST",
    	data: {"place": value},
       	success: function(data) {

 		console.log(data);
       }
 });
});

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
