package beans;

import java.util.List;

public class Coach extends User {
	private List<TrainingHistory> TrainingHistories;


	public Coach() {
		super();
	}

	public Coach(String username, String password, String name, String surname, Boolean gender, String date,
			RoleEnum role) {
		super(username, password, name, surname, gender, date, role);
	}

	public Coach(List<TrainingHistory> trainingHistory) {
		super();
		TrainingHistories = trainingHistory;
	}

	public List<TrainingHistory> getTrainingHistory() {
		return TrainingHistories;
	}

	public void setTrainingHistory(List<TrainingHistory> trainingHistory) {
		TrainingHistories = trainingHistory;
	}
	
	
}
