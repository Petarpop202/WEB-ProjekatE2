/**
 * 
 */
 $(window).ready(function() {
    $("#forma").submit(function(event) {
        event.preventDefault();

        let username = $('input[name="username"]').val();
        let password = $('input[name="password"]').val();
        let name = $('input[name="name"]').val();
        let surname = $('input[name="surname"]').val();
        let gender = $('select[name="gender"]').find(":selected").val();
        let date = $('input#datum').val();

                
            $.post({
                url: "../rest/registration",
                contentType: "application/json",
                data: JSON.stringify({
                    Username: username,
                    Password: password,
                    Name: name,
                    Surname: surname,
                    Gender: gender,
                    Date: date,
                }),
                success: function(odgovor) {
					alert("Korisnik " + odgovor.Username + " je registrovan!")
					
					let urlLogin = "../rest/prijava?username=" + odgovor.korisnickoIme + "&password=" + odgovor.lozinka
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