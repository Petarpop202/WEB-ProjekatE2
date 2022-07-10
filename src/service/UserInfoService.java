package service;

import java.io.IOException;
import java.text.ParseException;
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
import beans.Commentar;
import beans.Customer;
import beans.Manager;
import beans.Membership;
import beans.PromoCode;
import beans.SportsFacility;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import dao.CustomersDAO;
import dao.SportsFacilityDAO;

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
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@Context HttpServletRequest zahtev) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
				return Response.ok(korisnikDAO.getAllUsers()).build();
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
	
	@POST
	@Path("/setMember")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajMembership(Membership member) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		try {
			Membership k = korisnikDAO.dodajMembership(member, putanja);
			if (k == null) {
				return Response.status(400).entity("Greska prilikom kupovine!").build();
			}
			return Response.ok(k).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri kupovini!").build();
		}
	}
	
	@POST
	@Path("/doTraining")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response prijaviTrening(@Context HttpServletRequest zahtev, @QueryParam("name") String name , @QueryParam("date") String date) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		SportsFacilityDAO dao = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		String putanja = kontekst.getRealPath("");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		if(ulogovani == null) {
			return Response.status(400).entity("Niste registrovani!").build();
		}
		Customer c = korisnikDAO.dobaviKorisnika(ulogovani.getUsername());
		try {
			Training tr = dao.getTraining(name);
			Boolean th = dao.checkTraining(c,tr,date,putanja);
			if (th == null) {
				return Response.status(400).entity("Greska prilikom kupovine!").build();
			}
			return Response.ok(th).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri kupovini!").build();
		}
	}
	
	
	@GET
	@Path("/managerFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManagersFacility(@Context HttpServletRequest zahtev) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		Manager m = korisnikDAO.getManager(ulogovani.getUsername());
		SportsFacility sf = korisnikDAO.getManagerFacility(m,putanja );
				return Response.ok(sf).build();
	}
	
	@GET
	@Path("/FacilityTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFacilityTrainings(@Context HttpServletRequest zahtev) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		SportsFacilityDAO sfD = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		String putanja = kontekst.getRealPath("");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		Manager m = korisnikDAO.getManager(ulogovani.getUsername());
		SportsFacility sf = korisnikDAO.getManagerFacility(m,putanja );
		Collection<Training> trainings = sfD.getFacilityTrainings(sf.getName());
				return Response.ok(trainings).build();
	}
	
	@GET
	@Path("/userTrainingHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserTrainingHistory(@Context HttpServletRequest zahtev) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		SportsFacilityDAO sfD = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		String putanja = kontekst.getRealPath("");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		Collection<TrainingHistory> trainings = sfD.getTrainingHistoriesOfCustomer(ulogovani.getUsername(), putanja);
				return Response.ok(trainings).build();
	}
	
	@GET
	@Path("/userCheckedTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserCheckedTrainings(@Context HttpServletRequest zahtev) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		SportsFacilityDAO sfD = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		String putanja = kontekst.getRealPath("");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		Collection<CheckedTraining> trainings = sfD.getCheckedTrainingsOfCustomer(ulogovani.getUsername(),putanja);
				return Response.ok(trainings).build();
	}
	
	@GET
	@Path("/coachCheckedTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoachCheckedTrainings(@Context HttpServletRequest zahtev) {
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		SportsFacilityDAO sfD = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		String putanja = kontekst.getRealPath("");
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		Collection<CheckedTraining> trainings = sfD.getCheckedTrainingsOfCoach(ulogovani.getUsername(),putanja);
				return Response.ok(trainings).build();
	}
	
	@POST
	@Path("/addComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComent(@Context HttpServletRequest zahtev, @QueryParam("text") String text, @QueryParam("rate")String rate, @QueryParam("name") String name) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		SportsFacilityDAO sfD = (SportsFacilityDAO) kontekst.getAttribute("SportsFacilityDAO");
		JWTSession jwtKontroler = (JWTSession) kontekst.getAttribute("JWTSession");
		String putanja = kontekst.getRealPath("");
		SportsFacility sf = sfD.getFacility(name);
		User ulogovani = jwtKontroler.proveriJWT(zahtev, korisnikDAO);
		Customer cust = korisnikDAO.dobaviKorisnika(ulogovani.getUsername());
		try {
			Commentar c = korisnikDAO.addComment(cust,text,rate,sf,putanja);
			if (c == null) {
				return Response.status(400).entity("Greska prilikom kupovine!").build();
			}
			return Response.ok(c).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri kupovini!").build();
		}
	}
	
	@PUT
	@Path("/acceptComment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response odobravanjeKomentara(@QueryParam("text") String text) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		Commentar c = korisnikDAO.acceptComment(putanja,text);
			if(c != null)
				return Response.ok().build();
		return Response.status(401).entity("Greska prilikom odobravanja!").build();
}

	@GET
	@Path("/searchUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pretraziKorisnike(@QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("username") String username, @QueryParam("opt") String opt) throws ParseException, IOException{
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		Collection<User> korisnici = korisnikDAO.getSearchUsers(name, surname, username, opt);
		if (korisnici == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		return Response.ok(korisnici).build();
	}
	
	@GET
	@Path("/sortUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sortirajKorisnike(@QueryParam("opt") String opt, @QueryParam("sortOptions") String sortOptions) throws ParseException, IOException{
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		Collection<User> users = korisnikDAO.getAllUsers();
		Collection<User> korisnici = korisnikDAO.getSortUsers(users, opt, sortOptions);
		if (korisnici == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		return Response.ok(korisnici).build();
	}
	
	@POST
	@Path("/addCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCode(@Context HttpServletRequest zahtev, @QueryParam("code") String code, @QueryParam("uses")String uses, @QueryParam("expiration") String expiration, @QueryParam("discount") String discount) {
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		try {
			PromoCode pc = korisnikDAO.addCode(code,uses,expiration,discount,putanja);
			if (pc == null) {
				return Response.status(400).entity("Greska prilikom kupovine!").build();
			}
			return Response.ok(pc).build();
		} catch (Exception e) {
			return Response.status(500).entity("Greska pri kupovini!").build();
		}
	}
	
	@GET
	@Path("/checkCode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response proveriKod(@QueryParam("code") String code) throws ParseException, IOException{
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		String putanja = kontekst.getRealPath("");
		PromoCode pc = korisnikDAO.checkCode(code,putanja);
		if (pc == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		return Response.ok(pc).build();
	}

	@GET
	@Path("/filterUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response filtrirajKorisnike(@QueryParam("name") String name) throws ParseException, IOException{
		CustomersDAO korisnikDAO = (CustomersDAO) kontekst.getAttribute("CustomersDAO");
		Collection<User> users = korisnikDAO.getAllUsers();
		Collection<Customer> customers = korisnikDAO.findAllCustomers();
		Collection<Customer> kupci = korisnikDAO.filtrirajKupce(name, customers);
		Collection<User> korisnici = korisnikDAO.filtrirajKorisnike(name, users);
		if (korisnici == null) {
			return Response.status(400).entity("Greska pri pretrazi korisnika!").build();
		}
		if(name.equals("Zlatni") || name.equals("Srebrni") || name.equals("Bronzani"))
			return Response.ok(kupci).build();
		else return Response.ok(korisnici).build();
	}
	
}
