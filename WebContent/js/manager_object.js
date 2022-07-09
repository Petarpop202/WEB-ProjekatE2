$(document).ready(function () {

    let s;
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
            loadEditPage(data);  
            createTrainers(data); 
        }
    })

 });




 function createNaslov(objekat){

    let i = document.getElementById("naslov");
    i.innerHTML = '<div class="row"><div class="profile-nav col-md-3"><div class="panel" ><div class="user-heading round" ><img style="width: 100%;" src="../img/'+objekat.picture+'.jpg" alt=""><h1>'+objekat.name+'</h1><h3>'+objekat.location.address+'</h3></div></div></div><div class="profile-info col-md-9"><div class="panel"></div><div class="panel"><div class="panel-body bio-graph-info"><h1>Informacije o objektu</h1><div class="bio-row"><h4><span>Aktivan</span>: '+objekat.statusStr+'</h4></div><div class="bio-row"><h4><span>Tip Objekta</span>: '+objekat.typeStr+'</h4></div><div class="bio-row"><h4><span>Radno vreme </span>: '+objekat.workTime+'</h4></div><div class="bio-row"><h4><span>Adresa </span>: '+objekat.location.address+'</h4></div></div></div></div></div><div class="mt-5"><h1 style="text-align:center">Sadrzaj objekta</h1></div>'
 }

 function createCards(objekat){
    let i ="";
    let z = 0;
    for(let data of objekat){
    i = i + `<div class="col-sm mt-5 d-flex justify-content-center">
    <div class="card border-success" style="width: 19rem;">
        <img src="../img/`+data.picture+`.jpg" class="card-img-top" alt="...">
        <div class="card-body p-4 rounded-bottom" >
            <h5 class="card-title font-weight-bold">`+data.name+`&nbsp;&nbsp;&nbsp;&nbsp;</h5>
            <p class="card-text">`+data.description+`</p>
            <p class="card-text">String&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje</p>
            <div class="float-start">
                <a href="" data-toggle="modal" data-target="#modalEditContent" onclick="loadEditPage('`+data.name+`','`+data.duration+`','`+data.description+`')" class="btn btn-primary">Izmeni</a>
                <a href="" class="btn btn-primary">Izbrisi</a>
                <p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trajanje: `+data.duration+`min</p>
            </div>
        </div>
    </div>
</div>`;
z++;
}
let n = document.getElementById("sadrzaj");
n.innerHTML = i;
 }

 function createTrainers(objekat){
    let i = '<tr><th>#</th><th>Ime</th><th>Prezime</th><th>Korisnicko ime</th><th>Datum rodjenja</th><th>Pol</th></tr>';
    let h = 1;
    for(let s of objekat){
        if(s.trainer.gender)
            p = "Musko";
        else p = "Zensko"
        i = i + '<tr><td>'+h+'</td><td>'+s.trainer.name+'</td><td>'+s.trainer.surname+'</td><td>'+s.trainer.username+'</td><td>'+s.trainer.date+'</td><td>'+p+'</td></tr>';
        h++;
    }
    let n = document.getElementById("tabela");
    n.innerHTML = i;
 }


 function loadEditPage(name,duration,description){
    let i = 0;
    let h = "";
    let n = document.getElementById("edit");
    h = h + `<div class="modal fade" id="modalEditContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content bg-dark text-white">
                            <div class="modal-header text-center">
                                <h4 class="modal-title w-100 font-weight-bold text-primary">Izmena naloga</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            </div>
                                
                            <div class="modal-body mx-3">
                                    <div class="md-form mb-5 text-primary"><i class="fas fa-user prefix grey-text"></i><label data-error="wrong" data-success="right" for="name">Naziv treninga</label>
                                    <input type="text" value="`+name+`" id="name" name="trainingName" class="form-control validate" disabled>
                            </div>
                                
                            <div class="md-form mb-5 text-primary">
                                <i class="fas fa-envelope prefix grey-text"></i>
                                <div>
                                    <label data-error="wrong" data-success="right" for="username">Tip treninga</label>
                                </div>
                                <select class="form-select" name="type">
                                    <option value=Teretana selected>Teretana</option>
                                    <option value="Grupni trening">Grupni trening</option>
                                    <option value="Personalni trening">Personalni trening</option>
                                </select>
                            </div>

                            <div class="form-group col-auto">
                            <label for="trainer">Trener</label>
                            <select id="trainer" class="form-group col-auto custom-select">
                                
                            </select>
                        </div>
                            
                            <div class="md-form mb-5 text-primary">
                                <i class="fas fa-envelope prefix grey-text"></i>
                                <label data-error="wrong" data-success="right" for="duration">Trajanje</label>
                                <input type="text" value="`+duration+`" name="duration" class="form-control validate">
                            </div>



                            <div class="md-form mb-5 text-primary">
                                <i class="fas fa-envelope prefix grey-text"></i>
                                <label data-error="wrong" data-success="right" for="description">Opis</label>
                                <input type="text" value="`+description+`" name="description" class="form-control validate">
                            </div>
                            
                        </div>
                        <div class="modal-footer d-flex justify-content-center"><button type="submit" id="edit" name="edit" onclick="EditStart()" class="btn btn-deep-orange text-white">Izmeni</button>
                        </div>
                    </div>
                    </div>
                </div>`;
                    i++;
                n.innerHTML = h;
                putTrainers();
}


    function getTrainers(trainers){
        let i = "";
        for(let t of trainers){
            i = i + "<option value="+ t.username +">"+t.name+" "+t.surname+"</option>"
        }
        let obj  = document.getElementById("trainer");
        obj.innerHTML = i;
    }

        function EditStart(){
            let trainingName = $('input[name="trainingName"]').val();
            let duration = $('input[name="duration"]').val();
            let description = $('input[name="description"]').val();
            let trainer = document.getElementById("trainer").value;
            let type = $('select[name="type"]').find(":selected").val();


            $.ajax({
                type: "PUT",
                url: "../rest/sports/editTraining?trainingName="+trainingName+ "&duration=" + duration + "&description=" + description + "&trainer=" + trainer + "&type=" + type,

                contentType:"application/json",
                dataType:"json",
                success: function(odgovor) {
                    alert("Uspesno izmenjen trening!")
                    window.location.assign("http://localhost:8080/FitnessCentar/html/manager_object_page.html");
                },
                error: function(odgovor) {
                    alert(odgovor.responseText);
                    if(odgovor.status == 401) {
                        alert("Greska!");
                    }
                }
            });
            
    }


    function putTrainers(){
        $.get({
            url : "../rest/sports/trainers",
            contentType:"application/json",
            dataType:"json",
            success: function(data)
            {
                getTrainers(data);
            }
        })
    }
