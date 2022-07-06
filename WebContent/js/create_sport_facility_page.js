function create(document){
        let name = $('input[name="name"]').val();
        let type = document.getElementById("type").value;
        let address = $('input[name="address"]').val();
        let width = $('input[name="width"]').val();
        let length = $('input[name="length"]').val();
        let manager = document.getElementById("manager").value;
        var input = document.getElementById("picture");
        var file = input.value.split("\\");
        var one = file[file.length-1];
        var pic = one.split(".");
        var picture = pic[0];
        alert(picture);
        
        


            $.post({
                url: "../rest/sports/create?name=" + name + "&type="+ type + "&address=" + address + "&width=" + width + "&length=" + length + "&picture=" + picture + "&manager=" + manager,
                contentType: "application/json",

                success: function(odgovor) {
                    alert("Objekat " + odgovor.name + " je registrovan!")
                    
                },
                error: function(odgovor) {
                    if (odgovor.status == 400) {
                        alert("Greska pri kreiranju objekta!");
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
        url: "../rest/sports/available",
        type: "GET",
        success: function(objekat){
            getAvailableManagers(objekat);
        }
    });
    })

function getAvailableManagers(managers){
    let i = "";
    for(let m in managers){
        i = i + "<option value="+ m.name +"></option> "
    }
    
    let obj  = document.getElementById("manager");
}