package beans;

import java.util.List;

public class Customer extends User{
	private Membership Membership;
	private List<SportsFacility> SportFacilities;
	private Double Points;
	private CustomerType Type;
	
	public Customer() {
		super();
	}
	public Customer(String username, String password, String name, String surname, Boolean gender, String date,
			RoleEnum role) {
		super(username, password, name, surname, gender, date, role);
	}
	
	public Customer(Membership membership, List<SportsFacility> sportFacilities, Double points, CustomerType type) {
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
	public Double getPoints() {
		return Points;
	}
	public void setPoints(Double points) {
		Points = points;
	}
	
	
}
