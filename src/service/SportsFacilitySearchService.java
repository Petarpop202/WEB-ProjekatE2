package service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.SportsFacility;
import dao.SportsFacilityDAO;


@Path("/searchFacility")
public class SportsFacilitySearchService {
	
	
	@Context
	ServletContext kontekst;
	
	@PostConstruct
	public void init() {
	    	String putanja = kontekst.getRealPath("");
			kontekst.setAttribute("SportsFacilityDAO", new SportsFacilityDAO(putanja));

	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pretraziKorisnike(@QueryParam("name") String name, @QueryParam("type") String type,
													@QueryParam("location") String location, 
													@QueryParam("rate") String rate, @QueryParam("opt") String opt) throws ParseException, IOException{
		SportsFacilityDAO korisnikDAO = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		Collection<SportsFacility> korisnici = korisnikDAO.getSearchFacility(name, type, location, rate, opt);
		if (korisnici == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		return Response.ok(korisnici).build();
	}
	
	@GET
	@Path("/sort")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sortirajObjekte(@QueryParam("name") String name) throws ParseException, IOException{
		SportsFacilityDAO korisnikDAO = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		Collection<SportsFacility> facilities = korisnikDAO.GetAll();
		Collection<SportsFacility> korisnici = korisnikDAO.sortirajObjekte(name, facilities);
		if (korisnici == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		return Response.ok(korisnici).build();
	}
	
	@GET
	@Path("/filtered")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response filtrirajObjekte() throws ParseException, IOException{
		SportsFacilityDAO korisnikDAO = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		Collection<SportsFacility> facilities = korisnikDAO.GetAll();
		Collection<SportsFacility> korisnici = korisnikDAO.filtrirajObjekte(facilities);
		if (korisnici == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		return Response.ok(korisnici).build();
	}
	
}
