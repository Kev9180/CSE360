package application;

// Allows Nurse/DoctorPatientView to act as PatientListItem's controller
public interface PatientListItemListener {
	void onMessageButtonClick(Patient patient);
	void onListItemClick(Patient patient);
	void onViewInfoButtonClick(Patient patient);
}
