/**
 * 
 */
		$(document).ready(function () {
						$.ajax({
							url : "../rest/info/getUser",
							headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
							contentType:"application/json",
							type: "GET",
							success: function(data)
							{
								let n = document.getElementById("left");	
								n.innerHTML = '<div class="card mb-4"><div class="card-body text-center"><img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"class="rounded-circle img-fluid" style="width: 150px;"><h5 class="my-3">'+data.name+' '+data.surname+'</h5><p class="text-muted mb-1">Kupac</p><div class="d-flex justify-content-center mb-2"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalRegisterForm">Izmeni nalog</button></div></div></div>';
								setName(data);
								setSurname(data);
								setGender(data);
								setDate(data);
								setUsername(data);
								setRole(data); 
							}
						});
				})
		
		function setName(data){
			let n = document.getElementById("name");
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Ime</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+data.name+'</p></div>';
		}

		function setSurname(data){
			let n = document.getElementById("surname");
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Prezime</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+data.surname+'</p></div>';
		}

		function setGender(data){
			let n = document.getElementById("gender");
			let s = "";
			if(data.gender){
				s = "Musko";
			}
			else s = "Zensko";
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Pol</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+s+'</p></div>';
		}

		function setDate(data){
			let n = document.getElementById("date");
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Datum rodjenja</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+data.date+'</p></div>';
		}

		function setUsername(data){
			let n = document.getElementById("username");
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Korisnicko ime</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+data.username+'</p></div>';
		}

		function setRole(data){
			let n = document.getElementById("role");
			let s = "";
			if(data.role == "CUSTOMER"){
				s = "Kupac";
			}
			else s = "Admin";
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Nalog</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+s+'</p></div>';
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


