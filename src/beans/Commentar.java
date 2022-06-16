package beans;

public class Commentar {
	private Customer Customer;
	private SportsFacility Facility;
	private String Text;
	private Integer Rate;
	
	public Commentar() {
		super();
	}
	public Commentar(beans.Customer customer, SportsFacility facility, String text, Integer rate) {
		super();
		Customer = customer;
		Facility = facility;
		Text = text;
		Rate = rate;
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
	
	
}
