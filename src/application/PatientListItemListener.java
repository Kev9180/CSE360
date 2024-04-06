package application;

// allows Nurse/DoctorPatientView to act as PatientListItem's controller
public interface PatientListItemListener {
	void onMessageButtonClick();
	void onListItemClick();
	void onViewInfoButtonClick();
}
