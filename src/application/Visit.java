package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Visit {
	
	public Visit(int height, int weight, int temperature, int bloodPressure, List<String> immunizations,
			List<String> allergies, List<String> prescribedMedication, String healthConcerns, int dosage,
			String location, String physicalExamNotes, String medicationNotes) {
		this.height = height;
		this.weight = weight;
		this.temperature = temperature;
		this.bloodPressure = bloodPressure;
		this.immunizations = immunizations;
		this.allergies = allergies;
		this.prescribedMedication = prescribedMedication;
		this.healthConcerns = healthConcerns;
		this.dosage = dosage;
		this.location = location;
		this.physicalExamNotes = physicalExamNotes;
		this.medicationNotes = medicationNotes;
		this.visitDate = LocalDate.now();
	}
	
	// information about each visit
	private int height;
	private int weight;
	private int temperature;
	private int bloodPressure;
	private List<String> immunizations = new ArrayList<String>();
	private List<String> allergies = new ArrayList<String>();
	private List<String> prescribedMedication = new ArrayList<String>();
	private String healthConcerns;
	private int dosage;
	private String location;
	private String physicalExamNotes;
	private String medicationNotes;
	private LocalDate visitDate;	
	
	// I'm going insane
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(int bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public List<String> getImmunizations() {
		return immunizations;
	}
	public void setImmunizations(List<String> immunizations) {
		this.immunizations = immunizations;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	public List<String> getPrescribedMedication() {
		return prescribedMedication;
	}
	public void setPrescribedMedication(List<String> prescribedMedication) {
		this.prescribedMedication = prescribedMedication;
	}
	public String getHealthConcerns() {
		return healthConcerns;
	}
	public void setHealthConcerns(String healthConcerns) {
		this.healthConcerns = healthConcerns;
	}
	public int getDosage() {
		return dosage;
	}
	public void setDosage(int dosage) {
		this.dosage = dosage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhysicalExamNotes() {
		return physicalExamNotes;
	}
	public void setPhysicalExamNotes(String physicalExamNotes) {
		this.physicalExamNotes = physicalExamNotes;
	}
	public String getMedicationNotes() {
		return medicationNotes;
	}
	public void setMedicationNotes(String medicationNotes) {
		this.medicationNotes = medicationNotes;
	}
	public LocalDate getVisitDate() {
		return this.visitDate;
	}
}
