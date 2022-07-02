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
import beans.SportsFacility;
import beans.User;
public class CustomersDAO {
	
	private HashMap<String, Customer> korisnici;
	
	private String[] putanje = {"D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Users.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv",
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Users.csv"};

	
	public CustomersDAO() {
		korisnici = new HashMap<String, Customer>();
		getAllCustomers(putanje[2]);
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
	
	private void getAllCustomers(String putanja) {
		BufferedReader citac = null;
		try {
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Boolean pol = Boolean.parseBoolean(parametri[4]);
				Customer korisnik = new Customer(parametri[2], parametri[3], parametri[0], 
						parametri[1], pol, parametri[5]);
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
		upis.append((CharSequence) korisnik.getMembership());
		upis.append(",");
		upis.append((CharSequence) korisnik.getSportFacilities());
		upis.append(",");
		upis.append(NullToString(korisnik.getPoints()));
		upis.append(",");
		upis.append(getCustomerTypeToString(CustomerType.TypeEnum.BRONZE));
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
	private void upisUUsers(String putanja, Customer korisnik) throws IOException {
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
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
		private static String NullToString(Double points)
		{

		    // Value.ToString() allows for Value being DBNull, but will also convert int, double, etc.
		    return points == null ? "0" : points.toString();

		    // If this is not what you want then this form may suit you better, handles 'Null' and DBNull otherwise tries a straight cast
		    // which will throw if Value isn't actually a string object.
		    //return Value == null || Value == DBNull.Value ? "" : (string)Value;


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
		upis.append(getRoleTypeToString(User.RoleEnum.CUSTOMER));
		upis.append("\n");
		}
		upis.flush();
		upis.close();
	}
	
	
	
}
