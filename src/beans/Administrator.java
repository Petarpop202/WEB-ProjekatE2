package beans;

public class Administrator extends User{

	public Administrator() {
		super();
	}

	public Administrator(String username, String password, String name, String surname, Boolean gender, String date,
			RoleEnum role) {
		super(username, password, name, surname, gender, date, role);
		
	}
	
	
}
