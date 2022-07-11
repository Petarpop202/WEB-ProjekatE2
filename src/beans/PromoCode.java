package beans;

public class PromoCode {
	private String id;
	private String code;
	private Integer uses;
	private String expiraton;
	private Double discount;
	
	public PromoCode(String id, String code, Integer uses, String expiraton, Double discount) {
		super();
		this.id = id;
		this.code = code;
		this.uses = uses;
		this.expiraton = expiraton;
		this.discount = discount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getUses() {
		return uses;
	}

	public void setUses(Integer uses) {
		this.uses = uses;
	}

	public String getExpiraton() {
		return expiraton;
	}

	public void setExpiraton(String expiraton) {
		this.expiraton = expiraton;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
}
