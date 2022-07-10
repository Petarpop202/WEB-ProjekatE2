$(document).ready(function () {
    $.ajax({
        url : "../rest/info/getUsers",
        type: "GET",
        success: function(data)
        {
           let i = 1;
           let h = '<tr><th>#</th><th>Ime</th><th>Prezime</th><th>Korisnicko ime</th><th>Datum rodjenja</th><th>Aktivan</th><th>Uloga</th><th>Ukloni</th></tr>';
           for(let s of data){
                if(s.gender)
                    p = "Musko";
                else p = "Zensko"
                if(s.role == "CUSTOMER")
                    ul = "Kupac";
                else if(s.role == "ADMIN")
                    ul = "Administrator";
                else if(s.role == "MANAGER")
                    ul = "Menadzer";
                else ul = "Trener";
                h += '<tr><td>'+i+'</td><td>'+s.name+'</td><td>'+s.surname+'</td><td>'+s.username+'</td><td>'+s.date+'</td><td>'+p+'</td><td>'+ul+'</td><td><button onclick="al(`'+s.username+'`)" class="btn btn-primary">Izbrisi</button></td></tr>';
                i++;
           }
           let u = document.getElementById("tabela");
           u.innerHTML = h;
        }
    });

})

$(document).ready(function(){
$("#searchBtn").click(function(event){
    let name = $('input[name="searchBox"]').val();
    let surname = $('input[name="searchBox"]').val();
    let username = $('input[name="searchBox"]').val();
    let opt = document.getElementById("options").value;
    let url;
    
        url = "../rest/info/searchUsers?name=" + name + "&surname=" + surname + "&username=" + username + "&opt=" + opt;

            $.get({
                url: url,
                contentType:"application/json",
                dataType:"json", 
                success:function(korisnici){
                    let i = 1;
                    let h = '<tr><th>#</th><th>Ime</th><th>Prezime</th><th>Korisnicko ime</th><th>Datum rodjenja</th><th>Aktivan</th><th>Uloga</th><th>Ukloni</th></tr>';
                    for(let s of korisnici){
                         if(s.gender)
                             p = "Musko";
                         else p = "Zensko"
                         if(s.role == "CUSTOMER")
                             ul = "Kupac";
                         else if(s.role == "ADMIN")
                             ul = "Administrator";
                         else if(s.role == "MANAGER")
                             ul = "Menadzer";
                         else ul = "Trener";
                         h += '<tr><td>'+i+'</td><td>'+s.name+'</td><td>'+s.surname+'</td><td>'+s.username+'</td><td>'+s.date+'</td><td>'+p+'</td><td>'+ul+'</td><td><button onclick="al(`'+s.username+'`)" class="btn btn-primary">Izbrisi</button></td></tr>';
                         i++;
                    }
                    let u = document.getElementById("tabela");
                    u.innerHTML = h;
                },
                error:function(){
                    alert("Greska!");
                }
            });
    event.preventDefault();
});


$("#sortBtn").click(function(event){

    let opt = document.getElementById("options").value;
    let sortOptions = document.getElementById("sortOptions").value;
    let url;
    
        url = "../rest/info/sortUsers?opt=" + opt + "&sortOptions=" + sortOptions;

            $.get({
                url: url,
                contentType:"application/json",
                dataType:"json", 
                success:function(korisnici){
                    let i = 1;
                    let h = '<tr><th>#</th><th>Ime</th><th>Prezime</th><th>Korisnicko ime</th><th>Datum rodjenja</th><th>Aktivan</th><th>Uloga</th><th>Ukloni</th></tr>';
                    for(let s of korisnici){
                         if(s.gender)
                             p = "Musko";
                         else p = "Zensko"
                         if(s.role == "CUSTOMER")
                             ul = "Kupac";
                         else if(s.role == "ADMIN")
                             ul = "Administrator";
                         else if(s.role == "MANAGER")
                             ul = "Menadzer";
                         else ul = "Trener";
                         h += '<tr><td>'+i+'</td><td>'+s.name+'</td><td>'+s.surname+'</td><td>'+s.username+'</td><td>'+s.date+'</td><td>'+p+'</td><td>'+ul+'</td><td><button onclick="al(`'+s.username+'`)" class="btn btn-primary">Izbrisi</button></td></tr>';
                         i++;
                    }
                    let u = document.getElementById("tabela");
                    u.innerHTML = h;
                },
                error:function(){
                    alert("Greska!");
                }
            });
    event.preventDefault();
});

})

function al(i){
    $.ajax({
        type: "PUT",
        url: "../rest/edit/delete?username="+i,
        contentType:"application/json",
        dataType:"json",
        success: function(odgovor) {
            alert("Uspesno izbrisan korisnik!")
            window.location.reload();
        },
        error: function(odgovor) {
        }
    });
}

function filtriraj(name){

    $.get({
        url: "../rest/info/filterUsers?name=" + name,
        contentType:"application/json",
        dataType:"json", 
        success:function(korisnici){
            let i = 1;
            let h = '<tr><th>#</th><th>Ime</th><th>Prezime</th><th>Korisnicko ime</th><th>Datum rodjenja</th><th>Aktivan</th><th>Uloga</th><th>Ukloni</th></tr>';
            for(let s of korisnici){
                 if(s.gender)
                     p = "Musko";
                 else p = "Zensko"
                 if(s.role == "CUSTOMER")
                     ul = "Kupac";
                 else if(s.role == "ADMIN")
                     ul = "Administrator";
                 else if(s.role == "MANAGER")
                     ul = "Menadzer";
                 else ul = "Trener";
                 h += '<tr><td>'+i+'</td><td>'+s.name+'</td><td>'+s.surname+'</td><td>'+s.username+'</td><td>'+s.date+'</td><td>'+p+'</td><td>'+ul+'</td><td><button onclick="al(`'+s.username+'`)" class="btn btn-primary">Izbrisi</button></td></tr>';
                 i++;
            }
            let u = document.getElementById("tabela");
            u.innerHTML = h;
        },
        error:function(){
            alert("Greska!");
        }
    });
}