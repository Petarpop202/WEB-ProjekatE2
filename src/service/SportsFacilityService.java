package service;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import JWTController.JWTSession;
import beans.CheckedTraining;
import beans.Coach;
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
			ctx.setAttribute("CustomersDAO", new CustomersDAO(contextPath));
			if (ctx.getAttribute("JWTSession") == null) {
				ctx.setAttribute("JWTSession", new JWTSession());
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
	@Path("/available")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableManagers(@Context HttpServletRequest zahtev) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		Collection<Manager> managerss = dao.GetAllAvlManager();
				return Response.ok(managerss).build();
	}
	
	
	@GET
	@Path("/trainings")
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
	
	@POST
	@Path("/training")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajTrening(@QueryParam("name") String name, @QueryParam("type") String type, @QueryParam("facility") String facility, @QueryParam("duration") String duration, @QueryParam("trainer") String trainer, @QueryParam("description") String description, @QueryParam("picture") String picture) {
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		String putanja = ctx.getRealPath("");
		try {
			Training tr = ((SportsFacilityDAO) sportsFacilityDAO).dodajTrening(name,type,facility,duration,trainer,description,picture, putanja);
			if (tr == null) {
				return Response.status(400).entity("Trening sa datim imenom vec postoji!").build();
			}
			return Response.ok(tr).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri kreiranju treninga!").build();
		}
	}
	
	
	
	@GET
	@Path("/trainers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrainers(@Context HttpServletRequest zahtev) {
		CustomersDAO dao = (CustomersDAO) ctx.getAttribute("CustomersDAO");
		Collection<Coach> trainers = dao.getCoaches();
				return Response.ok(trainers).build();
	}
	
	
	@GET
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSportsFacilities(@Context HttpServletRequest zahtev) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		Collection<SportsFacility> facility = dao.GetAll();
				return Response.ok(facility).build();
	}
	

	
	@PUT
	@Path("/editTraining")
	@Produces(MediaType.APPLICATION_JSON)
	public Response izmenaPodataka(@Context HttpServletRequest zahtev, @QueryParam("trainingName") String trainingName, @QueryParam("duration") String duration, @QueryParam("description") String description, @QueryParam("trainer") String trainer, @QueryParam("type") String type) {
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
			String putanja = ctx.getRealPath("");
			try {
				Training t = sportsFacilityDAO.izmeniTrening(trainingName, duration, description, trainer, type, putanja);
				if (t == null) {
					return Response.status(400).entity("Greska pri izmeni podataka").build();
				}
				return Response.ok(t).build();
			} catch (Exception e) {
				return Response.status(400).entity("Greska pri izmeni podataka").build();
			}
	}
	

	@PUT
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response brisanjePodataka(@QueryParam("date") String date) {
		String putanja = ctx.getRealPath("");
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		try {
			CheckedTraining ct = dao.deleteChecked(date,putanja);
			if(ct != null)
				return Response.ok(ct).build();
			return Response.status(400).entity("Nije moguce otkazati trening !").build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).entity("Greska pri otkazivanju!").build();
				
	}
	
	@GET
	@Path("/getFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dobaviFacility(@Context HttpServletRequest request) {
		JWTSession jwtKontroler = (JWTSession) ctx.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) ctx.getAttribute("CustomersDAO");
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("SportsFacilityDAO");
		User ulogovani = jwtKontroler.proveriJWT(request, korisnikDAO);
		Manager manager = sportsFacilityDAO.getManager(ulogovani.getUsername());
		if (ulogovani == null) {
			return Response.status(401).entity("Sesija vam je istekla!").build();
		} else {
			SportsFacility sf = manager.getFacility();
			return Response.ok(sf).build();
		}
	}

}
