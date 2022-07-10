/**
 * 
 */


 $(document).ready(function () {
    const url = new URLSearchParams(window.location.search);
    let s;

    if(url.has('name')) {
    var id = url.get('name');

    $.ajax({
        url : "../rest/info/getUser",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            if(data.role == "ADMIN" || data.role == "MANAGER")
                loadAllComments(id);
            else loadComments(id);
        }
    });

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
            editForms(treninzi);
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
    let z = 1;
    let s = "onclick=zakazi('`+data.name+`')";
    for(let data of objekat){
        i = i + `<div class="col-sm mt-5 d-flex justify-content-center">
        <div class="card border-success" style="width: 19rem;">
            <img src="../img/`+data.picture+`.jpg" class="card-img-top" alt="...">
            <div class="card-body p-4 rounded-bottom" >
                <h5 class="card-title font-weight-bold">`+data.name+`&nbsp;&nbsp;&nbsp;&nbsp;</h5>
                <p class="card-text">`+data.description+`</p>
                <p class="card-text">String&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje</p>
                <div class="float-start">
                    <a data-toggle="modal" data-target="#modalEditContent`+z+`" class="btn btn-primary">Prijavi</a>
                    <p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje: `+data.duration+`min</p>
                </div>
            </div>
        </div>
    </div>`;
    z++;}

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

function zakazi(id,z){
    let date = $('input#datum'+z+'').val();

    $.post({
        url: "../rest/info/doTraining?name="+id+"&date="+date,
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType: "application/json",
        success: function(odgovor) {
            alert("Uspesna prijava")
            if(odgovor){
                commentSection();}
        },
        error: function(odgovor) {
            document.getElementById("eril").removeAttribute("hidden");
        }
    });


}

function editForms(treninzi){
    let i = "";
    let z = 1;
    var today = new Date();
    var date =today. getDate() +'.'+(today. getMonth() + 1)+'.'+ today. getFullYear()+'.';
    var dateTime = date;
    for(let data of treninzi){
        i = i + `<div class="modal fade" id="modalEditContent`+z+`" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content bg-dark text-white">
            <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold text-primary">Prijava treninga</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body mx-3">

            <div class="md-form mb-5 text-primary">
				<label data-error="wrong" data-success="right" for="name">Ime</label>
				<input type="text" id="name" name="name" class="form-control validate">
						
				<label class="text-lg-right" id="eri" style="color: red;" hidden>Pogresan format imena!</label>
			</div>

            <div class="md-form mb-5 text-primary">
				<label data-error="wrong" data-success="right" for="name">Prezime</label>
				<input type="text" id="surname" name="surname" class="form-control validate">
						
				<label class="text-lg-right" id="eri" style="color: red;" hidden>Pogresan format imena!</label>
			</div>

            <div class="md-form mb-5 text-primary">
            <label data-error="wrong" data-success="right" for="date">Zeljeni datum</label>
            <input type="text" class="datepickeri form-control" id="datum`+z+`" name="date"
            min="2022-07-12" onfocus="(this.type='date')">
            <label id="erd" style="color: red;">Izaberite datum</label>
        </div>
            </div>
            <div class="modal-footer d-flex justify-content-center"><button type="submit" data-toggle="modal" id="edit" name="edit" onclick="zakazi('`+data.name+`',`+z+`)" class="btn btn-deep-orange text-white">Zakazi</button>
            <label class="text-lg-right" id="eril" style="color: red;" hidden>Nemate vise termina !</label>
            </div>
            </div>
        </div>


        <div id="e" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" hidden>
        <div class="modal-content bg-dark text-white">
                <div class="modal-header text-center">
                <h4 class="modal-title w-100 font-weight-bold text-primary">Komentar na objekat</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body mx-3">

            <div class="md-form mb-5 text-primary">
                <label data-error="wrong" data-success="right" for="name">Komentar</label>
                <input type="text" id="name" name="text" class="form-control validate">
            </div>

            <div class="md-form mb-5 text-primary">
            <div>
                <label data-error="wrong" data-success="right" for="username">Ocena</label>
            </div>
            <select class="form-select" name="rate">
                <option value=1 selected>1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </div>

    </div>
    <div class="modal-footer d-flex justify-content-center"><button type="submit" id="edit" name="edit" class="btn btn-deep-orange text-white" onclick="sendComment()">Posalji</button>
    </div>
    </div>
</div>


        </div>`;
        z++;
    }
    let n = document.getElementById("form");
    n.innerHTML = i;
}

function loadComments(id){

    let komentari;
    $.get({
        url: "../rest/sports/acceptedComments?name=" + id,
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            ispis(data);
        }
    })

}

function loadAllComments(id){

    let komentari;
    $.get({
        url: "../rest/sports/allComments?name=" + id,
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            ispis(data);
        }
    })
    
    
}

function ispis(komentari){
    let i = "";

    for(let k of komentari){
        if(k.accepted){
        i = i + `<div id="first-comment">
        <div class="comment__card">
          <h3 class="comment__title">`+k.customer.name+` `+k.customer.surname+`</h3>
          <p>`+k.text+`</p>
          <div class="comment__card-footer">
            <div>Ocena: `+k.rate+`</div>
          </div>
        </div>
        </div>`;}
        else {
            i = i + `<div id="first-comment">
        <div class="comment__card">
          <h3 class="comment__title">`+k.customer.name+` `+k.customer.surname+`</h3>
          <p>`+k.text+`</p>
          <div class="comment__card-footer">
            <button class="btn btn-primary" onclick="acceptComment('`+k.text+`')">Odobri</button>
            <div>Ocena: `+k.rate+`</div>
          </div>
        </div>
        </div>`;
        }
    }
    let n = document.getElementById("comments");
    n.innerHTML = i;
}

function commentSection(){

    let n = document.getElementById("e");
    n.removeAttribute("hidden");
}

function sendComment(){
    let text = $('input[name="text"]').val();
    let rate = $('select[name="rate"]').find(":selected").val();

    const url = new URLSearchParams(window.location.search);
    var id = url.get('name');

    $.post({
        url: "../rest/info/addComment?text="+text+"&rate="+rate+"&name="+id,
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        dataType:"json",
        success: function(objekat){
            
        }
    })
}

function acceptComment(text){
    $.ajax({
        type: "PUT",
        url: "../rest/info/acceptComment?text="+text,

        contentType:"application/json",
        dataType:"json",
        success: function(odgovor) {
            alert("Uspesno odobren komentar!")
        },
        error: function(odgovor) {
            alert(odgovor.responseText);
        }
    });
}