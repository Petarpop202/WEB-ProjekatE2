package beans;

public class Commentar {
	private Customer Customer;
	private SportsFacility Facility;
	private String Text;
	private Integer Rate;
	private Boolean Accepted;
	
	public Commentar() {
		super();
	}
	public Commentar(beans.Customer customer, SportsFacility facility, String text, Integer rate,Boolean accepted) {
		super();
		Customer = customer;
		Facility = facility;
		Text = text;
		Rate = rate;
		Accepted = accepted;
	}
	public Customer getCustomer() {
		return Customer;
	}
	public void setCustomer(Customer customer) {
		Customer = customer;
	}
	public SportsFacility getFacility() {
		return Facility;
	}
	public void setFacility(SportsFacility facility) {
		Facility = facility;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public Integer getRate() {
		return Rate;
	}
	public void setRate(Integer rate) {
		Rate = rate;
	}
	public Boolean getAccepted() {
		return Accepted;
	}
	public void setAccepted(Boolean accepted) {
		Accepted = accepted;
	}
	
	
}
