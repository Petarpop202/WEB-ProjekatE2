/**
 * 
 */
		$(document).ready(function () {
					$.ajax({
						url : "rest/sports/",
						type: "GET",
						success: function(data, textStatus, jqXHR)
						{
							for (let s of data) {
								ispis(s);
							}
						},
						error: function (jqXHR, textStatus, errorThrown)
						{

						}
					});
				})
		
		function ispis(korisnik){
			let tr = $('<tr></tr>');
			let ime = $('<td>' + korisnik.firstName + '</td>');
		
			tr.append(ime)
		
			$('#korisnici tbody').append(tr);
		}