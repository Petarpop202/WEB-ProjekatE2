package beans;

public class SportsFacility {
	public enum TypeEnum{
		GYM,
		POOL,
		SPORTSCENTER,
		DANCESTUDIO
	}
	public enum ContentEnum{
		GROUP,
		PERSONAL, 
		SAUNA
	}
	private String Name;
	private TypeEnum Type;
	private ContentEnum Content;
	private Boolean Status;
	private Location Location;
	private String Picture;
	private Double Rate;
	private String WorkTime;
	private String StatusStr;
	private String TypeStr;
	
	
	public SportsFacility() {
		super();
	}
	public SportsFacility(String name, TypeEnum type, ContentEnum content, Boolean status, Location location,
			String picture, Double rate, String workTime) {
		super();
		Name = name;
		if(type == TypeEnum.GYM) {
			TypeStr = "Teretana";
		} else if(type == TypeEnum.DANCESTUDIO) {
			TypeStr = "Plesni studio";
		} else if(type == TypeEnum.POOL) {
			TypeStr = "Bazen";
		} else TypeStr = "Sportski centar";
		Type = type;
		Content = content;
		Status = status;
		Location = location;
		Picture = picture;
		Rate = rate;
		WorkTime = workTime;
		if(Status)
			StatusStr = "Radi";
		else StatusStr = "Ne radi";
	}
	
	public String getTypeStr() {
		return TypeStr;
	}
	public void setTypeStr(String typeStr) {
		TypeStr = typeStr;
	}
	
	
	public String getStatusStr() {
		return StatusStr;
	}
	public void setStatusStr(String statusStr) {
		StatusStr = statusStr;
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
	public ContentEnum getContent() {
		return Content;
	}
	public void setContent(ContentEnum content) {
		Content = content;
	}
	public Boolean getStatus() {
		return Status;
	}
	public void setStatus(Boolean status) {
		Status = status;
	}
	public Location getLocation() {
		return Location;
	}
	public void setLocation(Location location) {
		Location = location;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public Double getRate() {
		return Rate;
	}
	public void setRate(Double rate) {
		Rate = rate;
	}
	public String getWorkTime() {
		return WorkTime;
	}
	public void setWorkTime(String workTime) {
		WorkTime = workTime;
	}
	
	
}
