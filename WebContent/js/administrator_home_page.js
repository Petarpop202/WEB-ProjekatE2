/**
 * 
 */
		$(document).ready(function () {
						$.ajax({
							url : "../rest/sports/",
							type: "GET",
							success: function(data)
							{
								let i = "";
								let color = "";
								for (let s of data) {
									if(s.status)
										color = "green";
									else
										color = "red";
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card border-success" style="width: 19rem;"><img src="../img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;'+s.typeStr+'</h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="sport_facility_review.html?name='+s.name+'" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
								}
								ispis(i);
							}
						});

						$.get({
							url: "../rest/info/getUser",	
							headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
							contentType:"application/json",
							dataType:"json",
							success: function(korisnik){
								setMain(korisnik);
							},
							error: function(){
								//location.assign("../html/login.html");
							}
						});
				})
		
		function setMain(korisnik){

			let n = document.getElementById("nameMain");
			n.innerHTML = '<h1 class="font-weight-bold text-dark"> '+ korisnik.name + ' ' + korisnik.surname +' </h1> <p class="subhead fs-4 mb-0 text-white-50">Hvala vam sto ste izabrali nas.</p>';
		}
		function ispis(i){
			let n = document.getElementById("Objekti");
			n.innerHTML = i;
		}

		$(document).ready(function(){
			$("#searchBtn").click(function(event){
				let name = $('input[name="searchBoxn"]').val();
				let location = $('input[name="searchBoxl"]').val();
				let type = document.getElementById("types").value;
				let rate = document.getElementById("rate").value;
				let opt = document.getElementById("options").value;
				let url;
				
					url = "../rest/searchFacility?name=" + name + "&type=" + type + "&location=" + location + "&rate=" + rate + "&opt=" + opt;

						$.get({
							url: url,
							contentType:"application/json",
							dataType:"json", 
							success:function(korisnici){
								let i = "";
								for (let s of korisnici) {
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card" style="width: 19rem;"><img src="../img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h7>'+s.typeStr+'</h7></h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="sport_facilitiy_review.html?name='+s.name+'" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
								}
								ispis(i);
							},
							error:function(){
								alert("Greska!");
							}
						});
				event.preventDefault();
			});
		})

		function sortiraj(){
			let opt = document.getElementById("options").value;

			$.get({
				url: "../rest/searchFacility/sort?name="+opt,
				contentType:"application/json",
				dataType:"json", 
				success:function(korisnici){
					let i = "";
					for (let s of korisnici) {
						i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card" style="width: 19rem;"><img src="../img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h7>'+s.typeStr+'</h7></h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="sport_facilitiy_review.html?name='+s.name+'" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
					}
					ispis(i);
				},
				error:function(){
					alert("Greska!");
				}
			});
		}

		function filtriraj(data){
			$.get({
				url: "../rest/searchFacility/filtered?name="+data,
				contentType:"application/json",
				dataType:"json", 
				success:function(korisnici){
					let i = "";
					for (let s of korisnici) {
						i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card" style="width: 19rem;"><img src="../img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h7>'+s.typeStr+'</h7></h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="sport_facilitiy_review.html?name='+s.name+'" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
					}
					ispis(i);
				},
				error:function(){
					alert("Greska!");
				}
			});
		}
		
		function logout(){
			$(document).ready(function() {
					$.get({
						url: "../rest/login/logout",
						headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
						contentType:"application/json",
						dataType:"json",
						success: function(korisnik){
							sessionStorage.removeItem("jwt");
							location.assign("../index.html");
						},
						error: function(){
							alert("Greska");
						}
					})
			});

		}

		function profile(){
			location.assign("profile_page.html");
		}

		function createSportFacility(){
			location.assign("create_sport_facility_page.html");
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
						url: "../rest/registration/coach",
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

		$(window).ready(function() {
			$("#create").click(function (event) {
				event.preventDefault();
				createCode();
				
			});
		})

		function createCode(){
			let code = $('input[name="code"]').val();
			let uses = $('input[name="uses"]').val();
			let discount = $('input[name="discount"]').val();
			let expire = $('input#expire').val();

			$.post({
				url: "../rest/info/addCode?code="+code+"&uses="+uses+"&expiration="+expire+"&discount="+discount,
				contentType: "application/json",
				success: function(odgovor) {
					alert("Kod je uspesno kreiran!")		
				},
				error: function(odgovor) {
				}
			});
		}
		
		
		