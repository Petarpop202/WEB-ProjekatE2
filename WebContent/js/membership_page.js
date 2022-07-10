
$(document).ready(function () {
    
    
    $.ajax({
        url : "../rest/info/getMember",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
        setInfo(data);
	    let n = document.getElementById("nameMain");	
	    if(data != ""){
            if(data.status)
                 s = "Aktivna";
            else s = "Neaktivna";
            if(data.customer.type.type == "BRONZE")
                i = "bronze";
            else if(data.customer.type.type == "SILVER")
                i = "silver";
            else i = "gold";
           
         
            n.innerHTML = ' <h3 class="fs-1">Trenutna clanarina:</h3><div class="card bg-dark text-white mr-3" style="width: 18rem;"><img src="../img/'+i+'.jpg" style="width: 500px; height:200px;" class="card-img" alt="..."><div class="card-img-overlay"><h5 class="card-title">'+data.typeStr+'</h5><p class="card-text">Ova clanarina je stalna &nbsp;&nbsp;&nbsp;&nbsp; Clanarina je kupljena : '+data.payDate+' Clanarina vam istice : '+data.memberDate+'</p><p class="card-text">Status: '+s+'</p></div></div>';
       		 }
       		 else n.innerHTML = '<h3>Trenutno nemate aktivnu clanarinu !</h3>';
       		
        }
    });

})


function profile(){
    location.assign("profile_page.html");
}
function buyDay(){
    var today = new Date();
    var date = today. getFullYear() +'-0'+ (today. getMonth() + 1) +'-0'+ today. getDate()  ;
    var date1 = today. getFullYear()+'-0'+(today. getMonth() + 1) +'-0'+(today. getDate() + 1);

    $.ajax({
        url : "../rest/info/getUser",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            let price = 700.0;
            let termins = 1;
            let type = "Dnevna";
            setMember(date,date1,price,termins,type,data);
        },
        error: function(odgovor) {
            
        }
    });
    

}
function buyMonth(){
    var today = new Date();
    var date = today. getFullYear()+'-0'+(today. getMonth() + 1)+'-0'+today. getDate() ;
    var date1 = today. getFullYear()+'-0'+(today. getMonth() + 2)+'-0'+today. getDate() ;
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
    var date =  today. getFullYear()+'-0'+(today. getMonth() + 1)+'-0'+today. getDate() ;
    var date1 =(today. getFullYear() +1)+'-0'+ (today. getMonth() + 1) +'-0'+today. getDate();
    $.ajax({
        url : "../rest/info/getUser",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
            let price = 30000.0;
            let termins = 365;
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

function setInfo(data){

    var today = new Date();
    var date =(today. getDate() + 1) +'-'+(today. getMonth() + 1)+'-'+ today. getFullYear();
    var dateTime = date;
    let nd = 'Datum isteka: '+dateTime;

    var today = new Date();
    var date =today. getDate() +'-'+(today. getMonth() + 2)+'-'+ today. getFullYear();
    var dateTime = date;
    let nm = 'Datum isteka: '+dateTime;

    var today = new Date();
    var date =today. getDate() +'-'+(today. getMonth() + 1)+'-'+ (today. getFullYear() + 1);
    var dateTime = date;
    let ny= 'Datum isteka: '+dateTime;

    let n = document.getElementById("info");
    let d = 0;
    let m = 0;
    let y = 0;
    if(data.customer.type.type == "BRONZE"){
        i = 700;
        m = 3000;
        y = 20000;
    }          
    else if(data.customer.type.type == "SILVER"){
        i = 630;
        m = 2700;
        y = 18000;
    }
    else {
        i = 560;
        m = 2400;
        y = 16000;
    };
    n.innerHTML = `<div class="card bg-dark text-white mr-3 text-center" style="width: 18rem;">
    <img src="../img/bronze.jpg" class="card-img" style="height: 250px;" alt="...">
    <div class="card-img-overlay">
    <h4 class="card-title text-center">Dnvena</h4>
    <h5 class="text-center">Cena: `+i+` din</h5>
      <p class="card-text">Kupovinom ove clanarine ostvarujete 1 termin u teretani za 1 dan.</p>

      <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#modalMembershipFormDay">Kupi</a>
    </div>
  </div>

  <div class="card bg-dark text-white mr-3 text-center" style="width: 18rem;">
    <img src="../img/silver.jpg" class="card-img" style="height: 250px;" alt="...">
    <div class="card-img-overlay">
        <h4 class="card-title text-center">Mesecna</h4>
        <h5 class="text-center">Cena: `+m+` din</h5>
      <p class="card-text">Kupovinom ove clanarine ostvarujete 20 termina u teretani za 1 mesec.</p>
      <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#modalMembershipFormMonth">Kupi</a>
    </div>
  </div>

  <div class="card bg-dark text-white mr-3 text-center" style="width: 18rem;">
    <img src="../img/gold.jpg" class="card-img" style="height: 250px;" alt="...">
    <div class="card-img-overlay">
      <h4 class="card-title text-center">Godisnja</h4>
      <h5 class="text-center">Cena: `+y+` din</h5>
      <p class="card-text">Kupovinom ove clanarine ostvarujete 365 termina u teretani za 1 godinu.</p>
      <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#modalMembershipFormYear">Kupi</a>
    </div>
  </div>
`;

let l = document.getElementById("jump");
l.innerHTML = `<div class="modal fade" id="modalMembershipFormMonth" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content bg-dark text-white">
    <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold text-primary">Mesecna</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="modal-body mx-3">

      <div class="text-normal">
          <h6>Broj termina: 20</h6>
          <h6 id="month">`+nm+`</h6>
          <h6 for="kod">Promo kod:</h6>
          <input type="text" id="kod" name="kodm" size="50"><br>
          <label id="scsm" style="color: green;" hidden>Kod je ispravan</label>
          <label id="errm" style="color: red;" hidden>Kod je istekao ili ne vazi</label>
          <button class="btn btn-primary" onclick="checkMonth()">Proveri kod</button>


        <h6 id="mm">Cena: `+m+`</h6>
      </h6>
  </div>
    </div>
    <div class="modal-footer d-flex justify-content-center">
        <button type="submit" id="buyButton" class="btn btn-default text-white" onclick="buyMonth()">Kupi</button>
    </div>
    </div>
</div>
</div>

<div class="modal fade" id="modalMembershipFormDay" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content bg-dark text-white">
    <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold text-primary">Dnevna</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="modal-body mx-3">

      <div class="text-normal">
          <h6>Broj termina: 1</h6>
          <h6 id="day">`+nd+`</h6>
          <h6 for="kod">Promo kod:</h6>
          <input type="text" id="kod" name="kodd" size="50"><br>
          <label id="scsd" style="color: green;" hidden>Kod je ispravan</label>
          <label id="errd" style="color: red;" hidden>Kod je istekao ili ne vazi</label>
          <button class="btn btn-primary" onclick="checkDay()">Probveri kod</button>


        <h6 id="dd">Cena: `+d+`</h6>
      </h6>
  </div>
    </div>
    <div class="modal-footer d-flex justify-content-center">
        <button type="submit" id="buyButton" class="btn btn-default text-white" onclick="buyDay()">Kupi</button>
    </div>
    </div>
</div>
</div>


<div class="modal fade" id="modalMembershipFormYear" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content bg-dark text-white">
    <div class="modal-header text-center">
        <h4 class="modal-title w-100 font-weight-bold text-primary">Godisnja</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="modal-body mx-3">

      <div class="text-normal">
          <h6>Broj termina: 365</h6>
          <h6 id="year">`+ny+`</h6>
          <h6 for="kod">Promo kod:</h6>
          <input type="text" id="kod" name="kody" size="50"><br>
          <label id="scsy" style="color: green;" hidden></label>
          <label id="erry" style="color: red;" hidden>Kod je istekao ili ne vazi</label>
          <button class="btn btn-primary" onclick="checkYear()">Proveri kod</button>


        <h6 id="yy">Cena: `+y+`</h6>
      </h6>
  </div>
    </div>
    <div class="modal-footer d-flex justify-content-center">
        <button type="submit" id="buyButton" class="btn btn-default text-white" onclick="buyYear()">Kupi</button>
    </div>
    </div>
</div>
</div>`;
}

function checkYear(){
	let code = $('input[name="kody"]').val();
	
	 $.ajax({
        url : "../rest/info/checkCode?code="+code,
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
			upisCeneY(data.discount);
            let n = document.getElementById("scsy");
            n.removeAttribute("hidden");
            n.innerHTML = 'Ostvarili ste '+data.discount+'% popusta !';
        },
        error: function(odgovor) {
            let n = document.getElementById("erry");
            n.removeAttribute("hidden");
        }
    });
}

function checkDay(){
	let code = $('input[name="kodd"]').val();
	
	 $.ajax({
        url : "../rest/info/checkCode?code="+code,
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
			upisCeneD(data.discount);
            let n = document.getElementById("scsd");
            n.removeAttribute("hidden");
            n.innerHTML = 'Ostvarili ste '+data.discount+'% popusta !';
        },
        error: function(odgovor) {
            let n = document.getElementById("errd");
            n.removeAttribute("hidden");
        }
    });
}

function checkMonth(){
	let code = $('input[name="kodm"]').val();
	
	 $.ajax({
        url : "../rest/info/checkCode?code="+code,
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
			upisCeneM(data.discount);
            let n = document.getElementById("scsm");
            n.removeAttribute("hidden");
            n.innerHTML = 'Ostvarili ste '+data.discount+'% popusta !';
        },
        error: function(odgovor) {
            let n = document.getElementById("errm");
            n.removeAttribute("hidden");
        }
    });
}

function upisCeneY(d){
	$.ajax({
        url : "../rest/info/getMember",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
        if(data.customer.type.type == "BRONZE"){
	        y = 20000;
   		 }          
    	else if(data.customer.type.type == "SILVER"){
	        y = 18000;
    	}
    	else {
       	 	y = 16000;
   		 };
       		let res = (1-(d/100))*y;
       		let n = document.getElementById("yy");
       		n.innerHTML = 'Nova cena: '+res+'din';
        }
    });
}

function upisCeneM(d){
	$.ajax({
        url : "../rest/info/getMember",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
        if(data.customer.type.type == "BRONZE"){
	        y = 3000;
   		 }          
    	else if(data.customer.type.type == "SILVER"){
	        y = 2700;
    	}
    	else {
       	 	y = 2400;
   		 };
       		let res = (1-(d/100))*y;
       		let n = document.getElementById("mm");
       		n.innerHTML = 'Nova cena: '+res+'din';
        }
    });
}

function upisCeneD(d){
	$.ajax({
        url : "../rest/info/getMember",
        headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
        contentType:"application/json",
        type: "GET",
        success: function(data)
        {
        if(data.customer.type.type == "BRONZE"){
	        y = 700;
   		 }          
    	else if(data.customer.type.type == "SILVER"){
	        y = 630;
    	}
    	else {
       	 	y = 560;
   		 };
       		let res = (1-(d/100))*y;
       		let n = document.getElementById("dd");
       		n.innerHTML = 'Nova cena: '+res+'din';
        }
    });
}