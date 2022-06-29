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

import beans.Coach;
import beans.Location;
import beans.SportsFacility;
import beans.Training;
import beans.Training.TypeEnum;
import beans.User;


public class SportsFacilityDAO {
private HashMap<String, SportsFacility> facilities;
private HashMap<String, Location> locations;
private HashMap<String, Training> trainings;
	
	public Collection<SportsFacility> findAll() {
		return facilities.values();
	}
	
	public Collection<Training> findAllTrainings(){
		return trainings.values();
	}

	public SportsFacilityDAO() {
		facilities = new HashMap<String, SportsFacility>();
		locations = new HashMap<String, Location>();
	}
	
	public SportsFacilityDAO(String path) {
		facilities = new HashMap<String, SportsFacility>();
		locations = new HashMap<String, Location>();
		trainings = new HashMap<String, Training>();
		getAllLocations(path);
		getAllSportFacilities(path);
		getAllTrainings(path);
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
	
	private SportsFacility.TypeEnum getTypeSr(String status) {
		if (status.equals("Teretana")) {
			return SportsFacility.TypeEnum.GYM;
		}else if (status.equals("Bazen")) {
			return SportsFacility.TypeEnum.POOL;
		}else if (status.equals("Sportski centar")) {
			return SportsFacility.TypeEnum.SPORTSCENTER;
		}else if (status.equals("Plesni studio")) {
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
	
	private void getAllTrainings(String path) {
		BufferedReader reader = null;
		try {
			File file = new File(path + "data\\Trainings.csv");
			reader = new BufferedReader(new FileReader(file));
			String linija = "";
			while ((linija = reader.readLine()) != null) {
				String[] parametri = linija.split(",");
				String Name= parametri[0];
				Training.TypeEnum Type = getTrainingType(parametri[1]);
				SportsFacility Facility = getFacility(parametri[2]);
				String duration = parametri[3];
				Coach Trainer = new Coach();
				String Description = parametri[4];
				String Picture = parametri[5];
				Training t = new Training(Name,Type,Facility,duration,Trainer,Description,Picture);
				trainings.put(Name, t);
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
	
	private Collection<Training> getFacilityTrainings(String name){
		Collection<Training> trainingsFacility = new ArrayList<Training>();
		for(Training t : findAllTrainings()) {
			if(t.getFacility().getName().equals(name))
				trainingsFacility.add(t);
		}
		return trainingsFacility;
	}
	
	
	private Training.TypeEnum getTrainingType(String status) {
		if (status.equals("GROUP")) {
			return Training.TypeEnum.GROUP;
		}else if (status.equals("PERSONAL")) {
			return Training.TypeEnum.PERSONAL;
		}else if (status.equals("GYM")) {
			return Training.TypeEnum.GYM;
		}
		return null;
	}

	public Location getLocation(String Address) {
		if (locations.containsKey(Address)) {
			return locations.get(Address);
		}
		return null;
	}
	
	public Collection<SportsFacility> GetAll(){
		Collection<SportsFacility> sports = new ArrayList<SportsFacility>();
		for(SportsFacility s : findAll()) {
			if(s.getStatus()) {
				sports.add(s);
			}
		}
		for(SportsFacility s : findAll()) {
			if(!s.getStatus()) {
				sports.add(s);
			}
		}
		return sports;
	}
	
	public Collection<SportsFacility> getSearchFacility(String name, String type, String location, String rate, String opt) throws  IOException{
		//GetAll();
	
		Collection<SportsFacility> allfacilities = GetAll();
		Collection<SportsFacility> suitableFacilities = GetAll();
		if(opt.equals("Naziv"))
		if(!name.trim().isEmpty()) {
			suitableFacilities.clear();
			for (SportsFacility facility : findAll())
				if(facility.getName().toLowerCase().contains(name.toLowerCase()))
					suitableFacilities.add(facility);
				
			allfacilities.clear();
			allfacilities.addAll(suitableFacilities);
		}
		
		if(opt.equals("Lokacija"))
		if(!location.trim().isEmpty()) {
			suitableFacilities.clear();
			for (SportsFacility facility : findAll())
				if(facility.getLocation().getAddress().toString().toLowerCase().contains(location.toLowerCase().trim()))
					suitableFacilities.add(facility);
				
			allfacilities.clear();
			allfacilities.addAll(suitableFacilities);
		}
		
		if(opt.equals("Tip"))
		if(!type.trim().isEmpty()) {
			suitableFacilities.clear();
			for (SportsFacility facility : findAll()) {
				SportsFacility.TypeEnum typeSport = getTypeSr(type);
				if(typeSport == facility.getType()) {
					suitableFacilities.add(facility);
				}
			}
			
			allfacilities.clear();
			allfacilities.addAll(suitableFacilities);
		}
		
		int gradeFilter = Integer.parseInt(rate);
		double minGrade = (gradeFilter == 1) ? 1. : gradeFilter - 0.5;
		double maxGrade = (gradeFilter == 5) ? 5. : gradeFilter + 0.5;
		
		if(opt.equals("Ocena"))
		if(!type.trim().isEmpty()) {
			suitableFacilities.clear();
			for (SportsFacility facility : findAll()) {
				if(facility.getRate() > minGrade && facility.getRate() < maxGrade) 
					suitableFacilities.add(facility);
			}
			
			allfacilities.clear();
			allfacilities.addAll(suitableFacilities);
		}
		
					
		return suitableFacilities;
	}
	
	
	public SportsFacility getFacility(String name) {
        if (facilities.containsKey(name)) {
            return facilities.get(name);
        }
        return null;
    }
	
}
