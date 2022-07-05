package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.io.IOException;
import java.util.HashMap;

import beans.Customer;
import beans.CustomerType;
import beans.Membership;
import beans.Membership.TypeEnum;
import beans.SportsFacility;
import beans.User;
public class CustomersDAO {
	
	private HashMap<String, Customer> korisnici;
	private HashMap<String, Membership> memberships;
	private User admininstrator;
	
	private String[] putanje = {"D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Users.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Users.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Memberships.csv",
	 "C:\\Users\\david\\OneDrive\\Desktop\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "C:\\Users\\david\\OneDrive\\Desktop\\WEB-ProjekatE2\\WebContent\\data\\Users.csv"};

	
	public CustomersDAO(String path) {
		admininstrator = new User();
		korisnici = new HashMap<String, Customer>();
		memberships = new HashMap<String, Membership>();
		getAdmin(path);
		getAllCustomers(putanje[2]);
		path += "\\data\\Memberships.csv";
		getAllMemberships(path);
		
	}
	
	public Customer dodajKorisnika(Customer korisnik, String putanja) throws IOException {
		String put1 = putanja + "\\data\\Customers.csv";
		String put2 = putanja + "\\data\\Users.csv";
		if (korisnici.containsKey(korisnik.getUsername())) {
			return null;
		}
		korisnici.put(korisnik.getUsername(), korisnik);
		upisKorisnikaUFajl(put1, korisnik);
		upisKorisnikaUFajl(putanje[2], korisnik);
		upisUUsers(put2, korisnik);
		upisUUsers(putanje[3], korisnik);
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
						parametri[1], pol, parametri[5],points,cType);
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
						parametri[1], pol, parametri[5],getStringToRole(parametri[6]));
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
		upis.append(getRoleTypeToString(User.RoleEnum.CUSTOMER));
		upis.append(",");
		upis.append(NullToString(korisnik.getPoints()));
		upis.append(",");
		upis.append(getCustomerTypeToString(korisnik.getType().getType()));
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
		upis.append(getRoleTypeToString(User.RoleEnum.CUSTOMER));
		upis.append(",");
		upis.append(NullToString(korisnik.getPoints()));
		upis.append(",");
		upis.append(getCustomerTypeToString(korisnik.getType().getType()));
		upis.append("\n");
		}
		upis.flush();
		upis.close();
	}
	
	private void upisUUsers(String putanja, Customer korisnik) throws IOException {
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
		upis.append(getRoleTypeToString(User.RoleEnum.CUSTOMER));
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
		private static String NullToString(Integer points)
		{
		    return points == null ? "0" : points.toString();
		}
	
	
	private String getRoleTypeToString(User.RoleEnum status) {
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
	
	private String getCustomerTypeToString(CustomerType.TypeEnum status) {
		if (status == CustomerType.TypeEnum.BRONZE) {
			return "BRONZE";
		}else if (status == CustomerType.TypeEnum.SILVER) {
			return "SILVER";
		}else if (status == CustomerType.TypeEnum.GOLD) {
			return "GOLD";
		}
		return null;
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
		writeAllUsers(putanje[3]);
		return k;
	}
	
	private void writeAllMemberships(String putanja) throws IOException{
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
		//member.getCustomer().setMembership(member);
		
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
		writeAllMemberships(putanje[4]);
		upisSvihKorisnikaUFajl(put2);
		upisSvihKorisnikaUFajl(putanje[2]);
		return member;
	}
	
	public User adminProfile(String username) {
		if(username.equals("admin"))
			return admininstrator;
		return null;
	}
	
	
}
