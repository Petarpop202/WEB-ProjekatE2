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
								n.innerHTML = '<div class="card mb-4"><div class="card-body text-center"><img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"class="rounded-circle img-fluid" style="width: 150px;"><h5 class="my-3">'+data.name+' '+data.surname+'</h5><p class="text-muted mb-1">'+ data.role +'</p><div class="d-flex justify-content-center mb-2"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalRegisterForm">Izmeni nalog</button></div></div></div>';
								setName(data);
								setSurname(data);
								setGender(data);
								setDate(data);
								setUsername(data);
								setRole(data); 
								loadEditPage(data);
								loadMembership(data);
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
			}else if(data.role == "COACH"){
				s = "Trener";
			} else if(data.role == "MANAGER"){
				s = "Menadžer";
			} else s = "Admin";
			n.innerHTML = '<div class="col-sm-3"><p class="mb-0">Nalog</p></div><div class="col-sm-9"><p class="text-muted mb-0">'+s+'</p></div><div class="row gutters-sm" id="mem"></div>';
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

		function loadEditPage(data){
			let n = document.getElementById("modalRegisterForm");
			n.innerHTML = '<div class="modal-dialog" role="document"><div class="modal-content bg-dark text-white"><div class="modal-header text-center"><h4 class="modal-title w-100 font-weight-bold text-primary">Izmena naloga</h4><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body mx-3"><div class="md-form mb-5 text-primary"><i class="fas fa-user prefix grey-text"></i><label data-error="wrong" data-success="right" for="name">Ime</label><input type="text" value="'+data.name+'" id="name" name="name" class="form-control validate"></div><div class="md-form mb-5 text-primary"><i class="fas fa-envelope prefix grey-text"></i><label data-error="wrong" data-success="right" for="surname">Prezime</label><input type="text" id="surname" value="'+data.surname+'" name="surname" class="form-control validate"></div><div class="md-form mb-5 text-primary"><i class="fas fa-envelope prefix grey-text"></i><label data-error="wrong" data-success="right" for="date">Datum rodjenja</label><input type="text" value="'+data.date+'" class="datepickeri form-control" id="datum" name="date"min="1900-01-01" max="2003-12-31" onfocus="(this.type="date")"></div><div class="md-form mb-5 text-primary"><i class="fas fa-envelope prefix grey-text"></i><div><label data-error="wrong" data-success="right" for="username">Pol</label></div><select class="form-select" name="gender"><option value=True selected>Muški</option><option value="False">Ženski</option></select></div><div class="md-form mb-5 text-primary"><i class="fas fa-envelope prefix grey-text"></i><label data-error="wrong" data-success="right" for="username">Korisnicko ime</label><input type="text" value="'+data.username+'" name="username" class="form-control validate" disabled></div><div class="md-form mb-4 text-primary"><i class="fas fa-lock prefix grey-text"></i><label data-error="wrong" data-success="right" for="password">Lozinka</label><input type="password" id="pass" value="'+data.password+'" name="password" class="form-control validate"></div></div><div class="modal-footer d-flex justify-content-center"><button type="submit" id="edit" name="edit" onclick="EditStart()" class="btn btn-deep-orange text-white">Izmeni</button></div></div>';
		}

		function EditStart(){
				let username = $('input[name="username"]').val();
				let password = $('input[name="password"]').val();
				let name = $('input[name="name"]').val();
				let surname = $('input[name="surname"]').val();
				let gender = $('select[name="gender"]').find(":selected").val();
				let date = $('input#datum').val();


				$.ajax({
					type: "PUT",
					url: "../rest/edit",
					headers: {
						'Authorization':'Bearer ' + sessionStorage.getItem('jwt')
					},
					contentType:"application/json",
					dataType:"json",
					data: JSON.stringify({
						username: username,
						name: name,
						surname: surname,
						gender: gender,
						date: date,
						password: password,
					}),
					success: function(odgovor) {
						alert("Uspesno izmenjeni licni podaci!")
						window.location.assign("http://localhost:8080/FitnessCentar/html/profile_page.html");
					},
					error: function(odgovor) {
						alert(odgovor.responseText);
						if(odgovor.status == 401) {
							sessionStorage.removeItem("jwt");
							history.back();
						}
					}
				});
				
		}

		function loadMembership(kor){
			if(kor.role == "CUSTOMER"){
				$.ajax({
					url : "../rest/info/getMember",
					headers:{'Authorization':'Bearer ' + sessionStorage.getItem('jwt')},
					contentType:"application/json",
					type: "GET",
					success: function(data)
					{
						let n = document.getElementById("mem");	
						n.innerHTML = '<div class="col-sm-6 mb-3"><div class="card h-100"><div class="card-body"><h6 class="d-flex align-items-center mb-3"><i class="material-icons text-info mr-2">status</i>CLANARINA</h6><small>Vazi do : '+data.memberDate+'</small><div class="progress mb-3" style="height: 5px"><div class="progress-bar bg-primary" role="progressbar" style="width: 80%" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div></div><button onclick="openMember()">Pregledaj</button></div></div></div>';
					}
				});
			}
		}
		
		function openMember(){
			window.location.assign("http://localhost:8080/FitnessCentar/html/membership_page.html");
		}


