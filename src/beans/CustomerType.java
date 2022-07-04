package beans;

public class CustomerType {
	public enum TypeEnum{
		GOLD,
		SILVER,
		BRONZE
	}
	private TypeEnum Type;
	private Double Discount;
	private Double Points;
	
	public CustomerType() {
		super();
	}
	public CustomerType(TypeEnum type, Double discount, Double points) {
		super();
		Type = type;
		Discount = discount;
		Points = points;
	}
	public TypeEnum getType() {
		return Type;
	}
	public void setType(TypeEnum type) {
		Type = type;
	}
	public Double getDiscount() {
		return Discount;
	}
	public void setDiscount(Double discount) {
		Discount = discount;
	}
	public Double getPoints() {
		return Points;
	}
	public void setPoints(Double points) {
		Points = points;
	}
	
	
	
}
