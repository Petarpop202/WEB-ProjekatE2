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
								for (let s of data) {
									ispis(s);
								}
							}
						});
					});
				})
		
		function ispis(korisnik){
			let tr = $('<tr></tr>');
			let ime = $('<td>' + korisnik.firstName + '</td>');
		
			tr.append(ime)
		
			$('#korisnici tbody').append(tr);
		}