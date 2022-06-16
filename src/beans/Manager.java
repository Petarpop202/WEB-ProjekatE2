package beans;

public class Manager extends User{
	private SportsFacility Facility;

	
	public Manager(SportsFacility facility) {
		super();
		Facility = facility;
	}

	public Manager() {
		super();
	}

	public Manager(String username, String password, String name, String surname, Boolean gender, String date,
			RoleEnum role) {
		super(username, password, name, surname, gender, date, role);
	}

	public SportsFacility getFacility() {
		return Facility;
	}

	public void setFacility(SportsFacility facility) {
		Facility = facility;
	}
	
	
}
