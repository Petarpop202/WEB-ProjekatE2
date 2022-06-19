package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Customer;
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
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		Customer korisnik = korisnikDAO.dobaviKorisnika(username);
		if (korisnik == null) {
			return Response.status(401).entity("Uneli ste pogresno korisnicko ime!").build();
		}
		if (password.equals(korisnik.getPassword())) {
			return Response.ok(korisnik).build();
		}
		return Response.status(401).entity("Uneli ste pogresnu lozinku!").build();
	}
}
