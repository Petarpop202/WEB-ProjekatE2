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
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card border-success" style="width: 19rem;"><img src="../img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;'+s.typeStr+'</h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="#" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
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
				let name = $('input[name="searchBox"]').val();
				let location = $('input[name="searchBox"]').val();
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
									i = i + '<div class="col-sm mt-5 d-flex justify-content-center"><div class="card" style="width: 19rem;"><img src="../img/'+ s.picture +'.jpg" class="card-img-top" alt="..."><div class="card-body p-4 rounded-bottom" ><h5 class="card-title font-weight-bold">'+ s.name +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h7>'+s.typeStr+'</h7></h5><p class="card-text">'+ s.location.address +'</p><p class="card-text">'+ s.statusStr  + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + s.workTime +'</p><div class="float-start"><a href="#" class="btn btn-primary">Pregledaj</a><p style="display:inline" class="card-text text-center-end">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ocena:&nbsp;' + s.rate +'</p></div></div></div></div>';
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
		
		