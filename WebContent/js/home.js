/**
 * 
 */
		$(document).ready(function () {
					$("#dugme").click(function (event){
						event.preventDefault();
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
					});
				})
		
		function ispis(i){
			let n = document.getElementById("Objekti");
			n.innerHTML = i;
		}
