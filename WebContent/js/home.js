/**
 * 
 */
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

		$(window).ready(function() {
			$("#register").click(function (event) {
				event.preventDefault();
		
				let username = $('input[name="username"]').val();
				let password = $('input[name="password"]').val();
				let name = $('input[name="name"]').val();
				let surname = $('input[name="surname"]').val();
				let gender = $('select[name="gender"]').find(":selected").val();
				let date = $('input#datum').val();
		
						
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
								alert("Korisnik sa zadatim korisnickim imenom vec postoji!");
							}else {
								alert("Greska pri registraciji!");
							}
						}
					});
				
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
							//sessionStorage.setItem("jwt", odgovor.jwt);
							alert("Uspesna prijave");
						},
						error: function(odgovor) {
							alert("Greska prijave");
						}
					});			
					
				
		
			});
		
		});