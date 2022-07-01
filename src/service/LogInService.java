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
import beans.Customer;
import beans.User;
import dao.CustomersDAO;


@Path("/login")
public class LogInService {

	@Context
	ServletContext kontekst;
	
	@PostConstruct
	public void init() {
		if (kontekst.getAttribute("CustomersDAO") == null) {
			kontekst.setAttribute("CustomersDAO", new CustomersDAO());
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
		if (korisnik == null) {
			return Response.status(401).entity("Uneli ste pogresno korisnicko ime!").build();
		}
		if (password.equals(korisnik.getPassword())) {
			jwtKontroler.kreiranjeJWT(korisnik);
			return Response.ok(korisnik).build();
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
