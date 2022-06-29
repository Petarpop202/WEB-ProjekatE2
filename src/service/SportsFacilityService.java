package service;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.SportsFacility;
import beans.Training;
import dao.SportsFacilityDAO;

@Path("/sports")
public class SportsFacilityService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		System.out.println("USO");
		if (ctx.getAttribute("SportsFacilityDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("SportsFacilityDAO", new SportsFacilityDAO(contextPath));
		}
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
	@Path("/trainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrainings(@Context HttpServletRequest zahtev, @QueryParam("name") String name) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		Collection<Training> trainings = dao.getFacilityTrainings(name);
				return Response.ok(trainings).build();
	}
}
