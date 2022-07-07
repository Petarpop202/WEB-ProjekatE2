$(document).ready(function () {
    $.ajax({
        url : "../rest/info/userTrainingHistory",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        type: "GET",
        success: function(data)
        {
            let i = '<tr><th>#</th><th>Trening</th><th>Trener</th><th>Datum</th></tr>';
            let h = 1;
            for(let s of data){
                i = i + '<tr><td>'+h+'</td><td>'+s.training.name+'</td><td>'+s.trainer.name+' '+s.trainer.surname+'</td><td>'+s.date+'</td></tr>';
                h++;
            }
            let n = document.getElementById("tabela");
            n.innerHTML = i;
        }
    });

})