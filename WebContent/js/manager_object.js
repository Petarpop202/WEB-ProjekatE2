$(document).ready(function () {


    $.get({
        url: "../rest/info/managerFacility",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        dataType:"json",
        success: function(objekat){
            if(objekat == null)
            alert("NEMA OBJEKTA");
            else createNaslov(objekat);
        }
    })

    $.get({
        url: "../rest/info/FacilityTrainings",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        dataType:"json",
        success: function(data){
            createCards(data);
        }
    })

 });

 function createNaslov(objekat){

    let i = document.getElementById("naslov");
    i.innerHTML = '<div class="row"><div class="profile-nav col-md-3"><div class="panel" ><div class="user-heading round" ><img style="width: 100%;" src="../img/'+objekat.picture+'.jpg" alt=""><h1>'+objekat.name+'</h1><h3>'+objekat.location.address+'</h3></div></div></div><div class="profile-info col-md-9"><div class="panel"></div><div class="panel"><div class="panel-body bio-graph-info"><h1>Informacije o objektu</h1><div class="bio-row"><h4><span>Aktivan</span>: '+objekat.statusStr+'</h4></div><div class="bio-row"><h4><span>Tip Objekta</span>: '+objekat.typeStr+'</h4></div><div class="bio-row"><h4><span>Radno vreme </span>: '+objekat.workTime+'</h4></div><div class="bio-row"><h4><span>Adresa </span>: '+objekat.location.address+'</h4></div></div></div></div></div><div class="mt-5"><h1 style="text-align:center">Sadrzaj objekta</h1></div>'
 }

 function createCards(objekat){
    let i ="";

    for(let data of objekat){
    i = i + `<div class="col-sm mt-5 d-flex justify-content-center">
    <div class="card border-success" style="width: 19rem;">
        <img src="../img/`+data.picture+`.jpg" class="card-img-top" alt="...">
        <div class="card-body p-4 rounded-bottom" >
            <h5 class="card-title font-weight-bold">`+data.name+`&nbsp;&nbsp;&nbsp;&nbsp;</h5>
            <p class="card-text">`+data.description+`</p>
            <p class="card-text">String&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje</p>
            <div class="float-start">
                <a href="" class="btn btn-primary">Izmeni</a>
                <a href="" class="btn btn-primary">Izbrisi</a>
                <p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje: `+data.duration+`min</p>
            </div>
        </div>
    </div>
</div>`;}
let n = document.getElementById("sadrzaj");
n.innerHTML = i;
 }