package beans;

public class Membership {
	enum TypeEnum{
		YEAR,
		MONTH,
		DAY
	}
	private String Id;
	private TypeEnum Type;
	private String PayDate;
	private String MemberDate;
	private Double Price;
	private Customer Customer;
	private Boolean Status;
	private Integer Termins;
	
	
	
	public Membership(String id, TypeEnum type, String payDate, String memberDate, Double price,
			beans.Customer customer, Boolean status, Integer termins) {
		super();
		Id = id;
		Type = type;
		PayDate = payDate;
		MemberDate = memberDate;
		Price = price;
		Customer = customer;
		Status = status;
		Termins = termins;
	}
	public Membership() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public TypeEnum getType() {
		return Type;
	}
	public void setType(TypeEnum type) {
		Type = type;
	}
	public String getPayDate() {
		return PayDate;
	}
	public void setPayDate(String payDate) {
		PayDate = payDate;
	}
	public String getMemberDate() {
		return MemberDate;
	}
	public void setMemberDate(String memberDate) {
		MemberDate = memberDate;
	}
	public Double getPrice() {
		return Price;
	}
	public void setPrice(Double price) {
		Price = price;
	}
	public Customer getCustomer() {
		return Customer;
	}
	public void setCustomer(Customer customer) {
		Customer = customer;
	}
	public Boolean getStatus() {
		return Status;
	}
	public void setStatus(Boolean status) {
		Status = status;
	}
	public Integer getTermins() {
		return Termins;
	}
	public void setTermins(Integer termins) {
		Termins = termins;
	}
	
	
}
