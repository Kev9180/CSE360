package application;

import java.util.ArrayList;
import java.util.List;

/*
 * 		To access the patient list from a file other than this one:
 * 		List<Patient> patients = PatientManager.getInstance().getPatients();
 * 
 * 		As an example, if you want to print the names of the patients in the patient list:
 * 
 * 		for (Patient patient : patientList) {
 * 			System.out.println(patient.getFirstName() + " " + patient.getLastName());
 * 		}
 */

//PatientManager is a singleton that will allow Patient entities to be managed throughout the entire application
public class PatientManager {
	private static PatientManager instance;
	private List<Patient> patientList = new ArrayList<>();
	
	private PatientManager() {}
	
	//Method to return an instance of a PatientManager singleton
	public static synchronized PatientManager getInstance() {
		if (instance == null) {
			instance = new PatientManager();
		}
		
		return instance;
	}
	
	//Method to add a patient to the list
	public void addPatient(Patient patient) {
		patientList.add(patient);
	}
	
	//Method to return the list of all patients in the system
	public List<Patient> getPatients() {
		return patientList;
	}
	
	//Method to find a patient by their username (this can be modified to search for a patient using a different parameter)
	public Patient findPatientByUsername(String username) {
		for (Patient patient : patientList) {
			if (patient.getUsername().equals(username)) {
				return patient;
			}
		}
		
		return null;
	}

}
