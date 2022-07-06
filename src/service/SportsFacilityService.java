package service;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import beans.Manager;
import beans.SportsFacility;
import beans.Training;
import beans.User;
import dao.CustomersDAO;
import dao.SportsFacilityDAO;

@Path("/sports")
public class SportsFacilityService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("SportsFacilityDAO", new SportsFacilityDAO(contextPath));
		}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFacilities(@Context HttpServletRequest zahtev) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
				return Response.ok(dao.GetAll()).build();
	}
	
	@GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFacility(@Context HttpServletRequest zahtev,  @QueryParam("name") String name) {
        SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
        SportsFacility sports = dao.getFacility(name);
                return Response.ok(sports).build();
    }
	
	@GET
	@Path("/available")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableManagers(@Context HttpServletRequest zahtev) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		Collection<Manager> managerss = dao.GetAllAvlManager();
				return Response.ok(managerss).build();
	}
	
	@GET
	@Path("/managers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrainings(@Context HttpServletRequest zahtev, @QueryParam("name") String name) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		Collection<Training> trainings = dao.getFacilityTrainings(name);
				return Response.ok(trainings).build();
	}
	
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajObjekat(@QueryParam("name") String name, @QueryParam("type") String type, @QueryParam("address") String address, @QueryParam("width") String width, @QueryParam("length") String length, @QueryParam("manager") String manager, @QueryParam("picture") String picture) {
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		String putanja = ctx.getRealPath("");
		try {
			SportsFacility sf = sportsFacilityDAO.dodajObjekat(name,type,address,width,length,manager,picture, putanja);
			if (sf == null) {
				return Response.status(400).entity("Korisnik sa datim korisnickim imenom vec postoji!").build();
			}
			return Response.ok(sf).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri kreiranju objekta!").build();
		}
	}
	
	
}
