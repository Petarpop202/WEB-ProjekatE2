package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JWTController.JWTSession;
import beans.Customer;
import beans.Membership;
import beans.User;
import dao.CustomersDAO;

@Path("/info")
public class UserInfoService {

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
	
	@GET
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dobaviKorisnika(@Context HttpServletRequest request) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		User ulogovani = jwtKontroler.proveriJWT(request, korisnikDAO);
		if (ulogovani == null) {
			return Response.status(401).entity("Sesija vam je istekla!").build();
		} else {
			return Response.ok(ulogovani).build();
		}
	}
	
	@GET
	@Path("/getMember")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dobaviMembership(@Context HttpServletRequest request) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		User ulogovani = jwtKontroler.proveriJWT(request, korisnikDAO);
		if (ulogovani == null) {
			return Response.status(401).entity("Sesija vam je istekla!").build();
		} else {
			Membership m = korisnikDAO.getMembership(ulogovani);
			return Response.ok(m).build();
		}
	}
}
