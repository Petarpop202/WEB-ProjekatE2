function create(document){
    let name = $('input[name="name"]').val();
    let type = document.getElementById("type").value;
    let facility = document.getElementById("facility").value;
    let duration = $('input[name="duration"]').val();
    let trainer = document.getElementById("trainer").value;
    let description = $('input[name="description"]').val();
    var input = document.getElementById("picture");
    var file = input.value.split("\\");
    var one = file[file.length-1];
    var pic = one.split(".");
    var picture = pic[0];    


        $.post({
            url: "../rest/sports/training?name=" + name + "&type="+ type + "&facility=" + facility + "&duration=" + duration + "&trainer=" + trainer + "&picture=" + picture + "&description=" + description,
            contentType: "application/json",

            success: function(odgovor) {
                alert("Trening " + odgovor.name + " je registrovan!")
                
            },
            error: function(odgovor) {
                if (odgovor.status == 400) {
                    alert("Greska pri kreiranju treninga!");
                }
            }
        });
    

}

$(window).ready(function() {
$("#createTraining").click(function (event) {
    event.preventDefault();
    create(document);
    
});
})


$(document).ready(function () {
    $.ajax({
        url : "../rest/sports/facilities",
        type: "GET",
        success: function(data)
        {
            getFacilities(data);
        }
    });
})


function getFacilities(facilities){
    let i = "";
    for(let f of facilities){
        i = i + "<option value="+ f.name +">" + f.name+ "</option>"
    }

    let obj  = document.getElementById("facility");
    obj.innerHTML = i;
    }

    $(document).ready(function () {
        $.ajax({
            url : "../rest/sports/trainers",
            type: "GET",
            success: function(data)
            {
                getTrainers(data);
            }
        });
    })
    
    
    function getTrainers(trainers){
        let i = "";
        for(let t of trainers){
            i = i + "<option value="+ t.username +">"+t.name+" "+t.surname+"</option>"
        }
    
        let obj  = document.getElementById("trainer");
        obj.innerHTML = i;
        }