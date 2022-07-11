package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JWTController.JWTSession;
import beans.Coach;
import beans.Customer;
import beans.Manager;
import beans.Membership;
import beans.User;
import dao.CustomersDAO;


@Path("/registration")
public class CustomerService {


	@Context
	ServletContext kontekst;
	
	@PostConstruct
	public void init() {
		if (kontekst.getAttribute("CustomersDAO") == null) {
	    	String putanja = kontekst.getRealPath("");
			kontekst.setAttribute("CustomersDAO", new CustomersDAO(putanja));
		}
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajKorisnika(Customer korisnik) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		try {
			Customer k = korisnikDAO.dodajKorisnika(korisnik, putanja);
			if (k == null) {
				return Response.status(400).entity("Korisnik sa datim korisnickim imenom vec postoji!").build();
			}
			return Response.ok(k).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri registraciji!").build();
		}
	}
	
	
	@POST
	@Path("/coach")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCoach(Coach coach) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		try {
			Coach c = korisnikDAO.addCoach(coach, putanja);
			if (c == null) {
				return Response.status(400).entity("Trener sa datim korisnickim imenom vec postoji!").build();
			}
			return Response.ok(c).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri registraciji!").build();
		}
	}
	
	@POST
	@Path("/manager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addManager(Manager manager) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		try {
			Manager m = korisnikDAO.addManager(manager, putanja);
			if (m == null) {
				return Response.status(400).entity("Korisnik sa datim korisnickim imenom vec postoji!").build();
			}
			return Response.ok(m).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri registraciji!").build();
		}
	}
	
	
}
