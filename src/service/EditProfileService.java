package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JWTController.JWTSession;
import beans.User;
import dao.CustomersDAO;

@Path("/edit")
public class EditProfileService {
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
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response izmenaPodataka(@Context HttpServletRequest zahtev, User korisnik) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		if (ulogovani.getUsername().equals(korisnik.getUsername())) {
			String putanja = kontekst.getRealPath("");
			try {
				User k = korisnikDAO.izmenaPodataka(korisnik, putanja);
				if (k == null) {
					return Response.status(400).entity("Greska pri izmeni podataka").build();
				}
				return Response.ok(k).build();
			} catch (Exception e) {
				return Response.status(400).entity("Greska pri izmeni podataka").build();
			}
		}
		return Response.status(401).entity("Sesija vam je istekla").build();
	}
	
	@PUT
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response brisanjePodataka(@QueryParam("username") String username) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		User u = korisnikDAO.deleteUser(username,putanja);
			if(u != null)
				return Response.ok().build();
		return Response.status(401).entity("Sesija vam je istekla").build();
	}
}
