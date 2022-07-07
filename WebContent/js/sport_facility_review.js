/**
 * 
 */


 $(document).ready(function () {
    const url = new URLSearchParams(window.location.search);

    if(url.has('name')) {
    var id = url.get('name');

    $.get({
        url: "../rest/sports/get?name=" + id,
        contentType:"application/json",
        dataType:"json",
        success: function(objekat){
            reviewObj(objekat);
            reviewLocation(objekat);
        }
    })

    $.get({
        url: "../rest/sports/trainings?name=" + id,
        contentType: "application/json",
        dataType: "json",
        success: function(treninzi){
            reviewTr(treninzi);
        }
    })



    }

 });

 function reviewObj(objekat){
    let n = document.getElementById("main");
    n.innerHTML = '<div class="container pt-5 pb-5"><h1 class="font-weight-bold text-dark">'+objekat.name+'</h1><h3 class="subhead fs-4 mb-0 text-white-50">'+objekat.location.address+'</h3><span class="fa fa-star checked"></span><span class="fa fa-star checked"></span><span class="fa fa-star checked"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><h5 class="review-count">'+objekat.rate+'</h5></div></div>';
    n.style.background = "rgb(27,27,27) url('../img/"+objekat.picture+".jpg')";
    n.style.backgroundSize = "cover";

    
}

function reviewTr(objekat){

    let i = "";
    for(let data of objekat){
        i = i + `<div class="col-sm mt-5 d-flex justify-content-center">
        <div class="card border-success" style="width: 19rem;">
            <img src="../img/`+data.picture+`.jpg" class="card-img-top" alt="...">
            <div class="card-body p-4 rounded-bottom" >
                <h5 class="card-title font-weight-bold">`+data.name+`&nbsp;&nbsp;&nbsp;&nbsp;</h5>
                <p class="card-text">`+data.description+`</p>
                <p class="card-text">String&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje</p>
                <div class="float-start">
                    <a onclick="zakazi('`+data.name+`')" class="btn btn-primary">Prijavi</a>
                    <p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje: `+data.duration+`min</p>
                </div>
            </div>
        </div>
    </div>`;}

    let t = document.getElementById("sadrzaj");
    t.innerHTML = i;  
}

function reviewLocation(objekat){
    let n = document.getElementById("second");
    n.innerHTML = '<div class="card" style="width: 30rem; height: 30rem;"><iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2809.041198924487!2d19.843112351335193!3d45.24695965583078!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x475b10133be21a7d%3A0x8027ac1b6555d9ff!2z0KHQv9C10L3RgSDQodC_0L7RgNGC0YHQutC4INCm0LXQvdGC0LDRgCDQktC-0ZjQstC-0LTQuNC90LA!5e0!3m2!1ssr!2srs!4v1656424503043!5m2!1ssr!2srs" width="auto" height="100%" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe> <div class="card-body"> <h5 class="card-title">'+objekat.location.address+'</h5> <p class="card-text">'+objekat.workTime+'</p> <p class="card-text">'+objekat.statusStr+'</p> </div> </div>'
}

function profile(){
    location.assign("profile_page.html");
}

function zakazi(id){
    $.post({
        url: "../rest/info/doTraining?name="+id,
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType: "application/json",
        success: function(odgovor) {
            alert("Uspesna prijava")
        },
        error: function(odgovor) {
                alert("Greska prilikom prijave!");
        }
    });
}