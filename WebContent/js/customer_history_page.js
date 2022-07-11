$(document).ready(function () {
    $.ajax({
        url : "../rest/info/userTrainingHistory",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        type: "GET",
        success: function(data)
        {
            let i = '<tr><th>#</th><th>Objekat</th><th>Trening</th><th>Trener</th><th>Datum prijave</th><th>Datum treninga</th></tr>';
            let h = 1;
            for(let s of data){
                i = i + '<tr><td>'+h+'</td><td>'+s.training.name+'</td><td>'+s.trainer.name+' '+s.trainer.surname+'</td><td>'+s.date+'</td><td>'+s.date+'</td></tr>';
                h++;
            }
            let n = document.getElementById("tabela");
            n.innerHTML = i;
        }
    });

    $.ajax({
        url : "../rest/info/userCheckedTrainings",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        type: "GET",
        success: function(data)
        {
            let i = '<tr><th>#</th><th>Objekat</th><th>Trening</th><th>Trener</th><th>Datum prijave</th><th>Datum treninga</th></tr>';
            let h = 1;
            for(let s of data){
                i = i + '<tr><td>'+h+'</td><td>'+s.training.facility.name+'</td><td>'+s.training.name+'</td><td>'+s.trainer.name+' '+s.trainer.surname+'</td><td>'+s.checkedDate+'</td><td>'+s.trainingDate+'</td></tr>';
                h++;
            }
            let n = document.getElementById("tabela1");
            n.innerHTML = i;
        }
    });

    $("#searchBtn").click(function(event){
        let name = $('input[name="searchBox"]').val();
        let startDate = $('input#pocetniDatum').val();
        let endDate = $('input#krajnjiDatum').val();
        let opt = document.getElementById("options").value;
        let url;
        
            url = "../rest/info/searchTrainingsUser?name=" + name + "&endDate=" + endDate + "&startDate=" + startDate + "&opt=" + opt;
    
                $.get({
                    
                    url: url,
                    headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
                    contentType:"application/json",
                    dataType:"json", 
                    
                    success: function(data)
                    {
                        let i = '<tr><th>#</th><th>Objekat</th><th>Trening</th><th>Trener</th><th>Datum prijave</th><th>Datum treninga</th></tr>';
                        let h = 1;
                        for(let s of data){
                            i = i + '<tr><td>'+h+'</td><td>'+s.training.facility.name+'</td><td>'+s.training.name+'</td><td>'+s.trainer.name+' '+s.trainer.surname+'</td><td>'+s.checkedDate+'</td><td>'+s.trainingDate+'</td></tr>';
                            h++;
                        }
                        let n = document.getElementById("tabela1");
                        n.innerHTML = i;
                    }
                });
        event.preventDefault();
    });

$("#sortBtn").click(function(event){

    let opt = document.getElementById("options").value;
    let sortOptions = document.getElementById("sortOptions").value;
    let url;
    
        url = "../rest/info/sortTrainingsUser?opt=" + opt + "&sortOptions=" + sortOptions;

            $.get({
                url: url,
                headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
                contentType:"application/json",
                dataType:"json", 
                
                success: function(data)
        {
            let i = '<tr><th>#</th><th>Objekat</th><th>Trening</th><th>Trener</th><th>Datum prijave</th><th>Datum treninga</th></tr>';
            let h = 1;
            for(let s of data){
                i = i + '<tr><td>'+h+'</td><td>'+s.training.facility.name+'</td><td>'+s.training.name+'</td><td>'+s.trainer.name+' '+s.trainer.surname+'</td><td>'+s.checkedDate+'</td><td>'+s.trainingDate+'</td></tr>';
                h++;
            }
            let n = document.getElementById("tabela1");
            n.innerHTML = i;
        }
            });
    event.preventDefault();
    });

})

function filtriraj(name){
    let url;

    url = "../rest/info/filterTrainingsUser?name=" + name;
    $.get({
        url: url,
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        dataType:"json", 
        
        success: function(data)
                    {
                        let i = '<tr><th>#</th><th>Objekat</th><th>Trening</th><th>Trener</th><th>Datum prijave</th><th>Datum treninga</th></tr>';
                        let h = 1;
                        for(let s of data){
                            i = i + '<tr><td>'+h+'</td><td>'+s.training.facility.name+'</td><td>'+s.training.name+'</td><td>'+s.trainer.name+' '+s.trainer.surname+'</td><td>'+s.checkedDate+'</td><td>'+s.trainingDate+'</td></tr>';
                            h++;
                        }
                        let n = document.getElementById("tabela1");
                        n.innerHTML = i;
                    },
        error:function(){
            alert("Greska!");
        }
    });
}