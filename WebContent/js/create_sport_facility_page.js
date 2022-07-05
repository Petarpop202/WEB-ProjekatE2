function create(document){
        let name = $('input[name="name"]').val();
        let type = document.getElementById("type").value;
        let address = $('input[name="address"]').val();
        var input = document.getElementById("picture");
        var file = input.value.split("\\");
        var picture = file[file.length-1];
        alert(picture);
        


            $.post({
                url: "../rest/sports/create?name=" + name + "&type="+ type + "&address=" + address + "&picture=" + picture,
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