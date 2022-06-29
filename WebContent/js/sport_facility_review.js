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

function reviewTr(treninzi){

    let i = "";
    for(let t in treninzi){
        i = i + ' <tr><td>'+ t.name+'</td><td>'+t.facility.name+'</td><td>'+t.description+'</td><td><button type="button" class="btn btn-secondary">Prijavi se</button></td></tr>'
    }

    let t = document.getElementById("tabletr");
    t.innerHTML = i;  
}
