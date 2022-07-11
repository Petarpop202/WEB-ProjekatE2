package beans;

public class TrainingHistory {
	private String Date;
	private Training Training;
	private Customer Customer;
	private Coach Trainer;
	
	public TrainingHistory() {
		super();
	}
	public TrainingHistory(String date, Training training,Customer customer, Coach trainer) {
		super();
		Date = date;
		Training = training;
		Customer = customer;
		Trainer = trainer;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Training getTraining() {
		return Training;
	}
	public void setTraining(Training training) {
		Training = training;
	}
	public Customer getCustomer() {
		return Customer;
	}
	public void setCustomer(Customer customer) {
		Customer = customer;
	}
	public Coach getTrainer() {
		return Trainer;
	}
	public void setTrainer(Coach trainer) {
		Trainer = trainer;
	}
	
	
}
