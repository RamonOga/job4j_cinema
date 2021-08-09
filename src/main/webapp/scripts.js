
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


function postReq(row, place) {
    let name = $('#username').val();
    let phone = $('#phone').val();
    let email = $('#email').val();
        $.ajax() ({
            url: "http://localhost:8081/job4j_cinema/pay",
            dataType: "json",
            method: "POST",
            data: {row : row, place : place, username : name, phone : phone, email : email},
        }).done(function () {

        }).fail(function (err) {
            console.log(err)
        });
};

 function printHeader(row, place) {
    $(function () {
        $('h3').append("<p>Вы выбрали " + row + " ряд " + place + " место, сумма : 500 рублей.</p>");
    })
 }



