package beans;

public class Training {
	public enum TypeEnum{
		GROUP,
		PERSONAL,
		GYM
	}
	private String Name;
	private TypeEnum Type;
	private SportsFacility Facility;
	private String Duration;
	private Coach Trainer;
	private String Description;
	private String Picture;
	
	
	public Training(String name, TypeEnum type, SportsFacility facility, String duration, Coach trainer,
			String description, String picture) {
		super();
		Name = name;
		Type = type;
		Facility = facility;
		Duration = duration;
		Trainer = trainer;
		Description = description;
		Picture = picture;
	}
	
	
	public Training(String trainingName, TypeEnum t, String duration, Coach trainer, String description) {
		Name = trainingName;
		Type = t;
		Duration = duration;
		Trainer = trainer;
		Description = description;
	}
	
	public Training() {
		// TODO Auto-generated constructor stub
	}

	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public TypeEnum getType() {
		return Type;
	}
	public void setType(TypeEnum type) {
		Type = type;
	}
	public SportsFacility getFacility() {
		return Facility;
	}
	public void setFacility(SportsFacility facility) {
		Facility = facility;
	}
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		Duration = duration;
	}
	public Coach getTrainer() {
		return Trainer;
	}
	public void setTrainer(Coach trainer) {
		Trainer = trainer;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	
	
}
