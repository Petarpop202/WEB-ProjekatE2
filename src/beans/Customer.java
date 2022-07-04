package beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer extends User{
	private Membership Membership;
	private List<SportsFacility> SportFacilities;
	private Integer Points;
	private CustomerType Type;
	
	public Customer() {
		super();
	}
	public Customer(String username, String password, String name, String surname, Boolean gender, String date ,Integer points, CustomerType type) {
		super(username, password, name, surname, gender, date, User.RoleEnum.CUSTOMER);
		Membership = new Membership();
		Points = points;
		Type = type;
	}
	
	public Customer(Membership membership, List<SportsFacility> sportFacilities, Integer points, CustomerType type) {
		super();
		Membership = membership;
		SportFacilities = sportFacilities;
		Points = points;
		Type = type;
	}
	
	public CustomerType getType() {
		return Type;
	}
	public void setType(CustomerType type) {
		Type = type;
	}
	public Membership getMembership() {
		return Membership;
	}
	public void setMembership(Membership membership) {
		Membership = membership;
	}
	public List<SportsFacility> getSportFacilities() {
		return SportFacilities;
	}
	public void setSportFacilities(List<SportsFacility> sportFacilities) {
		SportFacilities = sportFacilities;
	}
	public Integer getPoints() {
		return Points;
	}
	public void setPoints(Integer points) {
		Points = points;
	}
	
	
}
