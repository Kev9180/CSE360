package application;

import java.util.List;

// PatientManager is a singleton that will allow Patient entities to be managed throughout the entire application
public class PatientManager {
	private static PatientManager instance;
	
	private PatientManager() {}
	
	// Method to return an instance of a PatientManager singleton
	public static synchronized PatientManager getInstance() {
		if (instance == null) {
			instance = new PatientManager();
		}
		
		return instance;
	}
	
	// Method to add a patient to the list
	public void addPatient(Patient patient) {
		//patientList.add(patient);
		DatabaseUtil.addUser(patient);
	}
	
	// Method to return the list of all patients in the system
	public List<Patient> getPatients() {
		// return patientList;
		return DatabaseUtil.getPatients();
	}
}
