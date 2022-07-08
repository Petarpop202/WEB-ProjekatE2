package beans;

public class CheckedTraining {
	private Training Training;
	private String CheckedDate;
	private String TrainingDate;
	private Customer Customer;
	private Coach Trainer;
	private Boolean Active;
	
	public CheckedTraining(Training training, String checkedDate, String trainingDate,Customer customer, Coach trainer, Boolean active) {
		super();
		Training = training;
		CheckedDate = checkedDate;
		TrainingDate = trainingDate;
		Customer = customer;
		Trainer = trainer;
		Active = active;
	}
	public Training getTraining() {
		return Training;
	}
	public void setTraining(Training training) {
		Training = training;
	}
	public String getCheckedDate() {
		return CheckedDate;
	}
	public void setCheckedDate(String checkedDate) {
		CheckedDate = checkedDate;
	}
	public String getTrainingDate() {
		return TrainingDate;
	}
	public void setTrainingDate(String trainingDate) {
		TrainingDate = trainingDate;
	}
	public Coach getTrainer() {
		return Trainer;
	}
	public void setTrainer(Coach trainer) {
		Trainer = trainer;
	}
	public Boolean getActive() {
		return Active;
	}
	public void setActive(Boolean active) {
		Active = active;
	}
	public Customer getCustomer() {
		return Customer;
	}
	public void setCustomer(Customer customer) {
		Customer = customer;
	}
	
}
