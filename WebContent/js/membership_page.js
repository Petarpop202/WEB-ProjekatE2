
$(document).ready(function () {
    setTime();
    $.ajax({
        url : "../rest/info/getMember",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            if(data.status)
                 s = "Aktivna";
            else s = "Neaktivna";
            if(data.customer.type.type == "BRONZE")
                i = "bronze";
            else if(data.customer.type.type == "SILVER")
                i = "silver";
            else i = "gold";
            let n = document.getElementById("nameMain");	
            if(data != "")
            	n.innerHTML = ' <h3 class="fs-1">Trenutna clanarina:</h3><div class="card bg-dark text-white mr-3" style="width: 18rem;"><img src="../img/'+i+'.jpg" style="width: 500px; height:200px;" class="card-img" alt="..."><div class="card-img-overlay"><h5 class="card-title">'+data.typeStr+'</h5><p class="card-text">Ova clanarina je stalna &nbsp;&nbsp;&nbsp;&nbsp; Clanarina je kupljena : '+data.payDate+' Clanarina vam istice : '+data.memberDate+'</p><p class="card-text">Status: '+s+'</p></div></div>';
       		else n.innerHTML = '<h3>Trenutno nemate aktivnu clanarinu !</h3>';
       		
        }
    });
})

function setTime(){
    var today = new Date();
    var date =(today. getDate() + 1) +'.'+(today. getMonth() + 1)+'.'+ today. getFullYear()+'.';
    var dateTime = date;
    let n = document.getElementById("day");
    n.innerHTML = 'Datum isteka: '+dateTime;

    var today = new Date();
    var date =today. getDate() +'.'+(today. getMonth() + 2)+'.'+ today. getFullYear()+'.';
    var dateTime = date;
    let nm = document.getElementById("month");
    nm.innerHTML = 'Datum isteka: '+dateTime;

    var today = new Date();
    var date =today. getDate() +'.'+(today. getMonth() + 1)+'.'+ (today. getFullYear() + 1)+'.';
    var dateTime = date;
    let ny = document.getElementById("year");
    ny.innerHTML = 'Datum isteka: '+dateTime;
}

function profile(){
    location.assign("profile_page.html");
}
function buyDay(){
    var today = new Date();
    var date =today. getDate() +'.'+(today. getMonth() + 1)+'.'+ today. getFullYear()+'.';
    var date1 =(today. getDate() + 1) +'.'+(today. getMonth() + 1)+'.'+ today. getFullYear()+'.';
    $.ajax({
        url : "../rest/info/getUser",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            let price = 700.0;
            let termins = 1000;
            let type = "Dnevna";
            setMember(date,date1,price,termins,type,data);
        },
        error: function(odgovor) {
            
        }
    });
    

}
function buyMonth(){
    var today = new Date();
    var date =today. getDate() +'.'+(today. getMonth() + 1)+'.'+ today. getFullYear()+'.';
    var date1 =today. getDate() +'.'+(today. getMonth() + 2)+'.'+ today. getFullYear()+'.';
    $.ajax({
        url : "../rest/info/getUser",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            let price = 3000.0;
            let termins = 20;
            let type = "Mesecna";
            setMember(date,date1,price,termins,type,data);
        },
        error: function(odgovor) {
            
        }
    });
}
function buyYear(){
    var today = new Date();
    var date = today. getDate() +'.'+(today. getMonth() + 1)+'.'+ today. getFullYear()+'.';
    var date1 =today. getDate() +'.'+(today. getMonth() + 1)+'.'+ (today. getFullYear() +1)+'.';
    $.ajax({
        url : "../rest/info/getUser",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            let price = 30000.0;
            let termins = 1000;
            let type = "Godisnja";
            setMember(date,date1,price,termins,type,data);
        },
        error: function(odgovor) {
            
        }
    });
}

function setMember(date,date1,price,termins,type,data){

    $.post({
        url: "../rest/info/setMember",
        contentType: "application/json",
        data: JSON.stringify({
            typeStr: type,
            payDate: date,
            memberDate: date1,
            price: price,
            status: true,
            termins: termins,
            customer: data,
        }),
        success: function(odgovor) {
            alert("Clanarina je uspesno kupljena!")
            window.location("http://localhost:8080/FitnessCentar/html/membership_page.html")
            
        },
        error: function(odgovor) {
            
        }
    });
}