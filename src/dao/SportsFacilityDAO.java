package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import beans.Location;
import beans.SportsFacility;


public class SportsFacilityDAO {
private HashMap<String, SportsFacility> facilities;
private HashMap<String, Location> locations;
	
	public Collection<SportsFacility> findAll() {
		return facilities.values();
	}

	public SportsFacilityDAO() {
		facilities = new HashMap<String, SportsFacility>();
		locations = new HashMap<String, Location>();
	}
	
	public SportsFacilityDAO(String path) {
		facilities = new HashMap<String, SportsFacility>();
		locations = new HashMap<String, Location>();
		getAllLocations(path);
		getAllSportFacilities(path);
	}
	
	private void getAllSportFacilities(String path) {
		BufferedReader reader = null;
		try {
			File file = new File(path + "data\\SportsFacility.csv");
			reader = new BufferedReader(new FileReader(file));
			String linija = "";
			while ((linija = reader.readLine()) != null) {
				String[] parametri = linija.split(",");
				String Name= parametri[0];
				SportsFacility.TypeEnum Type = getType(parametri[1]);
				SportsFacility.ContentEnum Content = getContent(parametri[2]);
				Boolean Status = Boolean.parseBoolean(parametri[3]);
				Location Location = getLocation(parametri[4]);
				String Picture = parametri[5];
				Double Rate = Double.parseDouble(parametri[6]);
				String WorkTime = parametri[7];
				SportsFacility s = new SportsFacility(Name,Type,Content,Status,Location,Picture,Rate,WorkTime);
				facilities.put(Name,s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( reader != null ) {
				try {
					reader.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	
	
	private SportsFacility.TypeEnum getType(String status) {
		if (status.equals("GYM")) {
			return SportsFacility.TypeEnum.GYM;
		}else if (status.equals("POOL")) {
			return SportsFacility.TypeEnum.POOL;
		}else if (status.equals("SPORTSCENTER")) {
			return SportsFacility.TypeEnum.SPORTSCENTER;
		}else if (status.equals("DANCESTUDIO")) {
			return SportsFacility.TypeEnum.DANCESTUDIO;
		}
		return null;
	}
	
	private SportsFacility.ContentEnum getContent(String status) {
		if (status.equals("GROUP")) {
			return SportsFacility.ContentEnum.GROUP;
		}else if (status.equals("PERSONAL")) {
			return SportsFacility.ContentEnum.PERSONAL;
		}else if (status.equals("SAUNA")) {
			return SportsFacility.ContentEnum.SAUNA;
		}
		return null;
	}
	
	private void getAllLocations(String path) {
		BufferedReader reader = null;
		try {
			File file = new File(path + "data\\Locations.csv");
			reader = new BufferedReader(new FileReader(file));
			String linija = "";
			while ((linija = reader.readLine()) != null) {
				String[] parametri = linija.split(",");
				Double Length= Double.parseDouble(parametri[0]);
				Double Width = Double.parseDouble(parametri[1]);
				String Address = parametri[2];
				Location l = new Location(Length,Width,Address);
				locations.put(Address, l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( reader != null ) {
				try {
					reader.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public Location getLocation(String Address) {
		if (locations.containsKey(Address)) {
			return locations.get(Address);
		}
		return null;
	}
	
	private Collection<SportsFacility> GetAll(){
		Collection<SportsFacility> sports = new ArrayList<SportsFacility>();
		for(SportsFacility s : findAll()) {
		sports.add(s);
		}
		return sports;
	}
	
public Collection<SportsFacility> getSearchFacility(String name, String type, String location, String rate) throws  IOException{
		//GetAll();
	
		Collection<SportsFacility> allfacilities = GetAll();
		Collection<SportsFacility> suitableFacilities = GetAll();
		
		if(!name.trim().isEmpty()) {
			suitableFacilities.clear();
			for (SportsFacility facility : findAll())
				if(facility.getName().toLowerCase().contains(name.toLowerCase()))
					suitableFacilities.add(facility);
				
			allfacilities.clear();
			allfacilities.addAll(suitableFacilities);
		}
		/*
		if(!location.trim().isEmpty()) {
			suitableFacilities.clear();
			for (Restaurant restaurant : allRestaurants)
				if(restaurant.getLocation().toString().toLowerCase().contains(searchParameters.getLocation().toLowerCase().trim()))
					suitableRestaurants.add(restaurant);
				
			allRestaurants.clear();
			allRestaurants.addAll(suitableRestaurants);
		}
		
		if(!searchParameters.getType().trim().isEmpty()) {
			suitableRestaurants.clear();
			for (Restaurant restaurant : allRestaurants) {
				String typeRest = restaurant.getType().toString();
				if(typeRest.toLowerCase().contains(searchParameters.getType().toLowerCase().trim())) {
					suitableRestaurants.add(restaurant);
				}
			}
			
		    allRestaurants.clear();
			allRestaurants.addAll(suitableRestaurants);
		}
		
		if(!searchParameters.getGrade().trim().isEmpty()) {
			int gradeFilter = Integer.parseInt(searchParameters.getGrade());
			double minGrade = (gradeFilter == 1) ? 1. : gradeFilter - 0.5;
			double maxGrade = (gradeFilter == 5) ? 5. : gradeFilter + 0.5;
			
			
			suitableRestaurants.clear();
			for (Restaurant restaurant : allRestaurants) 
				if(restaurant.getGrade() > minGrade && restaurant.getGrade() < maxGrade) 
					suitableRestaurants.add(restaurant);
			
			allRestaurants.clear();
			allRestaurants.addAll(suitableRestaurants);
		}*/
					
		return suitableFacilities;
	}
	
}
