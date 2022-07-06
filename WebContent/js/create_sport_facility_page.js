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
        url : "../rest/sports/available",
        type: "GET",
        success: function(data)
        {
            getAvailableManagers(data);
        }
    });
})


function getAvailableManagers(managers){
    let i = "";
    for(let m of managers){
        i = i + "<option value="+ m.username +">"+m.name+" "+m.surname+"</option> "
    }
    
    let obj  = document.getElementById("manager");
    
    obj.innerHTML = i;
    }
}


function validateCheck(document){
    let username = $('input[name="username"]').val();
        let password = $('input[name="password"]').val();
        let name2 = $('input[name="name2"]').val();
        let surname = $('input[name="surname"]').val();
        let gender = $('select[name="gender"]').find(":selected").val();
        let date = $('input#datum').val();
        let validate = false;

        var regName = new RegExp('([A-Z]{1})([a-z]+)$');
        var regUser = new RegExp('[a-zA-Z0-9]+$');
        if(!regName.test(name2)){
            document.getElementById("eri").removeAttribute("hidden");
        }else{
            document.getElementById("eri").setAttribute("hidden","true");
            validate = true;
        }
        if(!regName.test(surname)){
            document.getElementById("erp").removeAttribute("hidden");
            validate = false;
        }
        else{
            document.getElementById("erp").setAttribute("hidden","true");
            if(validate)
            validate = true;
        }
        if(date == ""){
            document.getElementById("erd").removeAttribute("hidden");
            validate = false;
        }
        else{
            document.getElementById("erd").setAttribute("hidden","true");
            if(validate)
            validate = true;
        }
        if(!regUser.test(username)){
            document.getElementById("erk").removeAttribute("hidden");
            validate = false;
        }
        else{
            document.getElementById("erk").setAttribute("hidden","true");
            if(validate)
            validate = true;
        }
        if(!regUser.test(password)){
            document.getElementById("erl").removeAttribute("hidden");
            validate = false;
        }
        else{
            document.getElementById("erl").setAttribute("hidden","true");
            if(validate)
            validate = true;
        }

        if(validate){
            $.post({
                url: "../rest/registration/manager",
                contentType: "application/json",
                data: JSON.stringify({
                    username: username,
                    password: password,
                    name: name2,
                    surname: surname,
                    gender: gender,
                    date: date,
                }),
                success: function(odgovor) {
                    alert("Korisnik " + odgovor.username + " je registrovan!")
                    
                    
                },
                error: function(odgovor) {
                    if (odgovor.status == 400) {
                        document.getElementById("errorUser").removeAttribute("hidden");
                    }else {
                        document.getElementById("errorUser").setAttribute("hidden","true");
                        alert("Greska pri registraciji!");
                    }
                }
            });
        }

}
$(window).ready(function() {
    $("#register").click(function (event) {
        event.preventDefault();
        validateCheck(document);
        
    });
})

