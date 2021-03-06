package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JWTController.JWTSession;
import beans.Coach;
import beans.Customer;
import beans.Manager;
import beans.User;
import dao.CustomersDAO;


@Path("/login")
public class LogInService {

	@Context
	ServletContext kontekst;
	
	@PostConstruct
	public void init() {
		if (kontekst.getAttribute("CustomersDAO") == null) {
			String putanja = kontekst.getRealPath("");
			kontekst.setAttribute("CustomersDAO", new CustomersDAO(putanja));
		}
		if (kontekst.getAttribute("JWTSession") == null) {
			kontekst.setAttribute("JWTSession", new JWTSession());
		}
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		Customer korisnik = korisnikDAO.dobaviKorisnika(username);
		User admin = korisnikDAO.adminProfile(username);
		Manager manager = korisnikDAO.getManager(username);
		User trainer = korisnikDAO.getCoach(username);
		if (korisnik == null && admin == null && manager == null && trainer == null) {
			return Response.status(401).entity("Uneli ste pogresno korisnicko ime!").build();
		}
		if (korisnik != null)
			if(korisnik.getDeleted())
				Response.status(401).entity("Nalog je izbrisan").build();
			else if(password.equals(korisnik.getPassword())){
				jwtKontroler.kreiranjeJWT(korisnik);
				return Response.ok(korisnik).build();
		}
		if (admin != null) 
			if(password.equals(admin.getPassword())){
		 		jwtKontroler.kreiranjeJWT(admin);
		 		return Response.ok(admin).build();
		}
		if (manager != null)
			if(manager.getDeleted())
				Response.status(401).entity("Nalog je izbrisan").build();
			else if(password.equals(manager.getPassword())){
		 		jwtKontroler.kreiranjeJWT(manager);
		 		return Response.ok(manager).build();
		}
		if (trainer != null)
			if(trainer.getDeleted())
				Response.status(401).entity("Nalog je izbrisan").build();
			else if(password.equals(trainer.getPassword())){
		 		jwtKontroler.kreiranjeJWT(trainer);
		 		return Response.ok(trainer).build();
		}
		
		return Response.status(401).entity("Uneli ste pogresnu lozinku!").build();
	}
	
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response odjava(@Context HttpServletRequest zahtev) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		User korisnik = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		if (korisnik != null) {
			korisnik.setJwt("");
			return Response.ok(korisnik).build();
		}
		return Response.status(400).entity("Niste ulogovani").build();
	}
	

}
