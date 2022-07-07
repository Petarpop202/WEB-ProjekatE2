package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import beans.Coach;
import beans.Customer;
import beans.Location;
import beans.Manager;
import beans.SportsFacility;
import beans.Training;
import beans.Training.TypeEnum;
import beans.TrainingHistory;
import beans.User;


public class SportsFacilityDAO {
private HashMap<String, SportsFacility> facilities;
private HashMap<String, Location> locations;
private HashMap<String, Training> trainings;
public HashMap<String, Manager> managers;
public HashMap<String, Coach> trainers;
public HashMap<String, TrainingHistory> trainingHistories;


private String[] putanje = {"D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Locations.csv",
		"D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\SportsFacility.csv",
		"D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Managers.csv",
		"D:\\David\\WEB\\WEB-ProjekatE2\\WebContent\\data\\Trainings.csv",
		"C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Locations.csv",
		"C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\SportsFacility.csv",
		"C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Managers.csv",
		"C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\Trainings.csv",
		"C:\\Users\\petar\\Desktop\\FitnessCentarWeb\\WEB-ProjekatE2\\WebContent\\data\\TrainingHistories.csv"};
	

	public Collection<SportsFacility> findAll() {
		return facilities.values();
	}
	
	public Collection<Training> findAllTrainings(){
		return trainings.values();
	}
	
	public Collection<Manager> findAllManagers(){
		return managers.values();
	}

	public SportsFacilityDAO() {
		facilities = new HashMap<String, SportsFacility>();
		locations = new HashMap<String, Location>();
		trainings = new HashMap<String, Training>();
		managers = new HashMap<String, Manager>();
	}
	
	public SportsFacilityDAO(String path) {
		facilities = new HashMap<String, SportsFacility>();
		locations = new HashMap<String, Location>();
		trainings = new HashMap<String, Training>();
		managers = new HashMap<String, Manager>();
		trainingHistories = new HashMap<String, TrainingHistory>();
		getAllLocations(path);
		getAllSportFacilities(path);
		getAllTrainings(path);
		getAllManagers(path);
		getAllTrainingHistories(path);
	}
	
	private void getAllTrainingHistories(String path) {
		BufferedReader reader = null;
		try {
			File file = new File(path + "data\\TrainingHistories.csv");
			reader = new BufferedReader(new FileReader(file));
			String linija = "";
			while ((linija = reader.readLine()) != null) {
				String[] parametri = linija.split(",");
				String Date= parametri[0];
				Training training = getTraining(parametri[1]);
				CustomersDAO cd = new CustomersDAO(path);
				Customer custom = cd.dobaviKorisnika(parametri[2]);
				Coach coach = cd.getCoach(parametri[3]);
				TrainingHistory t = new TrainingHistory(Date,training,custom,coach);
				trainingHistories.put(parametri[1],t);
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

	private void getAllManagers(String putanja) {
		BufferedReader citac = null;
		String s;
		try {
			putanja += "\\data\\Managers.csv";
			File fajl = new File(putanja);
			citac = new BufferedReader(new FileReader(fajl));
			String linija = "";
			while((linija = citac.readLine()) != null) {
				String[] parametri = linija.split(",");
				Boolean pol = Boolean.parseBoolean(parametri[4]);
				SportsFacility sf = getFacility(parametri[8]);
				Manager korisnik = new Manager(parametri[2], parametri[3], parametri[0], 
						parametri[1], pol, parametri[5],User.RoleEnum.MANAGER,false,sf);
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
	
	
	private String getTypeToString(SportsFacility.TypeEnum status) {
		if (status == SportsFacility.TypeEnum.GYM) {
			return "GYM";
		}else if (status == SportsFacility.TypeEnum.POOL) {
			return "POOL";
		}else if (status == SportsFacility.TypeEnum.DANCESTUDIO) {
			return "DANCESTUDIO";
		}else if (status == SportsFacility.TypeEnum.SPORTSCENTER) {
			return "SPORTSCENTER";
		}
		return null;
	}
	
	private String getTrainingTypeToString(Training.TypeEnum status) {
		if (status == Training.TypeEnum.GYM) {
			return "GYM";
		}else if (status == Training.TypeEnum.GROUP) {
			return "GROUP";
		}else if (status == Training.TypeEnum.PERSONAL) {
			return "PERSONAL";
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
	
	private Training.TypeEnum getTrainingTypeSr(String status) {
		if (status.equals("Teretana")) {
			return Training.TypeEnum.GYM;
		}else if (status.equals("Personalni trening")) {
			return Training.TypeEnum.PERSONAL;
		}else if (status.equals("Grupni trening")) {
			return Training.TypeEnum.GROUP;
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
				CustomersDAO cd = new CustomersDAO(path);
				Coach Trainer = cd.getCoach(parametri[4]);
				String Description = parametri[5];
				String Picture = parametri[6];
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
	
	public Collection<Training> getFacilityTrainings(String name){
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
	
	public Coach getCoach(String name) {
        if (trainers.containsKey(name)) {
            return trainers.get(name);
        }
        return null;
    }

	public SportsFacility dodajObjekat(SportsFacility facility, String putanja) throws IOException {
		String put = putanja + "\\data\\SportsFacility.csv";
		if (facilities.containsKey(facility.getName())) {
			return null;
		}
		facilities.put(facility.getName(), facility);
		upisObjektaUFajl(put, facility);
		upisObjektaUFajl(putanje[5], facility);
		return facility;
	}
	
	private void upisObjektaUFajl(String putanja, SportsFacility facility) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(facility.getName());
		upis.append(",");
		upis.append(getTypeToString(facility.getType()));
		upis.append(",");
		upis.append("nista");
		upis.append(",");
		upis.append("True");
		upis.append(",");
		upis.append(facility.getLocation().getAddress());
		upis.append(",");
		upis.append(facility.getPicture());
		upis.append(",");
		upis.append("0");
		upis.append(",");
		upis.append("09:00-21:00");
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
	private void upisLokacijeUFajl(String putanja, Location location) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(location.getWidth().toString());
		upis.append(",");
		upis.append(location.getLength().toString());
		upis.append(",");
		upis.append(location.getAddress());
		upis.append("\n");
		upis.flush();
		upis.close();
	}
	
	private void upisSvihMenadzeraUFajl(String putanja) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja));
		for(Manager manager : managers.values()) {
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
		upis.append(CustomersDAO.getRoleTypeToString(manager.getRole()));
		upis.append(",");
		upis.append(manager.getDeleted().toString());
		upis.append(",");
		if(manager.getFacility() != null)
		upis.append(manager.getFacility().getName());
		else upis.append("n");
		upis.append("\n");
		}
		upis.flush();
		upis.close();
	}
	

	public SportsFacility dodajObjekat(String name, String type, String address, String width, String length, String manager, String picture,String putanja) throws IOException {
		String put0 = putanja + "\\data\\Locations.csv";
		String put1 = putanja + "\\data\\SportsFacility.csv";
		String put2 = putanja + "\\data\\Managers.csv";
		SportsFacility.TypeEnum t = getTypeSr(type);
		Location l = new Location(Double.parseDouble(length), Double.parseDouble(width),address);
		SportsFacility sf = new SportsFacility(name,t,l,picture);
		sf.setStatus(true);
		Manager m = getManager(manager);
		m.setFacility(sf);
		if (facilities.containsKey(name)) {
			return null;
		}
		facilities.put(name, sf);
		locations.put(l.getAddress(), l);
		upisLokacijeUFajl(put0, l);
		upisLokacijeUFajl(putanje[4], l);
		upisObjektaUFajl(put1, sf);
		upisObjektaUFajl(putanje[5], sf);
		upisSvihMenadzeraUFajl(put2);
		upisSvihMenadzeraUFajl(putanje[6]);
		return sf;
	}
	
	private Manager getManager(String username) {
		if(managers.containsKey(username))
			return managers.get(username);
		return null;
	}
	
	
	public Collection<Manager> GetAllAvlManager(){
		Collection<Manager> managers = new ArrayList<Manager>();
		for(Manager m : findAllManagers()) {
			if(m.getFacility() == null) {
				managers.add(m);
			} else continue;
		}
		return managers;
	}

	public Training dodajTrening(String name, String type, String facility, String duration, String trainer, String description, String picture, String putanja) throws IOException {
		String put0 = putanja + "\\data\\Trainings.csv";
		Training.TypeEnum t = getTrainingTypeSr(type);
		SportsFacility sf = getFacility(facility);
		Coach c = getCoach(trainer);
		Training tr = new Training(name,t,sf,duration,c,description,picture);

		upisTreningaUFajl(put0, tr);
		upisTreningaUFajl(putanje[7], tr);

		return tr;
	}

	private void upisTreningaUFajl(String putanja, Training training) throws IOException {
		Writer upis = new BufferedWriter(new FileWriter(putanja, true));
		upis.append(training.getName());
		upis.append(",");
		upis.append(getTrainingTypeToString(training.getType()));
		upis.append(",");
		upis.append(training.getFacility().getName());
		upis.append(",");
		upis.append(training.getDuration());
		upis.append(",");
		upis.append(training.getTrainer().getName());
		upis.append(",");
		upis.append(training.getDescription());
		upis.append(",");
		upis.append(training.getPicture());
		upis.append("\n");
		upis.flush();
		upis.close();
		
	}

	public Training getTraining(String name) {
		if(trainings.containsKey(name))
			return trainings.get(name);
		return null;
	}
	
	public Collection<TrainingHistory> getTrainingHistoriesOfCustomer(String name, String path){
		Collection<TrainingHistory> history = new ArrayList<TrainingHistory>();
		for(TrainingHistory th : trainingHistories.values()) {
			if(th.getCustomer().getUsername().equals(name))
				history.add(th);
		}
		return history;
	}

	public TrainingHistory checkTraining(Customer ulogovani, Training tr,String path) {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		TrainingHistory his = new TrainingHistory(date.format(formatter).toString(),tr,ulogovani,tr.getTrainer());
		trainingHistories.put(tr.getName(),his);
		
		try {
			writeHistory(his,path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return his;
	}

	private void writeHistory(TrainingHistory his, String path) throws IOException {
		path += "\\data\\TrainingHistories.csv";
		Writer upis = new BufferedWriter(new FileWriter(path, true));
		upis.append(his.getDate());
		upis.append(",");
		upis.append(his.getTraining().getName());
		upis.append(",");
		upis.append(his.getCustomer().getUsername());
		upis.append(",");
		upis.append(his.getTrainer().getUsername());
		upis.append("\n");
		upis.flush();
		upis.close();
	}
}
