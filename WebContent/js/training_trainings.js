$(document).ready(function () {
    $.ajax({
        url : "../rest/info/coachCheckedTrainings",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        type: "GET",
        success: function(data)
        {
            let i = '<tr><th>#</th><th>Trening</th><th>Objekat</th><th>Datum</th></tr>';
            let z = '<tr><th>#</th><th>Trening</th><th>Objekat</th><th>Datum</th><th>Otkazi trening</th></tr>';
            let h = 1;
            let l = 1;

            for(let s of data){
                if(s.training.type != "PERSONAL"){
                i = i + '<tr><td>'+h+'</td><td>'+s.training.name+'</td><td>'+s.training.facility.name+'</td><td>'+s.trainingDate+'</td></tr>';
                h++;
            }
                else {z = z + '<tr><td>'+l+'</td><td>'+s.training.name+'</td><td>'+s.training.facility.name+'</td><td>'+s.trainingDate+'</td><td><button class="btn btn-primary" onclick="otkaziTr(`'+s.trainingDate+'`)">Otkazi</button></td></tr>';
                l++;
            }
            }

            let n = document.getElementById("tabela");
            n.innerHTML = i;
            let s = document.getElementById("tabela1")
            s.innerHTML = z;
        }
    });
})

function otkaziTr(data){
    $.ajax({
        type: "PUT",
        url: "../rest/sports/delete?date="+data,
        contentType:"application/json",
        dataType:"json",
        success: function(odgovor) {
            alert("Uspesno izbrisan korisnik!")
        },
        error: function(odgovor) {
            if(odgovor.status == 400)
            alert("Nije moguce otkazati trening manje od dva dana ranije !");
            else alert("Greska pri otkazivanju !");
        }
    });
}