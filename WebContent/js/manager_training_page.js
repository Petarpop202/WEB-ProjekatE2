function create(document){
    let name = $('input[name="name"]').val();
    let type = document.getElementById("type").value;
    let facility = document.getElementById("facility").value;
    let duration = $('input[name="duration"]').val();
    let trainer = document.getElementById("manager").value;
    let description = document.getElementById("description").value;
    var input = document.getElementById("picture");
    var file = input.value.split("\\");
    var one = file[file.length-1];
    var pic = one.split(".");
    var picture = pic[0];
    alert(picture);
    
    


        $.post({
            url: "../rest/sports/create?name=" + name + "&type="+ type + "&facility=" + facility + "&duration=" + duration + "&trainer=" + trainer + "&picture=" + picture + "&description=" + description,
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
$("#create").click(function (event) {
    event.preventDefault();
    create(document);
    
});
})


$(document).ready(function () {
    $.ajax({
        url : "../rest/sports/trainer",
        type: "GET",
        success: function(data)
        {
            getDropDowns(data);
        }
    });
})


function getDropDowns(trainers, facilities){
    let i = "";
    let j = "";
    for(let t of trainers){
        i = i + "<option value="+ t.username +">"+t.name+" "+t.surname+"</option> "
    }

    for(let sf of faiclity){
        j = j + "<option value="+ sf.username + ">" + sf.name + "</option> "
    }


    
    let obj  = document.getElementById("trainer");
    let obj2  = document.getElementById("facility");
    
    obj.innerHTML = i;
    obj2.innerHTML = j;
    }