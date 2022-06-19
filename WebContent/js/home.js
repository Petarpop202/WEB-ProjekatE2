/**
 * 
 */
	function inputChange(){
		let option = document.getElementById("options");
		if(option.value == "Naziv"){
			document.getElementById("types").setAttribute("hidden", "true");
			document.getElementById("serachh").style.display="none";
			
			document.getElementById("rate").setAttribute("hidden", "true");
		} else if(option.value == "Tip"){
			document.getElementById("types").removeAttribute("hidden");
			document.getElementById("serachh").style.display="block";
			
			document.getElementById("rate").setAttribute("hidden", "true");
		}


	}

		$(document).ready(function () {
						$.ajax({
							url : "rest/sports/",
							type: "GET",
							success: function(data)
							{
								let i = "";
								for (let s of data) {
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card" style="width: 18rem;"><img src="img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'</h5><p class="card-text">'+ s.location.address +'</p><a href="#" class="btn btn-primary">Pregledaj</a></div></div></div>';
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
							
							//let urlLogin = "../rest/prijava?username=" + odgovor.korisnickoIme + "&password=" + odgovor.lozinka
							/*
							$.post({
								   url: urlLogin,
								contentType: "application/json",
								success: function(odgovor) {
									sessionStorage.setItem("jwt", odgovor.jwt);
									window.location.assign("http://localhost:8088/wp-projekat/html/sopstveni_profil.html");
								},
								error: function(odgovor) {
										alert(odgovor.responseText);
								}
							});
							*/
		
							
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
				/*	
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
							
							//let urlLogin = "../rest/prijava?username=" + odgovor.korisnickoIme + "&password=" + odgovor.lozinka
							/*
							$.post({
								   url: urlLogin,
								contentType: "application/json",
								success: function(odgovor) {
									sessionStorage.setItem("jwt", odgovor.jwt);
									window.location.assign("http://localhost:8088/wp-projekat/html/sopstveni_profil.html");
								},
								error: function(odgovor) {
										alert(odgovor.responseText);
								}
							});
							
		
							
						},
						error: function(odgovor) {
							if (odgovor.status == 400) {
								alert("Korisnik sa zadatim korisnickim imenom vec postoji!");
							}else {
								alert("Greska pri registraciji!");
							}
						}
					});
					*/
				
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
							alert("Uspesna prijava");
						},
						error: function(odgovor) {
							document.getElementById("er").removeAttribute("hidden");
							return;
						}
					});			
					
				
		
			});
		
		});