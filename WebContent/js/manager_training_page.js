function create(document){
    let name = $('input[name="name"]').val();
    let type = document.getElementById("type").value;
    let facility = document.getElementById("facility").value;
    let duration = $('input[name="duration"]').val();
    let trainer = document.getElementById("trainer").value;
    let description = $('input[name="description"]').val();
    let price = $('input[name="price"]').val();
    var input = document.getElementById("picture");
    var file = input.value.split("\\");
    var one = file[file.length-1];
    var pic = one.split(".");
    var picture = pic[0];    


        $.post({
            url: "../rest/sports/training?name=" + name + "&type="+ type + "&facility=" + facility + "&duration=" + duration + "&trainer=" + trainer + "&picture=" + picture + "&description=" + description + "&price=" + price,
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
            url : "../rest/sports/trainers",
            type: "GET",
            success: function(data)
            {
                getTrainers(data);
            }
        });

        $.ajax({
            url : "../rest/sports/getFacility",
            headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
            type: "GET",
            success: function(facility)
            {
                getFacility(facility);
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

        function getFacility(facility){
            let i = "";
                i = i + "<option value="+"'"+ facility.name+"'"+">"+facility.name+"</option>"

        
            let obj  = document.getElementById("facility");
            obj.innerHTML = i;
            }