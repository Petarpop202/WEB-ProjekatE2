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
	 "C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Customers.csv"};

	
	public CustomersDAO() {
		korisnici = new HashMap<String, Customer>();
	}
	
	public Customer dodajKorisnika(Customer korisnik, String putanja) throws IOException {
		putanja += "\\data\\Customers.csv";
		if (korisnici.containsKey(korisnik.getUsername())) {
			return null;
		}
		korisnici.put(korisnik.getUsername(), korisnik);
		upisKorisnikaUFajl(putanja, korisnik);
		upisKorisnikaUFajl(putanje[1], korisnik);
		return korisnik;
	}
	
		private void upisKorisnikaUFajl(String putanja, Customer korisnik) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(korisnik.getName());
		upis.append("|");
		upis.append(korisnik.getSurname());
		upis.append("|");
		upis.append(korisnik.getUsername());
		upis.append("|");
		upis.append(korisnik.getPassword());
		upis.append("|");
		upis.append(korisnik.getGender().toString());
		upis.append("|");
		upis.append(korisnik.getDate());
		upis.append("|");
		upis.append(getRoleTypeToString(korisnik.getRole()));
		upis.append("|");
		upis.append((CharSequence) korisnik.getMembership());
		upis.append("|");
		upis.append((CharSequence) korisnik.getSportFacilities());
		upis.append("|");
		upis.append(NullToString(korisnik.getPoints()));
		upis.append("|");
		upis.append(getCustomerTypeToString(CustomerType.TypeEnum.BRONZE));
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
	
	
	
	
	
}
