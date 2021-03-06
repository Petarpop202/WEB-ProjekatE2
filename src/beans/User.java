package beans;

public class User {
	public enum RoleEnum {
		  ADMIN,
		  MANAGER,
		  COACH,
		  CUSTOMER
		}
	private String Username;
	private String Password;
	private String Name;
	private String Surname;
	private Boolean Gender;
	private String Date;
	private RoleEnum Role;
	private String jwt;
	private Boolean Deleted;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String name, String surname, Boolean gender, String date,
			RoleEnum role, Boolean deleted) {
		super();
		Username = username;
		Password = password;
		Name = name;
		Surname = surname;
		Gender = gender;
		Date = date;
		Role = role;
		Deleted = deleted;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public Boolean getGender() {
		return Gender;
	}
	public void setGender(Boolean gender) {
		Gender = gender;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public RoleEnum getRole() {
		return Role;
	}
	public void setRole(RoleEnum role) {
		Role = role;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public Boolean getDeleted() {
		return Deleted;
	}
	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}
	
	
	
}
