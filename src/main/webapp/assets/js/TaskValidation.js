document.addEventListener("DOMContentLoaded", function () {
    var today = new Date();

    var yyyy = today.getFullYear();
    var mm = today.getMonth() + 1;
    var dd = today.getDate();

    if (mm < 10) { mm = '0' + mm; }
    if (dd < 10) { dd = '0' + dd; }

    var todayFormatted = yyyy + '-' + mm + '-' + dd;

    today.setDate(today.getDate() + 3);

    var futureFormatted = today.toISOString().split('T')[0];

    var startDateInput = document.querySelector("#creationDate");
    var endDateInput = document.querySelector("#end-date");
    startDateInput.setAttribute("min", futureFormatted);
    endDateInput.setAttribute("min", futureFormatted);
});

$(document).ready(function(){
    $('.js-example-basic-multiple').select2();
});