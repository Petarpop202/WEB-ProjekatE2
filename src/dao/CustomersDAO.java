package dao;

import java.io.IOException;
import java.util.HashMap;

import beans.Customer;

public class CustomersDAO {
	
	private HashMap<String, Customer> korisnici;
	
	public CustomersDAO() {
		korisnici = new HashMap<String, Customer>();
	}
	
	public Customer dodajKorisnika(Customer korisnik, String putanja) throws IOException {
		putanja += "data\\Customers.csv";
		if (korisnici.containsKey(korisnik.getUsername())) {
			return null;
		}
		korisnici.put(korisnik.getUsername(), korisnik);
		return korisnik;
	}
}
