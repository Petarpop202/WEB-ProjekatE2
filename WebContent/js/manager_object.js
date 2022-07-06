$(document).ready(function () {


    $.get({
        url: "../rest/info/managerFacility",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        dataType:"json",
        success: function(objekat){
            if(objekat == "")
            alert("NEMA OBJEKTA");
            else alert("IMA OBJEKTA");
        }
    })

 });