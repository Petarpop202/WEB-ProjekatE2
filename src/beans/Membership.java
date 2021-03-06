package beans;

public class Membership {
	public enum TypeEnum{
		YEAR,
		MONTH,
		DAY
	}
	private TypeEnum Type;
	private String PayDate;
	private String MemberDate;
	private Double Price;
	private Customer Customer;
	private Boolean Status;
	private Integer Termins;
	private String TypeStr;
	
	
	
	public Membership(TypeEnum type, String payDate, String memberDate, Double price,
			Customer customer, Boolean status, Integer termins) {
		super();
		Type = type;
		PayDate = payDate;
		MemberDate = memberDate;
		Price = price;
		Customer = customer;
		Status = status;
		Termins = termins;
		if(Type == TypeEnum.DAY)
			TypeStr = "Dnevna";
		else if (Type == TypeEnum.MONTH)
			TypeStr = "Mesecna";
		else TypeStr = "Godisnja";
	}
	
	public Membership(String typeStr, String payDate, String memberDate, Double price,
			Customer customer, Boolean status, Integer termins) {
		super();
		PayDate = payDate;
		MemberDate = memberDate;
		Price = price;
		Customer = customer;
		Status = status;
		Termins = termins;
		TypeStr = typeStr;
	}
	public Membership() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getTypeStr() {
		return TypeStr;
	}
	public void setTypeStr(String typeStr) {
		TypeStr = typeStr;
	}
	
	
}
