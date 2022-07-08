package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import beans.Coach;
import beans.Customer;
import beans.CustomerType;
import beans.Manager;
import beans.Membership;
import beans.Membership.TypeEnum;
import beans.SportsFacility;
import beans.User;
import beans.User.RoleEnum;
public class CustomersDAO {
	
	private HashMap<String, Customer> korisnici;
	private HashMap<String, Membership> memberships;
	private HashMap<String, Manager> managers; 
	private HashMap<String, Coach> trainers;
	private User admininstrator;
	
	private String[] putanje = {
	 "D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Users.csv",
	 "D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Memberships.csv",
	 "D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Managers.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Users.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Memberships.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Managers.csv"};

	
	public CustomersDAO(String path){
		admininstrator = new User();
		korisnici = new HashMap<String, Customer>();
		memberships = new HashMap<String, Membership>();
		managers = new HashMap<String, Manager>();
		trainers = new HashMap<String, Coach>();
		getAdmin(path);
		getAllCustomers(putanje[4]);
		getAllManagers(path);
		getAllTrainers(path);
		String put1 = path + "\\data\\Memberships.csv";
		getAllMemberships(put1);
		checkMemberships(path);
	}
	





	public Customer dodajKorisnika(Customer korisnik, String putanja) throws IOException {
		String put1 = putanja + "\\data\\Customers.csv";
		String put2 = putanja + "\\data\\Users.csv";
		if (korisnici.containsKey(korisnik.getUsername())) {
			return null;
		}
		korisnik.setRole(RoleEnum.CUSTOMER);
		korisnik.setDeleted(false);
		korisnici.put(korisnik.getUsername(), korisnik);
		upisKorisnikaUFajl(put1, korisnik);
		upisKorisnikaUFajl(putanje[4], korisnik);
		upisUUsers(put2, korisnik);
		upisUUsers(putanje[5], korisnik);
		return korisnik;
	}
	
	private void getAllMemberships(String putanja) {
		BufferedReader citac = null;
		try {
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Membership.TypeEnum Type = getType(parametri[0]);
				String PayDate = parametri[1];
				String MemberDate = parametri[2];
				Double Price = Double.parseDouble(parametri[3]);
				Customer Customer = dobaviKorisnika(parametri[4]);
				Boolean Status = Boolean.parseBoolean(parametri[5]);
				Integer termins = Integer.parseInt(parametri[6]);

				Membership m = new Membership(Type,PayDate,MemberDate,Price,Customer,Status,termins);
				memberships.put(Customer.getUsername(), m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if ( citac != null ) {
				try {
					citac.close();
				}
				catch (Exception e) { }
			}
			
		}
	}
	
	private CustomerType getCustomerType(String t) {
		if(t == "BRONZE") {
			return new CustomerType(CustomerType.TypeEnum.BRONZE,0.0,50.0);
		}
		else if(t == "SILVER") {
			return new CustomerType(CustomerType.TypeEnum.SILVER,15.0,100.0);
		}
		else return new CustomerType(CustomerType.TypeEnum.GOLD,30.0,10000.0);
	}
	
	private Membership.TypeEnum getType(String status) {
		if (status.equals("YEAR")) {
			return Membership.TypeEnum.YEAR;
		}else if (status.equals("MONTH")) {
			return Membership.TypeEnum.MONTH;
		}else if (status.equals("DAY")) {
			return Membership.TypeEnum.DAY;
		}
		return null;
	}
	
	private void getAllCustomers(String putanja) {
		BufferedReader citac = null;
		try {
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Boolean pol = Boolean.parseBoolean(parametri[4]);
				Integer points = Integer.parseInt(parametri[7]);
				if(points >= 100)
					parametri[8] = "GOLD";
				else if (points >= 50 && points < 100)
					parametri[8] = "SILVER";
				else parametri[8] = "BRONZE";
				CustomerType cType = getCustomerType(parametri[8]);
				Customer korisnik = new Customer(parametri[2], parametri[3], parametri[0], 
						parametri[1], pol, parametri[5],points,cType,Boolean.parseBoolean(parametri[9]));
				korisnici.put(parametri[2], korisnik);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if ( citac != null ) {
				try {
					citac.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	private void getAdmin(String putanja) {
		BufferedReader citac = null;
		try {
			putanja += "\\data\\Users.csv";
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Boolean pol = Boolean.parseBoolean(parametri[4]);
				User korisnik = new User(parametri[2], parametri[3], parametri[0], 
						parametri[1], pol, parametri[5],getStringToRole(parametri[6]),false);
				if(getStringToRole(parametri[6]) ==  User.RoleEnum.ADMIN) {
					admininstrator = korisnik;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if ( citac != null ) {
				try {
					citac.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	private void getAllTrainers(String putanja) {
		BufferedReader citac = null;
		try {
			putanja += "\\data\\Users.csv";
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Boolean pol = Boolean.parseBoolean(parametri[4]);
				Coach korisnik = new Coach(parametri[2], parametri[3], parametri[0], 
						parametri[1], pol, parametri[5],getStringToRole(parametri[6]),false);
				if(getStringToRole(parametri[6]) ==  User.RoleEnum.ADMIN) {
					continue;
				}
				else if(getStringToRole(parametri[6]) ==  User.RoleEnum.COACH)
					trainers.put(parametri[2],korisnik);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if ( citac != null ) {
				try {
					citac.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	private void upisKorisnikaUFajl(String putanja, Customer korisnik) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(korisnik.getName());
		upis.append(",");
		upis.append(korisnik.getSurname());
		upis.append(",");
		upis.append(korisnik.getUsername());
		upis.append(",");
		upis.append(korisnik.getPassword());
		upis.append(",");
		upis.append(korisnik.getGender().toString());
		upis.append(",");
		upis.append(korisnik.getDate());
		upis.append(",");
		upis.append(getRoleTypeToString(korisnik.getRole()));
		upis.append(",");
		upis.append(NullToString(korisnik.getPoints()));
		upis.append(",");
		upis.append(getCustomerTypeToString(korisnik.getType()));
		upis.append(",");
		upis.append(korisnik.getDeleted().toString());
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
	private void upisSvihKorisnikaUFajl(String putanja) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja));
		for(Customer korisnik : korisnici.values()) {
		upis.append(korisnik.getName());
		upis.append(",");
		upis.append(korisnik.getSurname());
		upis.append(",");
		upis.append(korisnik.getUsername());
		upis.append(",");
		upis.append(korisnik.getPassword());
		upis.append(",");
		upis.append(korisnik.getGender().toString());
		upis.append(",");
		upis.append(korisnik.getDate());
		upis.append(",");
		upis.append(getRoleTypeToString(korisnik.getRole()));
		upis.append(",");
		upis.append(NullToString(korisnik.getPoints()));
		upis.append(",");
		upis.append(getCustomerTypeToString(korisnik.getType()));
		upis.append(",");
		upis.append(korisnik.getDeleted().toString());
		upis.append("\n");
		}
		upis.flush();
		upis.close();
	}
	
	private void upisUUsers(String putanja, User korisnik) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja,true));
		upis.append(korisnik.getName());
		upis.append(",");
		upis.append(korisnik.getSurname());
		upis.append(",");
		upis.append(korisnik.getUsername());
		upis.append(",");
		upis.append(korisnik.getPassword());
		upis.append(",");
		upis.append(korisnik.getGender().toString());
		upis.append(",");
		upis.append(korisnik.getDate());
		upis.append(",");
		upis.append(getRoleTypeToString(korisnik.getRole()));
		upis.append(",");
		upis.append(korisnik.getDeleted().toString());
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
		private static String NullToString(Integer points)
		{
		    return points == null ? "0" : points.toString();
		}
	
	
	public static String getRoleTypeToString(User.RoleEnum status) {
		if (status == User.RoleEnum.CUSTOMER) {
			return "CUSTOMER";
		}else if (status == User.RoleEnum.ADMIN) {
			return "ADMIN";
		}else if (status == User.RoleEnum.MANAGER) {
			return "MANAGER";
		}else if (status == User.RoleEnum.COACH) {
			return "COACH";
		}
		return null;
	}
	
	private User.RoleEnum  getStringToRole(String status) {
		if (status.equals("CUSTOMER") ) {
			return User.RoleEnum.CUSTOMER;
		}else if (status.equals("ADMIN")) {
			return User.RoleEnum.ADMIN ;
		}else if (status.equals("MANAGER")) {
			return User.RoleEnum.MANAGER;
		}else if (status.equals("COACH")) {
			return User.RoleEnum.COACH ;
		}
		return null;
	}
	
	private String getCustomerTypeToString(CustomerType status) {
		if(status != null)
		if (status.getType() == CustomerType.TypeEnum.BRONZE) {
			return "BRONZE";
		}else if (status.getType() == CustomerType.TypeEnum.SILVER) {
			return "SILVER";
		}else if (status.getType() == CustomerType.TypeEnum.GOLD) {
			return "GOLD";
		}
		return "";
	}
	
	public Customer dobaviKorisnika(String korisnickoIme) {
		if (korisnici.containsKey(korisnickoIme)) {
			return korisnici.get(korisnickoIme);
		}
		return null;
	}
	
	public User izmenaPodataka(User korisnik, String putanja) throws IOException {
		User k = korisnici.get(korisnik.getUsername());
		if(k == null)
			k = admininstrator;
		if (!korisnik.getName().equals(k.getName())) {
			k.setName(korisnik.getName());
		}
		if (!korisnik.getSurname().equals(k.getSurname())) {
			k.setSurname(korisnik.getSurname());
		}
		if (!korisnik.getGender().equals(k.getGender())) {
			k.setGender(korisnik.getGender());
		}
		if (!korisnik.getDate().equals(k.getDate())) {
			k.setDate(korisnik.getDate());
		}
		if (!korisnik.getPassword().equals(k.getPassword()) && !korisnik.getPassword().equals("")) {
			k.setPassword(korisnik.getPassword());
		}
		putanja += "data\\Users.csv";
		
		writeAllUsers(putanja);
		writeAllUsers(putanje[5]);
		return k;
	}
	
	public void writeAllMemberships(String putanja) throws IOException{
		Writer upis = new BufferedWriter(new FileWriter(putanja));
		for(Membership member : memberships.values()) {
			upis.append(getTypeToString(member.getType()));
			upis.append(",");
			upis.append(member.getPayDate());
			upis.append(",");
			upis.append(member.getMemberDate());
			upis.append(",");
			upis.append(member.getPrice().toString());
			upis.append(",");
			upis.append(member.getCustomer().getUsername());
			upis.append(",");
			upis.append(member.getStatus().toString());
			upis.append(",");
			upis.append(member.getTermins().toString());
			upis.append("\n");
		}
		upis.flush();
		upis.close();
	}
	
	private String getTypeToString(TypeEnum type) {
		if(type == TypeEnum.DAY)
			return "DAY";
		else if(type == TypeEnum.MONTH)
			return "MONTH";
		else return "YEAR";
	}
	
	private void getAllManagers(String putanja) {
		BufferedReader citac = null;
		try {
			putanja += "\\data\\Managers.csv";
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Boolean pol = Boolean.parseBoolean(parametri[4]);
				Manager korisnik = new Manager(parametri[2], parametri[3], parametri[0], 
						parametri[1], pol, parametri[5],User.RoleEnum.MANAGER,false,new SportsFacility());
				managers.put(parametri[2],korisnik);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if ( citac != null ) {
				try {
					citac.close();
				}
				catch (Exception e) { }
			}
		}
	}

	private void writeAllUsers(String putanja) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja));
		for(User korisnik : korisnici.values()) {
			upis.append(korisnik.getName());
			upis.append(",");
			upis.append(korisnik.getSurname());
			upis.append(",");
			upis.append(korisnik.getUsername());
			upis.append(",");
			upis.append(korisnik.getPassword());
			upis.append(",");
			upis.append(korisnik.getGender().toString());
			upis.append(",");
			upis.append(korisnik.getDate());
			upis.append(",");
			upis.append(getRoleTypeToString(korisnik.getRole()));
			upis.append(",");
			upis.append(korisnik.getDeleted().toString());
			upis.append("\n");
		}
		for(User korisnik : managers.values()) {
			upis.append(korisnik.getName());
			upis.append(",");
			upis.append(korisnik.getSurname());
			upis.append(",");
			upis.append(korisnik.getUsername());
			upis.append(",");
			upis.append(korisnik.getPassword());
			upis.append(",");
			upis.append(korisnik.getGender().toString());
			upis.append(",");
			upis.append(korisnik.getDate());
			upis.append(",");
			upis.append(getRoleTypeToString(korisnik.getRole()));
			upis.append(",");
			upis.append(korisnik.getDeleted().toString());
			upis.append("\n");
			}
		User korisnik = admininstrator;
		upis.append(korisnik.getName());
		upis.append(",");
		upis.append(korisnik.getSurname());
		upis.append(",");
		upis.append(korisnik.getUsername());
		upis.append(",");
		upis.append(korisnik.getPassword());
		upis.append(",");
		upis.append(korisnik.getGender().toString());
		upis.append(",");
		upis.append(korisnik.getDate());
		upis.append(",");
		upis.append(getRoleTypeToString(korisnik.getRole()));
		upis.append(",");
		upis.append(korisnik.getDeleted().toString());
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
	public Membership getMembership(User u) {
		if(memberships.containsKey(u.getUsername()))
			return memberships.get(u.getUsername());
		return null;
	}

	public Membership dodajMembership(Membership member, String putanja) throws IOException {
		String put1 = putanja + "\\data\\Memberships.csv";
		String put2 = putanja + "\\data\\Customers.csv";
		
		if(member.getTypeStr().equals("Dnevna")) {
			member.setType(TypeEnum.DAY);
			member.getCustomer().setType(getCustomerType("BRONZE"));
		}
		else if(member.getTypeStr().equals("Mesecna")) {
			member.setType(TypeEnum.MONTH);
			member.getCustomer().setType(getCustomerType("SILVER"));
			member.getCustomer().setPoints(50);
		}
		else {
			member.setType(TypeEnum.YEAR);
			member.getCustomer().setType(getCustomerType("GOLD"));
			member.getCustomer().setPoints(100);
		}
		if (memberships.containsKey(member.getCustomer().getUsername())) {
			memberships.remove(member.getCustomer().getUsername());
		}
		korisnici.remove(member.getCustomer().getUsername());
		korisnici.put(member.getCustomer().getUsername(), member.getCustomer());
		memberships.put(member.getCustomer().getUsername(), member);
		writeAllMemberships(put1);
		writeAllMemberships(putanje[6]);
		upisSvihKorisnikaUFajl(put2);
		upisSvihKorisnikaUFajl(putanje[4]);
		return member;
	}
	
	public User adminProfile(String username) {
		if(username.equals("admin"))
			return admininstrator;
		return null;
	}
	
	public Collection<User> getAllUsers() {
		Collection<User> users = new ArrayList<User>();
		for(Customer c : korisnici.values()) {
			if(!c.getDeleted())
				users.add(c);
		}
		for(Manager m : managers.values()) {
			if(!m.getDeleted())
				users.add(m);
		}
		for(Coach c : trainers.values()) {
			if(!c.getDeleted())
				users.add(c);
		}
		users.add(admininstrator);
		return users;
		
	}

	public User deleteUser(String username, String path) {
		User u = korisnici.get(username);
		if(u != null) {
			String put1 = path + "\\data\\Customers.csv";
			u.setDeleted(true);
			try {
				upisSvihKorisnikaUFajl(put1);
				upisSvihKorisnikaUFajl(putanje[4]);
				String put2 = path + "\\data\\Users.csv";
				writeAllUsers(put2);
				writeAllUsers(putanje[5]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return u;
	}

	public Manager getManager(String username) {
		if(managers.containsKey(username))
			return managers.get(username);
		return null;
	}


	public Coach getCoach(String username) {
		if(trainers.containsKey(username))
			return trainers.get(username);
		return null;
	}
	
	public Collection<Coach> findAllCoaches(){
		return trainers.values();
	}
	
	private void upisMenadzeraUFajl(String putanja, Manager manager) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(manager.getName());
		upis.append(",");
		upis.append(manager.getSurname());
		upis.append(",");
		upis.append(manager.getUsername());
		upis.append(",");
		upis.append(manager.getPassword());
		upis.append(",");
		upis.append(manager.getGender().toString());
		upis.append(",");
		upis.append(manager.getDate());
		upis.append(",");
		upis.append(getRoleTypeToString(manager.getRole()));
		upis.append(",");
		upis.append(manager.getDeleted().toString());
		upis.append(",");
		if(manager.getFacility() != null)
		upis.append(manager.getFacility().getName());
		else upis.append("n");
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
	

	public Manager addManager(Manager manager, String putanja) throws IOException {
		String put1 = putanja + "\\data\\Managers.csv";
		String put2 = putanja + "\\data\\Users.csv";
		if (managers.containsKey(manager.getUsername())) {
			return null;
		}
		managers.put(manager.getUsername(), manager);
		manager.setDeleted(false);
		manager.setRole(RoleEnum.MANAGER);
		manager.setFacility(null);
		upisMenadzeraUFajl(put1, manager);
		upisMenadzeraUFajl(putanje[7], manager);
		upisUUsers(put2, manager);
		upisUUsers(putanje[5], manager);
		return manager;
	}
	
	public SportsFacility getManagerFacility(Manager m, String path) {
		SportsFacilityDAO sf = new SportsFacilityDAO(path);
		return sf.managers.get(m.getUsername()).getFacility();
	}

	
	
	private void upisTreneraUFajl(String putanja, Coach coach) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(coach.getName());
		upis.append(",");
		upis.append(coach.getSurname());
		upis.append(",");
		upis.append(coach.getUsername());
		upis.append(",");
		upis.append(coach.getPassword());
		upis.append(",");
		upis.append(coach.getGender().toString());
		upis.append(",");
		upis.append(coach.getDate());
		upis.append(",");
		upis.append(getRoleTypeToString(coach.getRole()));
		upis.append(",");
		upis.append(coach.getDeleted().toString());
		upis.append("\n");
		upis.flush();
		upis.close();
	}

	public Coach addCoach(Coach coach, String putanja) throws IOException {
		String put2 = putanja + "\\data\\Users.csv";
		if (managers.containsKey(coach.getUsername())) {
			return null;
		}
		trainers.put(coach.getUsername(), coach);
		coach.setDeleted(false);
		coach.setRole(RoleEnum.COACH);
		upisUUsers(put2, coach);
		upisUUsers(putanje[5], coach);
		return coach;
	}
	
	public Collection<Coach> getCoaches() {
		Collection<Coach> coaches = new ArrayList<Coach>();
		for(Coach c : findAllCoaches()) {
				coaches.add(c);
		}
		return coaches;
	}
	

	private void checkMemberships(String path){
		for(Membership m : memberships.values()) {
			Customer c = korisnici.get(m.getCustomer().getUsername());
			Double pts = 0.0;
			String d = m.getMemberDate();
			LocalDate today = LocalDate.now();
			LocalDate date1 = LocalDate.parse(d);
			if(date1.compareTo(today) <= 0) {
				if(m.getType() == Membership.TypeEnum.DAY)
				{
					if(m.getTermins() == 0) {
						pts = (m.getPrice()/1000.0 ) * 1;
						c.setPoints(c.getPoints() + pts.intValue());
					}
					else {
						pts = (m.getPrice()/1000) *133 * 4;
						c.setPoints(c.getPoints() - pts.intValue());
					} 
					
				}
				if(m.getType() == Membership.TypeEnum.MONTH)
				{
					if(m.getTermins() < 15) {
						pts = (m.getPrice()/1000.0 ) * 20;
						c.setPoints(c.getPoints() + pts.intValue());
					}
					else {
						pts = (m.getPrice()/1000) *133 * 4;
						c.setPoints(c.getPoints() - pts.intValue());
					} 
					
				}
				if(m.getType() == Membership.TypeEnum.YEAR)
				{
					c.setPoints(c.getPoints() + 100);
				}
				m.setStatus(false);
				m.setTermins(0);
				try {
					String put1 = path + "\\data\\Memberships.csv";
					writeAllMemberships(put1);
					String put2 = path + "\\data\\Customers.csv";
					upisSvihKorisnikaUFajl(put2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
	}
	
}
