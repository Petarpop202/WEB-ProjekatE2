package beans;

public class Location {
	private Double Length;
	private Double Width;
	private String Address;
	
	
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Location(Double length, Double width, String address) {
		super();
		Length = length;
		Width = width;
		Address = address;
	}
	public Double getLength() {
		return Length;
	}
	public void setLength(Double length) {
		Length = length;
	}
	public Double getWidth() {
		return Width;
	}
	public void setWidth(Double width) {
		Width = width;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	
}
