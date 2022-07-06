/**
 * 
 */
	function inputChange(){
		let option = document.getElementById("options");
		let search = document.getElementById("searchh");
		if(option.value == "Naziv"){
			document.getElementById("types").setAttribute("hidden", "true");
			search.type = "text";
			document.getElementById("rate").setAttribute("hidden", "true");
		} else if(option.value == "Tip"){
			document.getElementById("types").removeAttribute("hidden");
			search.type = "hidden";
			document.getElementById("rate").setAttribute("hidden", "true");
		} else if(option.value == "Lokacija"){
			document.getElementById("types").setAttribute("hidden","true");
			search.type = "text";
			document.getElementById("rate").setAttribute("hidden", "true");
		} else if(option.value == "Ocena"){
			document.getElementById("types").setAttribute("hidden","true");
			search.type = "hidden";
			document.getElementById("rate").removeAttribute("hidden");
		}

	}

		$(document).ready(function () {
						$.ajax({
							url : "rest/sports/",
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
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card border-success" style="width: 19rem;"><img src="img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;'+s.typeStr+'</h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="html/sport_facility_review.html?name='+s.name+'" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
								}
								ispis(i);
							}
						});
				})
		
		function ispis(i){
			let n = document.getElementById("Objekti");
			n.innerHTML = i;
		}

		function validateCheck(document){
			let username = $('input[name="username"]').val();
				let password = $('input[name="password"]').val();
				let name = $('input[name="name"]').val();
				let surname = $('input[name="surname"]').val();
				let gender = $('select[name="gender"]').find(":selected").val();
				let date = $('input#datum').val();
				let validate = false;

				var regName = new RegExp('([A-Z]{1})([a-z]+)$');
				var regUser = new RegExp('[a-zA-Z0-9]+$');
				if(!regName.test(name)){
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
						url: "rest/registration",
						contentType: "application/json",
						data: JSON.stringify({
							username: username,
							password: password,
							name: name,
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

		$(document).ready(function() {
			$("#loginBtn").click(function (event) {
				event.preventDefault();
		
				let username = $('input[name="usernamel"]').val();
				let password = $('input[name="passwordl"]').val();
					
					$.post({
						url: "rest/login?username=" + username + "&password=" + password,
						contentType: "application/json",
						success: function(odgovor) {
							sessionStorage.setItem("jwt", odgovor.jwt);
							alert("Uspesna prijava");
							if(odgovor.role == "CUSTOMER")
							window.location.assign("http://localhost:8080/FitnessCentar/html/customer_home_page.html");
							else 
							window.location.assign("http://localhost:8080/FitnessCentar/html/administrator_home_page.html");
						},
						error: function(odgovor) {
							document.getElementById("er").removeAttribute("hidden");
							return;
						}
					});			
					
				
		
			});
		
		});

		$(document).ready(function(){
			$("#searchBtn").click(function(event){
				let name = $('input[name="searchBox"]').val();
				let location = $('input[name="searchBox"]').val();
				let type = document.getElementById("types").value;
				let rate = document.getElementById("rate").value;
				let opt = document.getElementById("options").value;
				let url;
				
					url = "rest/searchFacility?name=" + name + "&type=" + type + "&location=" + location + "&rate=" + rate + "&opt=" + opt;

						$.get({
							url: url,
							contentType:"application/json",
							dataType:"json", 
							success:function(korisnici){
								let i = "";
								for (let s of korisnici) {
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card" style="width: 19rem;"><img src="img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h7>'+s.typeStr+'</h7></h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="html/sport_facilitiy_review.html?name='+s.name+'" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
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

